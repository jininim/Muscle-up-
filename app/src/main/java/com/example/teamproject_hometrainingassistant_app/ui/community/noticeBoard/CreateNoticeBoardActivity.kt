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
    private var selectedUri: Uri? = null // 선택된 사진
    private val storage: FirebaseStorage by lazy { // firebase 스토리지
        Firebase.storage
    }
    private val articleDB: DatabaseReference by lazy { // firebase 저장 경로
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
                    android.Manifest.permission.READ_EXTERNAL_STORAGE // Manifest에서 갤러리 읽기 쓰기 권한 받았는지 체크
                ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> { // 권한을 거부했을 경우
                    showPermissionContextPopup()
                }
                else -> { // 체크를 이미 했었다면 바로 갤러리 실행
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1010
                    )
                }
            }
        }

        binding.submitButton.setOnClickListener { // 게시글 올리기 버튼
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val key = System.currentTimeMillis()

            showProgress() // 로딩창 띄우기
            if (selectedUri != null) { // 선택한 사진이 있다면
                val photoUri = selectedUri ?: return@setOnClickListener // 선택한 사진을 photoUri에 삽입 만약 null이면 return
                uploadPhoto(photoUri,  // 사진을 스토리지에 업로드 성공 시
                    successHandler = { uri ->
                        uploadNoticeBoard(title, content, uri, key) // 게시글 업로드 실행
                    },
                    errorHandler = { // 업로드 실패 시
                        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        hideProgress()
                    }
                )
            } else {
                uploadNoticeBoard(title, content, "", key) // 선택한 사진이 없다면 uri를 null로 전달하며 게시글 업로드 실행
            }
        }

        binding.exerciseBackButton.setOnClickListener { // 뒤로가기 버튼
            finish()
        }
    }

    private fun uploadPhoto( // 사진 업로드 함수
        uri: Uri,                           // 사진 uri
        successHandler: (String) -> Unit,   // 업로드 성공 핸들러
        errorHandler: () -> Unit            // 업로드 실패 핸들러
    ) {
        val fileName = "${System.currentTimeMillis()}.png" // 중복 방지를 위해 현재 시간을 ms으로 변환한 값을 파일명으로 설정.
        storage.reference.child("noticeBoard/photo").child(fileName) // firebase storage 저장 경로 설정.
            .putFile(uri) // uri 넣기
            .addOnCompleteListener {
                if (it.isSuccessful) { // uri put이 성공했다면 실제로 uri에 해당하는 사진을 업로드 시작.
                    storage.reference.child("noticeBoard/photo").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { uri ->      // 성공 시 성공 핸들러에 해당하는 일 시작.
                            successHandler(uri.toString())
                        }.addOnFailureListener {            // 실패 시 실패 핸들러에 해당하는 일 시작.
                            errorHandler()
                        }
                } else {
                    errorHandler() // put에 실패했다면 에러핸들러 실행.
                }
            }
    }

    private fun uploadNoticeBoard( // 게시글 업로드 함수
        title: String,      // 제목
        content: String,    // 내용
        imageUri: String,   // 사진 uri
        key: Long           // 게시글 고유 키값
    ) { // 게시글 업로드
        val model = NoticeBoardData(title, content, imageUri, key) // 게시글 데이터 형식으로 받아온 값들을 model에 저장
        articleDB.push().setValue(model) // 최종적으로 DB에 푸쉬

        hideProgress() // 로딩창 숨기기
        finish()
    }

    override fun onRequestPermissionsResult( // 권한 체크 시 그 결과를 확인하는 함수
        requestCode: Int,               // 요청할 때 보낸 코드
        permissions: Array<out String>,
        grantResults: IntArray          // 요청에 ok했을 때의 정보를 갖음.
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) { // 요청할 때 보낸 코드가 1010이면
            1010 ->
                    if (grantResults.isNotEmpty()) { // 요청 결과에 ok가 있다면
                        startContentProvider() // 갤러리 실행
                    } else { // 요청 결과에 ok가 없다면
                        Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult( // 갤러리 실행 후 결과를 체크하는 함수
        requestCode: Int, // 갤러리 실행 시 보낸 코드
        resultCode: Int,  // 사진 가져오기가 성공 시 갖는 코드
        data: Intent?     // 사진
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) { // 성공하지 못했다면
            return
        }

        when (requestCode) { // 갤러리 실행 시 보낸 코드가 2020일 때
            2020 -> {
                val uri = data?.data // 선택한 사진을 uri에 저장
                if (uri != null) { // 선택한 사진이 null이 아니라면
                    binding.photoImageView.setImageURI(uri) // 게시글 만들기 화면에 사진 띄우기
                    selectedUri = uri // 선택한 사진 변수 selectedUri에도 uri저장
                } else { // null이라면
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> { // 2020이 아닐 때
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startContentProvider() { // 갤러리 띄우기
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