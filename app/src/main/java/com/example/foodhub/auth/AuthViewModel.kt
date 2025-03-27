package com.example.foodhub.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val auth = FirebaseAuth.getInstance()

    private var _authState = MutableLiveData<FirebaseUser?>()
    val authState: LiveData<FirebaseUser?> = _authState

    private var _resetPasswordState = MutableLiveData<Boolean>()
    val resetPasswordState: LiveData<Boolean> = _resetPasswordState

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        checkIfLoggedIn()
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginTask ->
                if (loginTask.isSuccessful) {
                    val user = auth.currentUser
                    if (user?.isEmailVerified == true) {
                        _authState.value = user
                    } else {
                        _errorMessage.value = "please verify your email first"
                        auth.signOut()
                    }
                }
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { registerTask ->
                if (registerTask.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verivicationTask ->
                            if (verivicationTask.isSuccessful) {
                                _errorMessage.value =
                                    "Registered Successfully , check email for verification"
                            } else {
                                _errorMessage.value = "Fail to send verification message try again"
                            }
                        }
                } else {
                    if (registerTask.exception is FirebaseAuthUserCollisionException) {
                        _errorMessage.value = "Already Registered"
                    } else {
                        _errorMessage.value = registerTask.exception?.message
                    }
                }
            }
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { resetPasswordTask ->
                if (resetPasswordTask.isSuccessful) {
                    _errorMessage.value = "Check Your Email Inbox"
                } else {
                    _errorMessage.value = resetPasswordTask.exception?.message
                }
            }
    }

    fun setUsername(userName: String) {
        val user = auth.currentUser ?: return
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { setUsernameTask ->
                if (setUsernameTask.isSuccessful) {
                    _errorMessage.value = "username saved successfully"
                } else {
                    _errorMessage.value = setUsernameTask.exception?.message
                }
            }
    }

    fun logout(){
        auth.signOut()
        _authState.value = null
    }

    private fun checkIfLoggedIn(){
        _authState.value = auth.currentUser?.takeIf { it.isEmailVerified }
    }

}