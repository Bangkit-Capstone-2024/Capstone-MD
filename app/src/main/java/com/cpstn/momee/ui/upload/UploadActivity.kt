package com.cpstn.momee.ui.upload

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.cpstn.momee.R
import com.cpstn.momee.databinding.ActivityUploadBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.response.MediaType
import com.cpstn.momee.ui.product.SearchProductActivity
import com.cpstn.momee.utils.API
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.FileUtils
import com.cpstn.momee.utils.FileUtils.Companion.reduceFileImage
import com.cpstn.momee.utils.ImageClassifierHelper
import com.cpstn.momee.utils.Permissions
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.getLabelProduct
import com.cpstn.momee.utils.startActivityTo
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.util.Date

@AndroidEntryPoint
class UploadActivity : BaseActivity<ActivityUploadBinding>() {

    private val viewModel: UploadViewModel by viewModels()

    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private var currentImageUri: Uri? = null
    private var tempImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(binding.root, getString(R.string.app_permission_granted), Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(binding.root, getString(R.string.app_permission_denied), Snackbar.LENGTH_SHORT).show()
            }
        }

    override fun getViewBinding(): ActivityUploadBinding = ActivityUploadBinding.inflate(layoutInflater)

    override fun setupView() {
        setupPermission()
        setupListener()
        setupObserver()
        setupActionBar()
    }

    private fun analyzeImage() {
        imageClassifierHelper = ImageClassifierHelper(
            this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onResult(result: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        result?.let {
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                val sortedData = it.first().categories.sortedByDescending { category -> category?.score }
                                val resultData = sortedData.first()
                                val score = NumberFormat.getPercentInstance().format(resultData.score).trim()
                                showLoading(true)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    showLoading(false)
                                    val label = getLabelProduct(resultData.label)
                                    moveToResult(label)
                                }, 1750)
                            }
                        }
                    }
                }

                override fun onError(error: String) {
                    runOnUiThread {
                        showToast(error)
                    }
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setupActionBar() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, Permissions.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                tempImageUri = uri
                tempImageUri?.let {
                    val intent = UCrop.of(
                        it, Uri.fromFile(File(cacheDir, "sample-image-${Date().time}.png"))
                    ).getIntent(this)
                    launcherUCrop.launch(intent)
                }
            } else {
                Snackbar.make(
                    binding.root, getString(R.string.app_no_media_selected), Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    private var launcherUCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val resultUri = result.data?.let { UCrop.getOutput(it) }
            resultUri?.let { uri ->
                currentImageUri = uri
                currentImageUri?.let {
                    binding.btnAnalyze.isEnabled = true
                    showImage(it)
                }
            }
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, getString(R.string.app_canceled), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupPermission() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Permissions.CAMERA)
        }
        binding.btnGallery.setOnClickListener { startGallery() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImage(imageUri: Uri) {
        binding.ivPreview.setImageURI(imageUri)
    }

    private fun setupListener() = with(binding) {
        btnAnalyze.setOnClickListener {
            currentImageUri?.let {
                analyzeImage()
                imageClassifierHelper.classifyStaticImage(it)
            }
        }
    }

    private fun analyzeImage(uri: Uri) {
        val file = FileUtils.uriToFile(uri, this).reduceFileImage()
        val requestImage = file.asRequestBody(MediaType.IMAGE.value.toMediaType())
        val multiPartBody = MultipartBody.Part.createFormData(API.IMAGE, file.name, requestImage)
        viewModel.uploadImage(multiPartBody)
    }

    private fun setupObserver() {
        viewModel.uploadDataResult.observe(this) {
            when (it) {
                is DataResult.Loading -> {
                    showLoading(true)
                }
                is DataResult.Error -> {
                    showLoading(false)
                }
                is DataResult.Success -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        overlay.isVisible = isLoading
        ltScan.isVisible = isLoading
    }

    private fun moveToResult(label: String) {
        val bundle = bundleOf(
            EXTRAS.DATA to label
        )
        startActivityTo(SearchProductActivity::class.java, bundle)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}