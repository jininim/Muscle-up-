package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_CHAT
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityNoticeBoardDetailBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityNoticeBoardDetailBinding
class NoticeBoardDetailActivity : AppCompatActivity() {

    private val chatList = mutableListOf<NoticeBoardDetailData>()
    private val adapter = NoticeBoardDetailAdapter()
    private var chatDB: DatabaseReference? = null

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBoardDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 커뮤니티 프래그먼트에서 이벤트 클릭 시 화면 전환 & 해당 게시글의 정보를 intent 로 받아오기
        val chatKey = intent.getLongExtra("chatKey", -1) // 게시글 고유 키
        val title = intent.getStringExtra("title") // 게시글 제목
        val content = intent.getStringExtra("content") // 게시글 내용

        val time : Long = chatKey
        val postDate = SimpleDateFormat("MM-dd") // 게시 날짜
        val postTime = SimpleDateFormat("hh:mm") // 게시 시간

        binding.noticeBoardTitle.text = title.toString()
        binding.content.text = content.toString()

        binding.postTimeDate.text = postDate.format(time)
        binding.postTimeDetail.text = postTime.format(time)

        chatDB = Firebase.database.reference.child(DB_CHAT).child("$chatKey")

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

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }
}