package com.example.teamproject_hometrainingassistant_app

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 앱 바
        setSupportActionBar(binding.toolbar2)
        //toolbar에 표시되는 제목의 표시 유무 False로 해야 툴바의 이름 화면에 표시
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        //왼쪽 버튼 사용 설정
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)
    }
}