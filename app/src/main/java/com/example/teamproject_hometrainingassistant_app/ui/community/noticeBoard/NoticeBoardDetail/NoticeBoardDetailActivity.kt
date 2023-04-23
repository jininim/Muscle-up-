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
class NoticeBoardDetailActivity : AppCompatActivity() { // 게시글 내부 액티비티

    private val chatList = mutableListOf<NoticeBoardDetailData>() // 채팅들을 저장하는 리스트
    private val adapter = NoticeBoardDetailAdapter() // 리사이클러뷰 어댑터
    private var chatDB: DatabaseReference? = null    // firebase에 채팅을 저장하는 경로

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

        binding.noticeBoardTitle.text = title.toString()    // 받아온 값들로 게시글 화면 텍스트 수정.
        binding.content.text = content.toString()

        binding.postTimeDate.text = postDate.format(time)
        binding.postTimeDetail.text = postTime.format(time)

        chatDB = Firebase.database.reference.child(DB_CHAT).child("$chatKey") // firebase에 채팅을 저장하는 경로

        chatDB?.addChildEventListener(object : ChildEventListener{ // 채팅들 firebase에서 가져오기
            @SuppressLint("NotifyDataSetChanged")
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatItem = snapshot.getValue(NoticeBoardDetailData::class.java)
                chatItem ?: return

                chatList.add(chatItem) // 가져온 채팅들을 리스트(chatList)에 저장.
                adapter.submitList(chatList)
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {}

        })

        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this) // 리사이클러뷰 어댑터 연결
        binding.chatRecyclerview.adapter = adapter

        binding.sendButton.setOnClickListener { // 채팅 보내기 버튼 클릭 시
            val chatItem = NoticeBoardDetailData(
                message = binding.chatEditText.text.toString() // 작성한 문장을 chatItem에 삽입
            )
            binding.chatEditText.text = null // 작성한 에딧텍스트는 초기화

            chatDB?.push()?.setValue(chatItem) // firebase에 작성한 텍스트 저장
        }

        binding.exerciseBackButton.setOnClickListener { // 뒤로가기 버튼
            finish()
        }
    }
}