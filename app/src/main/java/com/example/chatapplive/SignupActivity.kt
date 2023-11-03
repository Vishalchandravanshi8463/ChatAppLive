package com.example.chatapplive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapplive.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")

        binding.gotosignin.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }



        binding.signupbutton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val name = binding.nameEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmpassET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // Update user profile with display name
                            val userProfileChangeRequest =
                                UserProfileChangeRequest.Builder().setDisplayName(name).build()
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.updateProfile(userProfileChangeRequest)

                            val userId = FirebaseAuth.getInstance().uid
                            if (userId != null) {
                                val userModel = UserModel(userId, name, email, pass)
                                databaseReference.child(userId).setValue(userModel)
                            }
                            Toast.makeText(
                                this,
                                "User Registered Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Move to SigninActivity after successful registration
                            val intent = Intent(this, SigninActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}