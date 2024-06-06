package com.example.momee.ui.auth.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.momee.MainActivity
import com.example.momee.R
import com.example.momee.databinding.ActivityRegisterBinding
import com.example.momee.ui.auth.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityRegisterBinding
    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registerButton.setOnClickListener {
            registerUser()
        }
        binding.googleSignInButton.setOnClickListener {
            autoSignIn()
        }
    }

    private fun autoSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun registerUser() {
        val email = binding.usernameInput.text.toString()
        val password = binding.passwordInput.text.toString()
        val phoneNumber = binding.numberInput.text.toString()
        val cppCode = binding.ccp.selectedCountryCode

        val codeWithPhone = cppCode+phoneNumber
        if (email.isNotEmpty() && password.isNotEmpty() && codeWithPhone.isNotEmpty()) {
            auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val result = task.result?.signInMethods
                        if (result.isNullOrEmpty()) {
                            // Email belum terdaftar, lanjutkan proses pendaftaran
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(this) { createUserTask ->
                                    if (createUserTask.isSuccessful) {
                                        val user = auth.currentUser
                                        saveUserToFirestore(user?.uid, email, codeWithPhone)
                                        Toast.makeText(this, "Registered as ${user?.email}", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, LoginActivity::class.java))
                                        finish()
                                    } else {
                                        val errorMsg = createUserTask.exception?.message ?: "Unknown error"
                                        Toast.makeText(this, "Registration failed: $errorMsg", Toast.LENGTH_SHORT).show()
                                        Log.e("RegisterActivity", "Registration failed", createUserTask.exception)
                                    }
                                }
                        } else {
                            // Email sudah terdaftar
                            Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorMsg = task.exception?.message ?: "Unknown error"
                        Toast.makeText(this, "Failed to check email registration status: $errorMsg", Toast.LENGTH_SHORT).show()
                        Log.e("RegisterActivity", "Failed to check email registration status", task.exception)
                    }
                }
        } else {
            Toast.makeText(this, "Please enter email, password, and phone number", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserToFirestore(uid: String?, email: String, phoneNumber: String) {
        uid?.let {
            val userMap = hashMapOf(
                "email" to email,
                "phoneNumber" to phoneNumber
            )
            db.collection("users").document(it)
                .set(userMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "User info saved", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save user info: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val email = user?.email ?: ""
                    val phoneNumber = "" // Nomor telepon kosong
                    saveUserToFirestore(user?.uid, email, phoneNumber)
                    Toast.makeText(this, "Signed in as ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
