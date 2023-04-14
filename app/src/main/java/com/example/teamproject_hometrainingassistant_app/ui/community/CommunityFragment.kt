package com.example.teamproject_hometrainingassistant_app.ui.community

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject_hometrainingassistant_app.DBKey
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityNoticeBoardBinding
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentCommunityBinding
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentNoticeBoardBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.CreateNoticeBoardActivity
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardAdapter
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardData
import com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard.NoticeBoardDetail.NoticeBoardDetailActivity
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommunityFragment : Fragment() {

    private lateinit var noticeBoardAdapter: NoticeBoardAdapter
    private lateinit var noticeBoardDB: DatabaseReference
    private var _binding: FragmentNoticeBoardBinding? = null

    private val noticeBoardList = mutableListOf<NoticeBoardData>()

    private val binding get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNoticeBoardBinding.inflate(inflater, container, false)
        val view = binding.root

        noticeBoardList.clear()
        noticeBoardDB = Firebase.database.reference.child(DBKey.DB_NOTICE_BOARD)
        noticeBoardAdapter = NoticeBoardAdapter(onItemClicked = { noticeBoardData -> // 게시글 클릭 시 이벤트
            val intent = Intent(context, NoticeBoardDetailActivity::class.java)
            intent.putExtra("chatKey", noticeBoardData.key)
            intent.putExtra("title", noticeBoardData.text)
            intent.putExtra("content", noticeBoardData.content)
            startActivity(intent)
        })

        binding.createNoticeBoardButton.setOnClickListener {
            val intent = Intent(context, CreateNoticeBoardActivity::class.java)
            intent.run { startActivity(this) }
        }

        binding.titleRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.titleRecyclerView.adapter = noticeBoardAdapter

        noticeBoardDB.addChildEventListener(listener)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        noticeBoardDB.removeEventListener(listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()

        noticeBoardAdapter.notifyDataSetChanged() // Adapter 에게 RecyclerView 의 리스트 데이터가 바뀌었으니 모든 항목을 통째로 업데이트를 하라는 신호가 간다.
    }

}