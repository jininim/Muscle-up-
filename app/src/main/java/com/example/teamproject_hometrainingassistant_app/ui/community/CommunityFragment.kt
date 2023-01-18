package com.example.teamproject_hometrainingassistant_app.ui.community

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentCommunityBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator

class CommunityFragment : Fragment() {

    lateinit var communityAdapter: CommunityAdapter
    val datas = mutableListOf<CommunityData>()
    private var _binding: FragmentCommunityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(CommunityViewModel::class.java)

        _binding = FragmentCommunityBinding.inflate(inflater, container, false)

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        initRecycler()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(){
        communityAdapter = CommunityAdapter(this)
        binding.recyclerView.adapter = communityAdapter
        binding.recyclerView.addItemDecoration(VerticalItemDecorator(0))

        datas.apply {
            add(CommunityData(img = R.drawable.ic_photo, text = "내가 쓴 글"))
            add(CommunityData(img = R.drawable.ic_photo, text = "댓글 단 글"))
            add(CommunityData(img = R.drawable.ic_photo, text = "즐겨찾기"))
            add(CommunityData(img = R.drawable.ic_photo, text = "핫 게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "베스트 게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "자유게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "질문게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "운동정보게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "오운완게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "꿀팁게시판"))
            add(CommunityData(img = R.drawable.ic_photo, text = "루틴추천게시판"))
            communityAdapter.datas = datas
            communityAdapter.notifyDataSetChanged()
        }
    }
}