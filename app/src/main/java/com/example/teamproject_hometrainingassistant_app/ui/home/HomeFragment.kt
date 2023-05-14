package com.example.teamproject_hometrainingassistant_app.ui.home


import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentHomeBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseActivity
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.home.db.Routine
import com.example.teamproject_hometrainingassistant_app.ui.home.db.RoutineViewModel
import com.example.teamproject_hometrainingassistant_app.ui.recommend.RecommendActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var username: String? = null
    private var userimage: String? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val routineViewModel =
            ViewModelProvider(this)[RoutineViewModel::class.java]


        //카카오 로그인한 사용자 정보 받아오기
        val bundle = arguments
        username = bundle?.getString("USER_NAME")
        userimage = bundle?.getString("USER_IMAGE")
        binding.username.text = username
        Glide.with(this).load(userimage).into(binding.userimage)


        //사용자가 선택한 운동정보 가져오기
        val nameList: java.util.ArrayList<String>? = bundle?.getStringArrayList("NAME_LIST")
        //nameList가 null이 아닌경우에만 운동 정보를 저장.
        if (nameList != null) {
            routineViewModel.addProduct(Routine(0, nameList.toString(), false))
        }
        //어답터 연결
        val adapter = HomeAdapter(
            onClickUpdate = {
                routineViewModel.updateProduct(it)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(VerticalItemDecorator(0))

        //운동정보가 추가되면 adapter에 아이템을 새로운 아이템으로 변경.
        routineViewModel.readAllData.observe(
            viewLifecycleOwner,
        ) {data->
            adapter.setData(data)
            //fabdel 버튼 클릭시
            binding.fabdel.setOnClickListener {
                for (i in data) {
                    if (i.check) {
                        routineViewModel.deleteProduct(i)
                    }
                }
            }
        }




        //운동 검색 버튼 클릭 시
        binding.Search.setOnClickListener {
            val intent = Intent(context, ExerciseActivity::class.java)
            startActivity(intent) //인트로 실행 후 바로 exerciseActivity로 넘어감.

        }

        //운동 추천 버튼 클릭 시
        binding.recommend.setOnClickListener {
            val intent = Intent(context, RecommendActivity::class.java)
            startActivity(intent)
        }
        //fab버튼 클릭 시
        binding.fabadd.setOnClickListener {
            val intent = Intent(context, ExerciseActivity::class.java)
            startActivity(intent)
        }






        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}