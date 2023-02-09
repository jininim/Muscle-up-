package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_NOTICE_BOARD
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityNoticeBoardBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail.NoticeBoardDetailActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NoticeBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticeBoardBinding
    private lateinit var noticeBoardAdapter: NoticeBoardAdapter
    private lateinit var noticeBoardDB: DatabaseReference

    private val noticeBoardList = mutableListOf<NoticeBoardData>()
    private val listener = object : ChildEventListener { // 데이터베이스의 특정한 노드에 대한 변경을 수신 대기
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) { // 리스트의 아이템을 검색하거나 추가가 있을 때 수신합니다.
            val noticeBoardModel = snapshot.getValue(NoticeBoardData::class.java)
            noticeBoardModel ?: return

            noticeBoardList.add(noticeBoardModel)
            noticeBoardAdapter.submitList(noticeBoardList) // 아이템 업데이트

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {} // 리스트의 아이템의 변화가 있을때 수신합니다.

        override fun onChildRemoved(snapshot: DataSnapshot) {} //  리스트의 아이템이 삭제되었을때 수신합니다.

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {} // 리스트의 순서가 변경되었을때 수신합니다.

        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoticeBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noticeBoardList.clear()
        noticeBoardDB = Firebase.database.reference.child(DB_NOTICE_BOARD)
        noticeBoardAdapter = NoticeBoardAdapter(onItemClicked = { noticeBoardData ->
            val intent = Intent(this, NoticeBoardDetailActivity::class.java)
            intent.putExtra("chatKey", noticeBoardData.key)
            startActivity(intent)
        })

        binding.createNoticeBoardButton.setOnClickListener {
            val intent = Intent(this, CreateNoticeBoardActivity::class.java)
            intent.run { startActivity(this) }
        }

        binding.titleRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.titleRecyclerView.adapter = noticeBoardAdapter

        noticeBoardDB.addChildEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        noticeBoardAdapter.notifyDataSetChanged() // Adapter 에게 RecyclerView 의 리스트 데이터가 바뀌었으니 모든 항목을 통째로 업데이트를 하라는 신호가 간다.
    }

    override fun onDestroy() {
        super.onDestroy()

        noticeBoardDB.removeEventListener(listener)
    }
}