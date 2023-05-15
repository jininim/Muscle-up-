package com.example.teamproject_hometrainingassistant_app.ui.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.teamproject_hometrainingassistant_app.R
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentDashboardBinding
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.Decorator.VerticalItemDecorator
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardAdapter: DashboardAdapter
    private val datas = mutableListOf<DashboardRoutineData>()
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //현재 날짜 가져오기
        val today = Calendar.getInstance()
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        //현재 날짜 표기
        binding.calendarDate.text = "${month + 1}월 ${day}일"

        //다른 날짜 선택 시
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.calendarDate.text = "${month + 1}월 ${dayOfMonth}일"
            val selectedDate = "${month+1}${dayOfMonth}"
            // SharedPreferences 객체 생성
            val sharedPreferences = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            // 선택된 날짜로부터 데이터 가져오기
            val data = sharedPreferences.getString(selectedDate, null)
            binding.edText.setText(data)

            binding.saveButton.setOnClickListener {
                // SharedPreferences 객체 생성
                val sharedPreferences =
                    requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                // 에디터 객체 생성
                val editor = sharedPreferences.edit()
                //작성한 내용 저장
                val memo = binding.edText.text.toString()
                // 선택된 날짜를 키로 사용하여 데이터 저장
                editor.putString(selectedDate, memo)

                // 변경 사항 적용
                editor.apply()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

