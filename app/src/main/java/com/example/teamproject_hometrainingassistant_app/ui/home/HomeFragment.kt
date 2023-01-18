package com.example.teamproject_hometrainingassistant_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.teamproject_hometrainingassistant_app.MainActivity
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentHomeBinding
import com.example.teamproject_hometrainingassistant_app.ui.exerciseActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        //운동검색 버튼 클릭 시
        binding.Search.setOnClickListener {
                val intent = Intent(context, exerciseActivity::class.java)
                startActivity(intent) //인트로 실행 후 바로 exerciseActivity로 넘어감.

        }

        //운동 추천 버튼 클릭 시
        binding.recommend.setOnClickListener {

        }
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}