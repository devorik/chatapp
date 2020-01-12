package kz.iitu.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_sign_up)
        setupViews()
    }

    private fun setupViews() {
        sign_up_button_sign_up_activity.setOnClickListener {
            if (email_edit_text_sign_up.text.toString().trim().isEmpty() ||
                password_edit_text_sign_up.toString().trim().isEmpty() ||
                name_edit_text_sign_up.toString().trim().isEmpty() ||
                surname_edit_text_sign_up.text.toString().trim().isEmpty()
                    ) {

                Toast.makeText(this,"fields cannot be empty",Toast.LENGTH_LONG).show()

            }else {
                signUp(email_edit_text_sign_up.text.toString().trim(),password_edit_text_sign_up.text.toString().trim())
                onBackPressed()
            }
        }
    }

    private fun signUp(
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {task ->
                if(task.isSuccessful) {
                    Toast.makeText(this,"Success!", Toast.LENGTH_LONG).show()
                    val id = task.result?.user?.uid
                    val email: String = email_edit_text_sign_up.text.toString().trim()
                    val name: String  = name_edit_text_sign_up.text.toString().trim()
                    val surname: String = surname_edit_text_sign_up.text.toString().trim()

                    val user = hashMapOf(
                        "id" to id,
                        "email" to email,
                        "name" to name,
                        "surname" to surname
                    )

                    database.collection("users").add(user)
                    return@addOnCompleteListener
                }

                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            }
    }
}

