package com.example.sleeptrackerapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

/**
 * Activity for handling user sign-in using Google authentication.
 *
 * This activity manages the Google Sign-In process and authenticates the user with Firebase.
 * It provides a button for users to initiate the sign-in process and handles the result to
 * authenticate with Firebase.
 */
class SignInActivity : AppCompatActivity() {

    // FirebaseAuth instance for handling authentication with Firebase.
    private lateinit var auth: FirebaseAuth

    // GoogleSignInClient for handling Google Sign-In.
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize FirebaseAuth instance.
        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In options.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // Request the ID token for authentication
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail() // Request the user's email address
            .build()

        // Create a GoogleSignInClient instance with the configured options.
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Set up the sign-in button and its click listener.
        findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            signIn()
        }
    }

    /**
     * Initiates the Google Sign-In process by starting the sign-in intent.
     */
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        // Request code for Google Sign-In result.
        private const val RC_SIGN_IN = 9001
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Handle the result of the Google Sign-In process.
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Retrieve the signed-in account from the task.
                val account = task.getResult(ApiException::class.java)!!
                // Authenticate with Firebase using the Google account's ID token.
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Log the sign-in failure and show an error message.
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    /**
     * Updates the UI based on the user's sign-in status.
     *
     * @param user The currently signed-in user. Null if sign-in failed.
     */
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in, navigate to the main activity.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the sign-in activity so the user can't navigate back to it
        } else {
            // User is not signed in, show an error message.
            Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Authenticates with Firebase using the Google account's ID token.
     *
     * @param idToken The ID token obtained from Google Sign-In.
     */
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in was successful, log the success and update the UI.
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // Sign-in failed, log the failure and update the UI.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
}