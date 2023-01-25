package com.example.teamproject_hometrainingassistant_app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentHomeBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseActivity
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.recommend.RecommendActivity


class HomeFragment : Fragment() {

    lateinit var homeAdapter: HomeAdapter
    val datas = mutableListOf<HomeData>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //운동검색 버튼 클릭 시
        binding.Search.setOnClickListener {
            val intent = Intent(context, ExerciseActivity::class.java)
            startActivity(intent) //인트로 실행 후 바로 exerciseActivity로 넘어감.
        }
        //운동 추천 버튼 클릭 시
        binding.recommend.setOnClickListener {
            val intent = Intent(context, RecommendActivity::class.java)
            startActivity(intent)
        }
        //어답터 연결
        homeAdapter = HomeAdapter(this)
        binding.recyclerView.adapter = homeAdapter
        binding.recyclerView.addItemDecoration(VerticalItemDecorator(0))
        //아이템 클리어
        datas.clear()
        initRecycler()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(){
        datas.apply {
            add(HomeData(img = R.drawable.ic_photo, text = "루틴1", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴2", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴3", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴4", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴5", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴6", img2 = R.drawable.ic_menu))
            add(HomeData(img = R.drawable.ic_photo, text = "루틴7", img2 = R.drawable.ic_menu))
            homeAdapter.datas = datas
            homeAdapter.notifyDataSetChanged()
        }
    }
}