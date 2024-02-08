package kr.co.lion.android31_br

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android31_br.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val permissionList = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.RECEIVE_BOOT_COMPLETED,
        Manifest.permission.READ_PHONE_STATE
    )

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        requestPermissions(permissionList, 0)
    }
}