package com.example.teamproject_hometrainingassistant_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인버튼 클릭시 메인화면 전환
        binding.kakaologin.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent) //인트로 실행 후 바로 MainActivity로 넘어감.
            finish()
        }
    }
}