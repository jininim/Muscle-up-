package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentDashboardBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator

class DashboardFragment : Fragment() {

    lateinit var dashboardAdapter: DashboardAdapter
    val datas = mutableListOf<DashboardRoutineData>()
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        initRecycler()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(){
        dashboardAdapter = DashboardAdapter(this)
        binding.recyclerView.adapter = dashboardAdapter
        binding.recyclerView.addItemDecoration(VerticalItemDecorator(0))
        datas.apply {
            add(DashboardRoutineData(img = R.drawable.ic_photo, text = "루틴 1"))
            add(DashboardRoutineData(img = R.drawable.ic_photo, text = "루틴 2"))
            add(DashboardRoutineData(img = R.drawable.ic_photo, text = "루틴 3"))
            dashboardAdapter.datas = datas
            dashboardAdapter.notifyDataSetChanged()
        }

    }

}