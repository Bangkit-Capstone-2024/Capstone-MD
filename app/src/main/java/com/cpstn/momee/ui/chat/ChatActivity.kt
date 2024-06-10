package com.cpstn.momee.ui.chat

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.cpstn.momee.R
import com.cpstn.momee.data.domain.UserFirebase
import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.databinding.ActivityChatBinding
import com.cpstn.momee.ui.chat.adapter.ChatAdapter
import com.cpstn.momee.ui.chat.adapter.Message
import com.cpstn.momee.utils.Constant
import com.cpstn.momee.utils.EXTRAS
import com.cpstn.momee.utils.Firebase
import com.cpstn.momee.utils.Notification
import com.cpstn.momee.utils.StringHelper.encode
import com.cpstn.momee.utils.base.BaseActivity
import com.cpstn.momee.utils.hideKeyboard
import com.cpstn.momee.utils.parcelable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class ChatActivity : BaseActivity<ActivityChatBinding>() {

    private val viewModel: ChatListViewModel by viewModels()

    private var receiverUserData: UserListDomain.Query = UserListDomain.Query()
    private var mAdapter: ChatAdapter? = null

    private var senderRoom: String = Constant.EMPTY_STRING
    private var receiverRoom: String = Constant.EMPTY_STRING
    private var messageList: ArrayList<Message> = arrayListOf()
    private var otherUserFcmToken: String = Constant.EMPTY_STRING

    private lateinit var mObjRef: DatabaseReference

    override fun getViewBinding(): ActivityChatBinding = ActivityChatBinding.inflate(layoutInflater)

    override fun setupView() {

        mObjRef = FirebaseDatabase
            .getInstance(Firebase.DB_FIREBASE_URL)
            .getReference()


        setupExtras()
        viewModel.getUserSession()
        setupObserver()
        with(binding) {
            tvUserName.text = receiverUserData.username
            etMessage.setHint(getString(R.string.app_type_message))
        }
    }

    private fun setupObserver() {
        viewModel.userSessionResult.observe(this) {
            val currentUserEmail = it.userEmail
            setupChatRoom(currentUserEmail)
        }
    }

    private fun setupChatRoom(currentUserEmail: String) {
        senderRoom = "${receiverUserData.email} $currentUserEmail".encode()
        receiverRoom = "$currentUserEmail ${receiverUserData.email}".encode()

        mObjRef.child("users").child(receiverUserData.email.orEmpty().encode()).get()
            .addOnSuccessListener {
                val data = it.getValue(UserFirebase::class.java)
                otherUserFcmToken = data?.fcmToken.orEmpty()
            }
        mObjRef.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (data in snapshot.children) {
                        val messages = data.getValue(Message::class.java)
                        messageList.add(messages ?: Message())
                    }
                    mAdapter = ChatAdapter(messageList, currentUserEmail)
                    binding.rvChat.adapter = mAdapter
                    mAdapter?.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        binding.ivSend.setOnClickListener {
            binding.etMessage.hideKeyboard()
            val message = binding.etMessage.text.toString()
            val now = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val messageObj = Message(message = message, senderId = currentUserEmail, date = now)
            Notification.sendChatNotification(
                fcmToken = otherUserFcmToken,
                receiverUserData.username,
                receiverUserData.email.orEmpty(),
                messageObj.message,
                Dispatchers.IO
            )
            binding.etMessage.setText("")
            mObjRef.child("chats").child(senderRoom).child("message").push()
                .setValue(messageObj).addOnSuccessListener {
                    mObjRef.child("chats").child(receiverRoom).child("message").push()
                        .setValue(messageObj).addOnSuccessListener { }
                }
        }
    }


    private fun setupExtras() {
        receiverUserData = intent.extras?.parcelable<UserListDomain.Query>(EXTRAS.DATA) ?: UserListDomain.Query()
    }
}