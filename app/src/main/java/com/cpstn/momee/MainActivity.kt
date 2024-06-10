package com.cpstn.momee

import android.os.StrictMode
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.cpstn.momee.data.domain.UserFirebase
import com.cpstn.momee.databinding.ActivityMainBinding
import com.cpstn.momee.utils.AccessToken
import com.cpstn.momee.utils.StringHelper.encode
import com.cpstn.momee.utils.base.BaseActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mObjRef: DatabaseReference

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun setupView() {
        viewModel.getUserSession()
        mObjRef = FirebaseDatabase
            .getInstance("https://momee-e9f6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
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

        Log.i("zxc", AccessToken.getAccessToken())
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            if (it.userToken.isNotEmpty()) {
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