package com.cpstn.momee

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.StrictMode
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cpstn.momee.data.domain.UserFirebase
import com.cpstn.momee.data.domain.UserLocation
import com.cpstn.momee.databinding.ActivityMainBinding
import com.cpstn.momee.utils.Firebase
import com.cpstn.momee.utils.StringHelper.encode
import com.cpstn.momee.utils.base.BaseActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mObjRef: DatabaseReference

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissionRequest = this.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                getMyLastLocation()
            }

            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                getMyLastLocation()
            }

            else -> {
                Toast.makeText(
                    this, "Location permission not granted", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupView() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getMyLastLocation()
        viewModel.getUserSession()
        mObjRef = FirebaseDatabase
            .getInstance(Firebase.DB_FIREBASE_URL)
            .getReference()

        setupBottomNav()
    }

    private fun setupBottomNav() = with(binding) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        setupObserver()

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

//        Log.i("zxc", AccessToken.getAccessToken())
    }


    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                        CancellationTokenSource().token

                    override fun isCancellationRequested(): Boolean = false

                }).addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val long = location.longitude
                    Geocoder(this, Locale("id"))
                        .getAddress(lat, long) { address: Address? ->
                            if (address != null) {
                                viewModel.getCurrentLocation.postValue(
                                    UserLocation(
                                        province = address.adminArea.orEmpty(),
                                        city = address.subAdminArea.orEmpty()
                                    )
                                )
                            }
                        }
                } else {
                    Toast.makeText(
                        this, "Location is not found. Try Again", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    @Suppress("DEPRECATION")
    private fun Geocoder.getAddress(
        latitude: Double,
        longitude: Double,
        address: (Address?) -> Unit
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
            return
        }
        try {
            address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
        } catch (e: Exception) {
            address(null) //will catch if there is an internet problem
        }
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            if (it.userToken.isNotEmpty()) {
                viewModel.currentUserInfo = it
                getFcmToken(it.userEmail)
            }
        }
    }

    private fun getFcmToken(currentUserEmail: String) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                sendFcmTokenToFirebase(it.result, currentUserEmail)
            }
        }
    }

    private fun sendFcmTokenToFirebase(token: String, currentEmail: String) {
        val user = UserFirebase(userEmail = currentEmail, fcmToken = token)
        val identifier = currentEmail.encode()
        mObjRef.child("users").child(identifier).setValue(user)
    }
}