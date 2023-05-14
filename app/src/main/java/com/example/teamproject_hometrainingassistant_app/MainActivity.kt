package com.example.teamproject_hometrainingassistant_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMainBinding
import com.example.teamproject_hometrainingassistant_app.ui.community.CommunityFragment
import com.example.teamproject_hometrainingassistant_app.ui.dashboard.DashboardFragment
import com.example.teamproject_hometrainingassistant_app.ui.home.HomeFragment
import com.google.android.material.navigation.NavigationBarView
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var username: String
    private lateinit var userimage: String
    private var nameList: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 카카오 로그인정보 받아오기
        username = intent.getStringExtra("USER_NAME") ?: ""
        userimage = intent.getStringExtra("USER_IMAGE") ?: ""

        // 선택한 운동 정보 가져오기
        nameList = intent.getStringArrayListExtra("NAME_LIST")

        // 앱 바
        setSupportActionBar(binding.toolbar2)
        // toolbar에 표시되는 제목의 표시 유무 False로 해야 툴바의 이름 화면에 표시
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    val fragment = HomeFragment()
                    val bundle = Bundle().apply {
                        putString("USER_NAME", username)
                        putString("USER_IMAGE", userimage)
                        putStringArrayList("NAME_LIST", nameList)
                    }
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, fragment)
                        .commit()
                }
                R.id.navigation_dashboard -> {
                    val dashboardFragment = DashboardFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, dashboardFragment)
                        .commit()
                }
                else -> {
                    val comFragment = CommunityFragment()
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
            username = savedInstanceState.getString("USER_NAME").orEmpty()
            userimage = savedInstanceState.getString("USER_IMAGE").orEmpty()
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
            putString("USER_NAME", username)
            putString("USER_IMAGE", userimage)
            putStringArrayList("NAME_LIST", nameList)
            putBundle("FRAGMENT_ARGS", fragmentArgs)
        }

        super.onSaveInstanceState(outState)
    }


}