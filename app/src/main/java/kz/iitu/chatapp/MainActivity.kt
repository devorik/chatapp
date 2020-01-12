package kz.iitu.chatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()


        database.collection("users")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val users = querySnapshot?.documents?.map {
                    it.toObject(User::class.java)
                }
                Log.d("taag", users.toString())
            }
    }

    private fun setupViews() {
        to_sign_up_page.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        log_in_button_login_page.setOnClickListener {
            SignIn(email_login_edit_text.text.toString().trim(),password_login_edit_text.text.toString().trim())
        }
    }

     private fun SignIn(
         email: String,
         password: String
     ) {
         auth.signInWithEmailAndPassword(email,password)
             .addOnCompleteListener {task ->
                 if(task.isSuccessful) {
                     startActivity(Intent(this,ChatList::class.java))
                     return@addOnCompleteListener
                 }

                 Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()}
     }



}
