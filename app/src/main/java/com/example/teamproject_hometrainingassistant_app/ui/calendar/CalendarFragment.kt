package com.example.teamproject_hometrainingassistant_app.ui.calendar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_USER
import com.example.teamproject_hometrainingassistant_app.databinding.FragmentCalendarBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private lateinit var userName: String
    private lateinit var userImage: String
    private lateinit var userDB: DatabaseReference
    private var routineList: MutableList<List<String>>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //현재 날짜 가져오기
        val today = Calendar.getInstance()
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)
        //현재 시간 가져오기
        val time = getCurrentKoreaTime()
        binding.editTime.setText(time)

        val sharedPreferences = requireActivity().getSharedPreferences("kakao", AppCompatActivity.MODE_PRIVATE)

        userName = sharedPreferences.getString("USER_NAME", "") ?: ""
        userImage = sharedPreferences.getString("USER_IMAGE", "") ?: ""

        userDB = FirebaseDatabase.getInstance().reference.child(DB_USER).child("${month+1}").child("$day")

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val routineList = mutableListOf<List<String>>()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })



        //수행한 운동정보 가져오기
        val bundle = arguments
        val itemList: ArrayList<String>? = bundle?.getStringArrayList("ITEM_LIST")
        if (itemList != null){
            binding.editName.setText(itemList.joinToString(","))
            //현재 시간 가져오기
            val time = getCurrentKoreaTime()
            binding.editTime.setText(time)
            // SharedPreferences 객체 생성
            val sharedPreferences2 =
                requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            // 에디터 객체 생성
            val editor = sharedPreferences2.edit()
            //작성한 내용 저장
            val memo = binding.editName.text.toString()
            val memoTime = binding.editTime.text.toString()
            // 선택된 날짜를 키로 사용하여 데이터 저장
            editor.putString("${month + 1}${day}", memo)
            editor.putString("${month}${day}", memoTime)

            // 변경 사항 적용
            editor.apply()
        }

        //현재 날짜 표기
        binding.calendarDate.text = "${month + 1}월 ${day}일"

        //다른 날짜 선택 시
        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            binding.calendarDate.text = "${month + 1}월 ${dayOfMonth}일"
            val selectedDate = "${month+1}${dayOfMonth}"
            val selectedDate2 = "${month}${dayOfMonth}"
            // SharedPreferences 객체 생성
            val sharedPreferences3 = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            // 선택된 날짜로부터 데이터 가져오기
            val data = sharedPreferences3.getString(selectedDate, null)
            val timeData = sharedPreferences3.getString(selectedDate2, null)
            binding.editName.setText(data)
            binding.editTime.setText(timeData)
        }
    }
    private fun getCurrentKoreaTime(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return String.format("%02d:%02d:%02d", hour, minute, second)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


