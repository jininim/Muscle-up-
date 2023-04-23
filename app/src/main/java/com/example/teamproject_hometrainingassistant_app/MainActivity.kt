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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //카카오 로그인정보 받아오기
        val username = intent.getStringExtra("USER_NAME")
        val userimage = intent.getStringExtra("USER_IMAGE")

//        선택한 운동 정보 가져오기
        val nameList = intent.getStringArrayListExtra("NAME_LIST")
        val fragment = HomeFragment()
        var bundle = Bundle().apply {
            putString("USER_NAME", username)
            putString("USER_IMAGE", userimage)
            putStringArrayList("NAME_LIST", nameList)
        }
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()


        // 앱 바
        setSupportActionBar(binding.toolbar2)
        //toolbar에 표시되는 제목의 표시 유무 False로 해야 툴바의 이름 화면에 표시
        supportActionBar?.setDisplayShowTitleEnabled(false)


        val navView: BottomNavigationView = binding.navView
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    bundle = Bundle().apply {
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
        setContentView(binding.root)

    }
}