package kz.iitu.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat_list.*
import java.util.*

class ChatList : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val database by lazy { FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        setupViews()
    }


    private fun setupViews() {
        list_view.layoutManager = LinearLayoutManager(this)
        database.collection("messages")
            .whereEqualTo("senderId",FirebaseAuth.getInstance().currentUser?.uid)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                val messages = querySnapshot?.documents?.map{
                    it.toObject(Message::class.java)
                } ?: emptyList()
                list_view.adapter = MessageAdapter(message = messages)
            }




        send_button.setOnClickListener {
            getUserData {
                val message = Message(
                    senderId = FirebaseAuth.getInstance().currentUser!!.uid,
                    name = it.name,
                    surname = it.surname,
                    message = message_send.text.toString().trim(),
                    date = Date().toString()
                )


                database.collection("messages").add(message)
            }


        }
    }

    private fun getUserData(onUserDataLoaded: (User) -> Unit) {
        database.collection("users")
            .whereEqualTo("id", FirebaseAuth.getInstance().currentUser?.uid )
            .addSnapshotListener { snapshot, firebaseFirestoreException ->
                onUserDataLoaded(snapshot?.documents?.first()?.toObject(User::class.java)!!)
            }
    }
}
