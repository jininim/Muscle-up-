package com.example.teamproject_hometrainingassistant_app.ui.home


import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentHomeBinding
import com.example.teamproject_hometrainingassistant_app.ui.exercise.ExerciseActivity
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import com.example.teamproject_hometrainingassistant_app.ui.home.db.Routine
import com.example.teamproject_hometrainingassistant_app.ui.home.db.RoutineViewModel
import com.example.teamproject_hometrainingassistant_app.ui.home.myroutine.MyRoutineDetailActivity
import com.example.teamproject_hometrainingassistant_app.ui.recommend.RecommendActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var userimage: String
    private lateinit var adapter: HomeAdapter
    private val routineViewModel by lazy {
        ViewModelProvider(this)[RoutineViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            val username = savedInstanceState.getString("username")
            val userimage = savedInstanceState.getString("userimage")
            binding.username.text = username
            Glide.with(this).load(userimage).into(binding.userimage)
        }
        val bundle = arguments
        username = bundle?.getString("USER_NAME").orEmpty()
        userimage = bundle?.getString("USER_IMAGE").orEmpty()
        binding.username.text = username
        Glide.with(this).load(userimage).into(binding.userimage)

        //사용자가 선택한 운동정보 가져오기
        val nameList: java.util.ArrayList<String>? = bundle?.getStringArrayList("NAME_LIST")
//nameList가 null이 아닌경우에만 운동 정보를 저장.
        if (nameList != null) {
            routineViewModel.addProduct(Routine(0, nameList.toString(), false))
        }

            adapter = HomeAdapter(
                onClickUpdate = {
                    routineViewModel.updateProduct(it)
                }
            )
            binding.recyclerView.adapter = adapter
            binding.recyclerView.addItemDecoration(VerticalItemDecorator(0))
//        binding.recyclerView.addOnItemTouchListener(
//            RecyclerItemClickListener(
//                requireContext(),
//                binding.recyclerView,
//                object : RecyclerItemClickListener.OnItemClickListener{
//                    override fun onItemClick(view: View, position: Int) {
//                        val textView = view.findViewById<TextView>(R.id.routine)
//                        val intent = Intent(context, MyRoutineDetailActivity::class.java)
//                        intent.putExtra("textViewText", textView.text.toString())
//                        startActivity(intent)
//                    }
//
//                    override fun onLongItemClick(view: View?, position: Int) {
//
//                    }
//
//                }
//            )
//        )

            routineViewModel.readAllData.observe(
                viewLifecycleOwner
            ) { data ->
                adapter.setData(data)
                binding.fabdel.setOnClickListener {
                    for (i in data) {
                        if (i.check) {
                            routineViewModel.deleteProduct(i)
                        }
                    }
                }
            }

            binding.Search.setOnClickListener {
                val intent = Intent(context, ExerciseActivity::class.java)
                startActivity(intent)
            }

            binding.recommend.setOnClickListener {
                val intent = Intent(context, RecommendActivity::class.java)
                startActivity(intent)
            }

            binding.fabadd.setOnClickListener {
                val intent = Intent(context, ExerciseActivity::class.java)
                startActivity(intent)
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("username", username)
        outState.putString("userimage", userimage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val username = savedInstanceState.getString("username")
            val userimage = savedInstanceState.getString("userimage")
            binding.username.text = username
            Glide.with(this).load(userimage).into(binding.userimage)
        }
    }


    }

