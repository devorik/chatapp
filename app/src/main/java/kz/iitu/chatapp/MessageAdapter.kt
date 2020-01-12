package kz.iitu.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.layout_message.view.*
import java.util.concurrent.RecursiveAction
private val auth by lazy { FirebaseAuth.getInstance() }
private val database by lazy { FirebaseFirestore.getInstance() }
class MessageAdapter(
    private val message: List<Message?> = listOf()
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

        class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
            fun bindMessage(message: Message?) {
                view.name_text_view.text = message?.name
                view.surname_text_view.text = message?.surname
                view.message_text_view.text = message?.message
                view.timestamp_text_view.text = message?.date
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_message,parent,false))


    override fun getItemCount() = message.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindMessage(message[position])
    }
}