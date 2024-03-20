package kr.co.lion.androidproject4boardapp.dao

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.core.OrderBy
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.androidproject4boardapp.ContentState
import kr.co.lion.androidproject4boardapp.ContentType
import kr.co.lion.androidproject4boardapp.model.ContentModel
import kr.co.lion.androidproject4boardapp.model.UserModel
import java.io.File

class ContentDao {

    companion object{

        // 이미지 데이터를 firebase storage에 업로드하는 메서드
        suspend fun uploadImage(context:Context, fileName:String, uploadFileName:String){
            // 외부저장소 까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 서버로 업로드할 파일의 경로
            val file = File("${filePath}/${fileName}")
            val uri = Uri.fromFile(file)

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // Storage에 접근할 수 있는 객체를 가져온다.(폴더의 이름과 파일이름을 저장해준다.
                val storageRef = Firebase.storage.reference.child("image/$uploadFileName")
                // 업로드한다.
                storageRef.putFile(uri)
            }

            job1.join()
        }

        // 이미지 데이터를 받아오는 메서드
        suspend fun gettingContentImage(context:Context, imageFileName:String, imageView: ImageView){
            CoroutineScope(Dispatchers.IO).launch {
                // 이미지에 접근할 수 있는 객체를 가져온다.
                val storageRef = Firebase.storage.reference.child("image/$imageFileName")
                // 이미지의 주소를 가지고 있는 Uri 객체를 받아온다.
                val imageUri = storageRef.downloadUrl.await()
                // 이미지 데이터를 받아와 이미지 뷰에 보여준다.
                CoroutineScope(Dispatchers.Main).launch {
                    Glide.with(context).load(imageUri).into(imageView)
                    // 이미지 뷰가 나타나게 한다.
                    imageView.visibility = View.VISIBLE
                }
            }

            // 이미지는 용량이 매우 클 수 있다. 즉 이미지 데이터를 내려받는데 시간이 오래걸릴 수도 있다.
            // 이에, 이미지 데이터를 받아와 보여주는 코루틴을 작업이 끝날 때 까지 대기 하지 않는다.
            // 그 이유는 데이터를 받아오는데 걸리는 오랜 시간 동안 화면에 아무것도 나타나지 않을 수 있기 때문이다.
            // 따라서 이 메서드는 제일 마지막에 호출해야 한다.(다른 것들을 모두 보여준 후에...)
        }


        // 게시글 번호 시퀀스값을 가져온다.
        suspend fun getContentSequence():Int{

            var contentSequence = -1

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시글 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("ContentSequence")
                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                contentSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()

            return contentSequence
        }

        // 게시글 시퀀스 값을 업데이트 한다.
        suspend fun updateContentSequence(contentSequence:Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시글 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("ContentSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = contentSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 게시글 정보를 저장한다.
        suspend fun insertContentData(contentModel: ContentModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ContentData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(contentModel)
            }
            job1.join()
        }

        // 글 번호를 이용해 글 데이터를 가져와 반환한다.
        suspend fun selectContentData(contextIdx:Int):ContentModel?{

            var contentModel:ContentModel? = null

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ContentData")
                // 컬렉션이 가지고 있는 문서들 중에 contentIdx 필드가 지정된 글 번호값하고 같은 Document들을 가져온다.
                val querySnapshot = collectionReference.whereEqualTo("contentIdx", contextIdx).get().await()
                // 가져온 글 정보를 객체에 담아서 반환 받는다.
                // contentIdx가 같은 글은 존재할 수가 없기 때문에 첫 번째 객체를 바로 추출해서 사용한다.
                // toObject : 지정한 클래스를 가지고 객체를 만든 다음 가져온 데이터의 필드의 이름과 동일한 이름의
                // 프로퍼티에 필드의 값을 담아준다.
                contentModel = querySnapshot.documents[0].toObject(ContentModel::class.java)
            }
            job1.join()

            return contentModel
        }

        // 게시글 목록을 가져온다.
        suspend fun gettingContentList(contentType:Int):MutableList<ContentModel>{
            // 게시글 정보를 담을 리스트
            val contentList = mutableListOf<ContentModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ContentData")
                // 게시글 상태가 정상
                var query = collectionReference.whereEqualTo("contentState", ContentState.CONTENT_STATE_NORMAL.number)
                // 게시글 번호를 기준으로 내림차순 정렬
                query = query.orderBy("contentIdx",Query.Direction.DESCENDING)
                // 만약 전체 게시판이 아니라면
                if(contentType != ContentType.TYPE_ALL.number){
                    query = query.whereEqualTo("contentType", contentType)
                }
                val querySnapshot = query.get().await()
                querySnapshot.forEach {
                    // 현재 번째의 문서를 객체로 받아온다.
                    val contentModel = it.toObject(ContentModel::class.java)
                    // 객체를 리스트에 담는다.
                    contentList.add(contentModel)
                }
            }
            job1.join()

            return contentList
        }

    }
}








