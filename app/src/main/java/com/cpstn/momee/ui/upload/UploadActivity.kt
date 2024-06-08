package com.cpstn.momee.ui.upload

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.databinding.ActivityUploadBinding
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.response.MediaType
import com.cpstn.momee.ui.result.ResultActivity
import com.cpstn.momee.utils.API
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.FileUtils
import com.cpstn.momee.utils.FileUtils.Companion.reduceFileImage
import com.cpstn.momee.utils.startActivityTo
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.Date

@AndroidEntryPoint
class UploadActivity : BaseActivity<ActivityUploadBinding>() {

    private val viewModel: UploadViewModel by viewModels()

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
    }

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this, REQUIRED_PERMISSION
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
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
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
            currentImageUri?.let { uri -> analyzeImage(uri) }
        }
    }

    private fun analyzeImage(uri: Uri) {
        val file = FileUtils.uriToFile(uri, this).reduceFileImage()
        val requestImage = file.asRequestBody(MediaType.IMAGE.value.toMediaType())
        val multiPartBody = MultipartBody.Part.createFormData(API.IMAGE, file.name, requestImage)
        viewModel.uploadImage(multiPartBody)
    }

    private fun setupObserver() {
        viewModel.uploadResult.observe(this) {
            when (it) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Error -> {
                    showLoading(false)
                    showToast("Tidak dapat mengupload file")
                }
                is Result.Success -> {
                    showLoading(false)
                    moveToResult(it.data?.data ?: listOf())
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) = with(binding) {
        overlay.isVisible = isLoading
        ltScan.isVisible = isLoading
    }

    private fun moveToResult(data: List<ProductsSearchByImageDomain.Data>) {
        val bundle = bundleOf(
            ResultActivity.EXTRA_IMAGE_URI to currentImageUri.toString(),
            ResultActivity.EXTRA_RESULT to ArrayList(data)
        )
        startActivityTo(ResultActivity::class.java, bundle)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}