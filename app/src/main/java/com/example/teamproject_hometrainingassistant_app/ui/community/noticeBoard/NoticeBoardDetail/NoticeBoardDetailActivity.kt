package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_CHAT
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_NOTICE_BOARD
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityNoticeBoardDetailBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityNoticeBoardDetailBinding
class NoticeBoardDetailActivity : AppCompatActivity() {

    private val chatList = mutableListOf<NoticeBoardDetailData>()
    private val adapter = NoticeBoardDetailAdapter()
    private var chatDB: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chatKey = intent.getLongExtra("chatKey", -1)

        chatDB = Firebase.database.reference.child(DB_NOTICE_BOARD).child(DB_CHAT).child("$chatKey")

        chatDB?.addChildEventListener(object : ChildEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(NoticeBoardDetailData::class.java)
                chatItem ?: return

                chatList.add(chatItem)
                adapter.submitList(chatList)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })

        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerview.adapter = adapter

        binding.sendButton.setOnClickListener {
            val chatItem = NoticeBoardDetailData(
                message = binding.chatEditText.text.toString()
            )

            chatDB?.push()?.setValue(chatItem)
        }
    }
}