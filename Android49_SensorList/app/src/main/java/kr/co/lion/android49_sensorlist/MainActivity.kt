package kr.co.lion.android49_sensorlist

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android49_sensorlist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            textView.apply {
                // 센서를 관리하는 객체를 추출한다.
                val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                // 단말기에 있는 센서 목록을 가져온다.
                val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

                text = ""

                sensorList.forEach {
                    append("센서 이름 : ${it.name}\n")
                    append("센서 제조사 : ${it.vendor}\n")
                    append("센서 종류 : ${it.type}\n\n")
                }
            }
        }
    }
}