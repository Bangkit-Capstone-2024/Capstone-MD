package com.cpstn.momee.ui.account.tenant

import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.TenantDomain
import com.cpstn.momee.databinding.ActivityCreateTenantBinding
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.response.MediaType
import com.cpstn.momee.utils.API
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.FileUtils
import com.cpstn.momee.utils.FileUtils.Companion.reduceFileImage
import com.cpstn.momee.utils.Permissions
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.parcelable
import com.cpstn.momee.utils.visible
import com.google.android.material.snackbar.Snackbar
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Date

@AndroidEntryPoint
class CreateTenantActivity : BaseActivity<ActivityCreateTenantBinding>() {

    private val viewModel: TenantViewModel by viewModels()

    private var tenantData: TenantDomain.Data? = null

    private var currentImageUri: Uri? = null
    private var tempImageUri: Uri? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Snackbar.make(
                    binding.root, getString(R.string.app_permission_granted), Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    binding.root, getString(R.string.app_permission_denied), Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    override fun getViewBinding(): ActivityCreateTenantBinding =
        ActivityCreateTenantBinding.inflate(layoutInflater)

    override fun setupView() {
        setBundleData()
        setupObserver()
        setupActionBar()
        setupPermission()
        setupListener()
    }

    private fun setBundleData() {
        tenantData = intent.extras?.parcelable<TenantDomain.Data>(EXTRAS.DATA) ?: TenantDomain.Data()
        if (tenantData?.nameTenants.orEmpty().isNotEmpty()) {
            binding.toolbar.title = "Ubah Lapak Momee"
            binding.etTenant.setText(tenantData?.nameTenants.orEmpty())
        }
        if (tenantData?.addressTenants.orEmpty().isNotEmpty()) binding.etAdress.setText(tenantData?.addressTenants.orEmpty())
        if (tenantData?.phone.orEmpty().isNotEmpty()) binding.etPhone.setText(tenantData?.phone.orEmpty())
        if (tenantData?.image.orEmpty().isNotEmpty()) {
            binding.btnReUpload.visible(true)
            binding.ivPreview.load(tenantData?.image.orEmpty())
        }
    }

    private fun setupObserver() {
        viewModel.submitTenantResult.observe(this) {
            when (it) {
                is DataResult.Loading -> binding.containerLoading.visible(true)

                is DataResult.Success -> {
                    binding.containerLoading.visible(false)
                    setResult(RESULT_OK)
                    finish()
                }

                is DataResult.Error -> {
                    binding.containerLoading.visible(false)
                    Toast.makeText(this@CreateTenantActivity, it.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
                    showImage(it)
                }
            }
        } else if (result.resultCode == RESULT_CANCELED) {
            // write code if user canceled pick image
        }
    }

    private fun setupPermission() {
        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Permissions.CAMERA)
        }
        binding.ivPreview.setOnClickListener { startGallery() }
        binding.btnSubmit.setOnClickListener {
            uploadImage()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun uploadImage() {
        val imageFile = currentImageUri?.let { FileUtils.uriToFile(it, this).reduceFileImage() }
        val tenantName = binding.etTenant.text.toString().toRequestBody(MediaType.TEXT.value.toMediaType())
        val tenantAddress = binding.etAdress.text.toString().toRequestBody(MediaType.TEXT.value.toMediaType())
        val tenantPhone = binding.etPhone.text.toString().toRequestBody(MediaType.TEXT.value.toMediaType())
        val requestImage = imageFile?.asRequestBody(MediaType.IMAGE.value.toMediaType())
        val multiPartBody = requestImage?.let { MultipartBody.Part.createFormData(API.IMAGE, imageFile.name, it) }
        viewModel.submitTenant(tenantData?.id, tenantName, tenantAddress, tenantPhone, multiPartBody, tenantData != null)
    }

    private fun showImage(imageUri: Uri) {
        binding.ivPreview.setImageURI(imageUri)
        binding.btnReUpload.visible(true)
        binding.tvUploadLabel.visible(false)
    }

    private fun setupListener() = with(binding) {
        btnReUpload.setOnClickListener {
            startGallery()
        }
    }

}