package com.example.teamproject_hometrainingassistant_app.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.DBKey
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentNoticeBoardBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.CreateNoticeBoardActivity
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardAdapter
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardData
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail.NoticeBoardDetailActivity
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommunityFragment : Fragment() {

    private lateinit var noticeBoardAdapter: NoticeBoardAdapter // 리사이클러뷰 어댑터
    private lateinit var noticeBoardDB: DatabaseReference       // firebase realTimeDB
    private var _binding: FragmentNoticeBoardBinding? = null

    private val noticeBoardList = mutableListOf<NoticeBoardData>() // firebase에서 가져온 값들을 저장하는 리스트

    private val binding get() = _binding!!

    private val listener = object : ChildEventListener {    // listener = firebase에서 저장된 게시글 값들을 가져와서 여기에 저장.
                                                            // 데이터베이스의 특정한 노드에 대한 변경을 수신 대기
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) { // 리스트의 아이템을 검색하거나 추가가 있을 때 수신.
            val noticeBoardModel = snapshot.getValue(NoticeBoardData::class.java) // 주어진 게시글 형식으로 이루어진 데이터들을 snapshot으로 가져와서 저장.
            noticeBoardModel ?: return

            noticeBoardList.add(noticeBoardModel) // noticeBoardModel에 저장된 값을 리스트에 추가.
            noticeBoardAdapter.submitList(noticeBoardList) // 아이템 업데이트
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {} // 리스트의 아이템의 변화가 있을때 수신합니다.

        override fun onChildRemoved(snapshot: DataSnapshot) {} //  리스트의 아이템이 삭제되었을때 수신합니다.

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {} // 리스트의 순서가 변경되었을때 수신합니다.

        override fun onCancelled(error: DatabaseError) {}

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoticeBoardBinding.inflate(inflater, container, false)
        val view = binding.root

        noticeBoardList.clear()
        noticeBoardDB = Firebase.database.reference.child(DBKey.DB_NOTICE_BOARD) // firebase에 저장되는 경로.
        noticeBoardAdapter = NoticeBoardAdapter(onItemClicked = { noticeBoardData -> // noticeBoardData = 게시글 데이터 형식.
            val intent = Intent(context, NoticeBoardDetailActivity::class.java)      // 아이템 클릭 시 아이템에 해당하는 데이터들을
            intent.putExtra("chatKey", noticeBoardData.key)                    // 게시글 화면으로 전달.
            intent.putExtra("title", noticeBoardData.text)
            intent.putExtra("content", noticeBoardData.content)
            startActivity(intent)
        })

        noticeBoardDB.addChildEventListener(listener) // 실시간으로 변경 사항을 감지

        binding.createNoticeBoardButton.setOnClickListener { // 게시글 만들기 버튼
            val intent = Intent(context, CreateNoticeBoardActivity::class.java)
            intent.run { startActivity(this) }
        }

        binding.titleRecyclerView.layoutManager = LinearLayoutManager(context) // 리사이클러뷰 어댑터 연결
        binding.titleRecyclerView.adapter = noticeBoardAdapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        noticeBoardDB.removeEventListener(listener) // 화면 나가면 초기화
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        noticeBoardAdapter.notifyDataSetChanged() // Adapter 에게 RecyclerView 의 리스트 데이터가 바뀌었으니 모든 항목을 통째로 업데이트를 하라는 신호가 간다.
    }

}