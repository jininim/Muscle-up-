package com.example.teamproject_hometrainingassistant_app.ui.community.noticeBoard

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.teamproject_hometrainingassistant_app.DBKey.Companion.DB_NOTICE_BOARD
import com.example.teamproject_hometrainingassistant_app.databinding.ActivityCreateNoticeBoardBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class CreateNoticeBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoticeBoardBinding
    private var selectedUri: Uri? = null
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }
    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_NOTICE_BOARD)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNoticeBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageAddButton.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission( // 사진 갤러리 사용 권한 체크
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> { // 권한을 거부했을 경우
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1010
                    )
                }
            }
        }

        binding.submitButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val key = System.currentTimeMillis()

            showProgress()
            if (selectedUri != null) {
                val photoUri = selectedUri ?: return@setOnClickListener
                uploadPhoto(photoUri,
                    successHandler = { uri ->
                        uploadNoticeBoard(title, content, uri, key)
                    },
                    errorHandler = {
                        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )
            } else {
                uploadNoticeBoard(title, content, "", key)
            }
        }

        binding.exerciseBackButton.setOnClickListener {
            finish()
        }
    }

    private fun uploadPhoto(
        uri: Uri,
        successHandler: (String) -> Unit,
        errorHandler: () -> Unit
    ) { // 사진 추가 버튼 눌렀을 때
        val fileName = "${System.currentTimeMillis()}.png"
        storage.reference.child("noticeBoard/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    storage.reference.child("noticeBoard/photo").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { uri ->
                            successHandler(uri.toString())
                        }.addOnFailureListener {
                            errorHandler()
                        }
                } else {
                    errorHandler()
                }
            }
    }

    private fun uploadNoticeBoard(
        title: String,
        content: String,
        imageUri: String,
        key: Long
    ) { // 게시글 업로드
        val model = NoticeBoardData(title, content, imageUri, key)
        articleDB.push().setValue(model)

        hideProgress()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 ->
                    if (grantResults.isNotEmpty()) {
                        startContentProvider()
                    } else {
                        Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            2020 -> {
                val uri = data?.data
                if (uri != null) {
                    binding.photoImageView.setImageURI(uri)
                    selectedUri = uri
                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)
    }

    private fun showProgress() { // 로딩창 o
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() { // 로딩창 x
        binding.progressBar.isVisible = false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() { // 권한 동의x 를 누른 후 띄워지는 팝업
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }
}