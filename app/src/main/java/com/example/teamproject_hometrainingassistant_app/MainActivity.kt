package com.example.teamproject_hometrainingassistant_app

import android.content.Intent
import android.os.Bundle

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMainBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityFragment
import com.example.teamproject_hometrainingassistant_app.ui.calendar.CalendarFragment
import com.example.teamproject_hometrainingassistant_app.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userName: String
    private lateinit var userImage: String
    private var itemList : ArrayList<String>? = null
    private var nameList: ArrayList<String>? = null
    private var timeList: ArrayList<String>? = null
    private var urlList: ArrayList<String>? = null
    override fun onRestart() {
        super.onRestart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // 카카오 로그인정보 받아오기
        val sharedPreferences = getSharedPreferences("kakao", MODE_PRIVATE)

        userName = sharedPreferences.getString("USER_NAME", "") ?: ""
        userImage = sharedPreferences.getString("USER_IMAGE", "") ?: ""

        // 선택한 운동 정보 가져오기
        nameList = intent.getStringArrayListExtra("NAME_LIST")
        timeList = intent.getStringArrayListExtra("TIME_LIST")
        urlList = intent.getStringArrayListExtra("URL_LIST")

        //운동 종료시 사용할 운동 정보 리스트
        itemList = intent.getStringArrayListExtra("ITEM_LIST")



        // 앱 바
        setSupportActionBar(binding.toolbar2)
        // toolbar에 표시되는 제목의 표시 유무 False로 해야 툴바의 이름 화면에 표시
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.BackButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment()
                    val bundle = Bundle().apply {
                        putString("USER_NAME", userName)
                        putString("USER_IMAGE", userImage)
                        putStringArrayList("NAME_LIST", nameList)
                        putStringArrayList("TIME_LIST", timeList)
                        putStringArrayList("URL_LIST", urlList)
                    }
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, fragment)
                        .commit()
                    nameList = null
                    timeList = null
                    urlList = null
                }
                R.id.navigation_dashboard -> {
                    val calendarFragment = CalendarFragment()
                    val bundle = Bundle().apply {
                        putStringArrayList("ITEM_LIST", itemList)
                    }
                    calendarFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, calendarFragment)
                        .commit()
                }
                else -> {
                    val comFragment = CommunityFragment()
                    val bundle = Bundle().apply {
                        putString("USER_NAME", userName)
                    }
                    comFragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, comFragment)
                        .commit()
                }
            }
            true
        }

        if (savedInstanceState == null) {
            // 앱을 처음 실행하는 경우 HomeFragment를 보여준다.
            navView.selectedItemId = R.id.navigation_home
        } else {
            // 앱이 이전에 실행되었던 경우에는 저장된 상태를 복원한다.
            userName = savedInstanceState.getString("USER_NAME").orEmpty()
            userImage = savedInstanceState.getString("USER_IMAGE").orEmpty()
            nameList = savedInstanceState.getStringArrayList("NAME_LIST")

            // 현재 보여지는 fragment를 찾아서 화면에 다시 그려준다.
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
            currentFragment?.arguments = savedInstanceState.getBundle("FRAGMENT_ARGS")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // 현재 보여지는 fragment를 찾아서 인자 값을 저장한다.
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
        val fragmentArgs = currentFragment?.arguments ?: Bundle()

        outState.run {
            putString("USER_NAME", userName)
            putString("USER_IMAGE", userImage)
            putStringArrayList("NAME_LIST", nameList)
            putBundle("FRAGMENT_ARGS", fragmentArgs)
        }

        super.onSaveInstanceState(outState)
    }


}