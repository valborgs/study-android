package kr.co.lion.android52_picture

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import kr.co.lion.android52_picture.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // Activity 실행을 위한 런처
    lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 촬영된 사진이 저장된 경로 정보를 가지고 있는 Uri 객체
    lateinit var contentUri:Uri

    // 확인할 권한 목록
    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 권한 확인
        requestPermissions(permissionList,0)

        // 사진 촬영을 위한 런처 생성
        val contract1 = ActivityResultContracts.StartActivityForResult()
        cameraLauncher = registerForActivityResult(contract1){
            // 사진을 사용하겠다고 한 다음에 돌아온 경우
            if(it.resultCode == RESULT_OK){
                // 사진 객체를 생성한다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)

                // 회전 각도값을 구한다.
                val degree = getDegree(contentUri)
                // 회전된 이미지를 구한다.
                val bitmap2 = rotateBitmap(bitmap, degree.toFloat())
                // 크기를 조정한 이미지를 구한다.
                val bitmap3 = resizeBitmap(bitmap2, 1024)

                activityMainBinding.imageView.setImageBitmap(bitmap3)

                // 사진 파일을 삭제한다.
                val file = File(contentUri.path)
                file.delete()
            }
        }

        // 앨범 실행을 위한 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract2){
            // 사진 선택을 완료한 후 돌아왔다면
            if(it.resultCode == RESULT_OK){
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data!!
                if(uri != null){
                    // 안드로이드 Q(10) 이상이라면
                    val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        // 이미지를 생성할 수 있는 객체를 생성한다.
                        val source = ImageDecoder.createSource(contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    }else{
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = contentResolver.query(uri, null, null, null, null)
                        if(cursor != null){
                            cursor.moveToNext()
                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성한다.
                            BitmapFactory.decodeFile(source)
                        }else{
                            null
                        }
                    }

                    // 회전 각도값을 가져온다.
                    val degree = getDegree(uri)
                    // 회전 이미지를 가져온다.
                    val bitmap2 = rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = resizeBitmap(bitmap2, 1024)

                    activityMainBinding.imageView.setImageBitmap(bitmap3)
                }
            }
        }

        activityMainBinding.apply {

            // 카메라로 사진 찍기
            // step1) res/xml 폴더에 xml 파일을 만들어주고 이 파일에 사진이 저장될 외부저장소까지의
            // 경로를 기록해준다. (이 예제에서는 res/xml/file_path.xml)
            // step2) AndroidManifest.xml 에 1에서 만든 파일의 경로를 지정해준다.
            button.setOnClickListener {
                // 촬영한 사진이 저장될 경로
                // 외부 저장소 중에 애플리케이션 영역 경로를 가져온다.
                val rootPath = getExternalFilesDir(null).toString()
                // 이미지 파일명을 포함한 경로
                val picPath = "${rootPath}/tempImage.jpg"
                // File 객체 생성
                val file = File(picPath)
                // 사진이 저장될 위치를 관리할 Uri 생성
                // AndroidManifest.xml에 등록한 provider의 authorities
                val a1 = "kr.co.lion.android52_picture.file_provider"
                contentUri = FileProvider.getUriForFile(this@MainActivity, a1, file)

                if(contentUri != null){
                    // 실행할 액티비티를 카메라 액티비티로 지정한다.
                    // 단말기에 설치되어 있는 모든 애플리케이션이 가진 액티비티 중에 사진촬영이
                    // 가능한 액티비가 실행된다.
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    // 이미지가 저장될 경로를 가지고 있는 Uri 객체를 인텐트에 담아준다.
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                    // 카메라 액티비티 실행
                    cameraLauncher.launch(cameraIntent)
                }
            }

            button2.setOnClickListener {
                // 앨범에서 사진을 선택할 수 있도록 셋팅된 인텐트를 생성한다.
                val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // 실행할 액티비티의 타입을 설정(이미지를 선택할 수 있는 것이 뜨게 한다)
                albumIntent.setType("image/*")
                // 선택할 수 있는 파일들의 MimeType을 설정한다.
                // 여기서 선택한 종류의 파일만 선택이 가능하다. 모든 이미지로 설정한다.
                val mimeType = arrayOf("image/*")
                // val mimeType = arrayOf("image/jpeg")
                albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)

                // 액티비티를 실행한다.
                albumLauncher.launch(albumIntent)
            }

        }
    }

    // 사진의 회전 각도값을 반환하는 메서드
    // ExifInterface : 사진, 영상, 소리 등의 파일에 기록한 정보
    // 위치, 날짜, 조리개값, 노출 정도 등 다양한 정보가 기록된다.
    // ExifInterface 정보에서 사진 회전 각도값을 가져와서 그만큼 다시 돌려준다.
    fun getDegree(uri:Uri) : Int {
        // 사진 정보를 가지고 있는 객체를 담을 변수.
        // 사용자가 ExifInterface를 지우면 null이 반환될 수 있다.
        var exifInterface:ExifInterface? = null

        // 안드로이드 os 버전에 따라 분기가 필요하다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // 이미지 데이터를 가져올 수 있는 Content Provider의 Uri를 추출한다.
            // val photoUri = MediaStore.setRequireOriginal(uri) 
            // photoUri는 권한이 없는 객체여서 나중에 실행 시 에러가 나므로 그냥 uri를 사용
            
            // ExifInterface 정보를 읽어올 스트림을 추출한다.
            val inputStream = contentResolver.openInputStream(uri)!!
            // ExifInterface 객체를 생성한다.
            exifInterface = ExifInterface(inputStream)
        } else {
            // ExifInterface 객체를 생성한다.
            exifInterface = ExifInterface(uri.path!!)
        }

        // exifInterface가 null이 아닐때만
        if(exifInterface != null){
            // 반환할 각도 값을 담을 변수
            var degree = 0
            // ExifInterface 객체에서 회전 각도값을 가져온다.
            // defaultValue는 아무값이나 넣어주면 된다.
            var ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

            degree = when(ori){
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }

            return degree
        }

        return 0
    }

    // 회전시키는 메서드
    fun rotateBitmap(bitmap:Bitmap, degree:Float):Bitmap{
        // 회전 이미지를 생성하기 위한 변환 행렬
        val matrix = Matrix()
        matrix.postRotate(degree)

        // 회전 행렬을 적용하여 회전된 이미지를 생성한다.
        // 첫 번째 : 원본 이미지
        // 두번째와 세번째 : 원본 이미지에서 사용할 부분의 좌측상단 x,y 좌표
        // 네번째와 다섯번째 : 원본 이미지에서 사용할 부분의 가로 세로 길이
        // 여기에서는 이미지데이터 전체를 사용할 것이기 때문에 전체 영역으로 잡아준다.
        // 여섯번째 : 변환 행렬. 적용해준 변환행렬이 무엇이냐에 따라 이미지 변형 방식이 달라진다. (matrix.postRotate 회전)
        // 일곱번째 : 필터 적용. 원본 그대로 적용하고자 할 때에는 false를 넣어준다.
        val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

        return rotateBitmap
    }

    // 이미지 사이즈를 조정하는 메서드
    fun resizeBitmap(bitmap:Bitmap, targetWidth:Int):Bitmap{
        // 이미지 확대/축소 비율을 구한다.
        val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
        // 세로 길이를 구한다.
        val targetHeight = (bitmap.height * ratio).toInt()
        // 크기를 조정한 Bitmap을 생성한다.
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

        return resizedBitmap
    }

}