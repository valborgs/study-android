package kr.co.lion.android51_compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android51_compass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 가속도 센서로 측정한 값을 담을 배열을 담을 프로퍼티
    val accValues = floatArrayOf(0.0f, 0.0f, 0.0f)
    // 자기장 센서로 측정한 값을 담을 배열을 담을 프로퍼티
    val magValues = floatArrayOf(0.0f, 0.0f, 0.0f)

    // 센서로부터 값이 측정된 적이 있는지
    var isGetAcc = false
    var isGetMag = false

    // 각 센서들의 리스너
    var accelerometerSensorListener:AccelerometerSensorListener? = null
    var magneticSensorListener:MagneticSensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {
            button.setOnClickListener {
                if(accelerometerSensorListener == null && magneticSensorListener == null){
                    // 센서 관리 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 센서 객체를 가져온다.
                    val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
                    // 리스너 생성
                    accelerometerSensorListener = AccelerometerSensorListener()
                    magneticSensorListener = MagneticSensorListener()
                    // 센서에 리스너를 연결한다.
                    sensorManager.registerListener(accelerometerSensorListener, accSensor, SensorManager.SENSOR_DELAY_UI)
                    sensorManager.registerListener(magneticSensorListener, magSensor, SensorManager.SENSOR_DELAY_UI)
                }
            }

            button2.setOnClickListener {
                if(accelerometerSensorListener != null && magneticSensorListener != null){
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

                    // 센서 리스너를 해제한다.
                    sensorManager.unregisterListener(accelerometerSensorListener)
                    sensorManager.unregisterListener(magneticSensorListener)

                    accelerometerSensorListener = null
                    magneticSensorListener = null
                }
            }
        }
    }

    // 가속도 센서의 리스너
    inner class AccelerometerSensorListener : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null){
                // 측정된 값이 담긴 배열을 담아준다.
                accValues[0] = event.values[0]
                accValues[1] = event.values[1]
                accValues[2] = event.values[2]

                isGetAcc = true

                getAzimuth()
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

    }

    // 자기장 센서의 리스너
    inner class MagneticSensorListener : SensorEventListener{
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null){
                // 측정된 값이 담긴 배열을 담아준다.
                magValues[0] = event.values[0]
                magValues[1] = event.values[1]
                magValues[2] = event.values[2]

                isGetMag = true

                getAzimuth()
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

    }

    // 방위값을 계산하는 메서드
    fun getAzimuth(){
        // 두 센서 모두 값이 측정된 적이 있을 경우에만
        if(isGetAcc && isGetMag){
            // 방위값 등을 계싼하기 위한 계산 행렬
            val R = FloatArray(9)
            val I = FloatArray(9)
            // 계산 행렬을 구한다.
            SensorManager.getRotationMatrix(R, I, accValues, magValues)
            // 방위값을 추출한다.
            val values = FloatArray(3)
            SensorManager.getOrientation(R, values)

            // 결과가 라디언 값으로 나오기 때문에 각도 값으로 변환한다.
            var azimuth = Math.toDegrees(values[0].toDouble()) // 방위
            var pitch = Math.toDegrees(values[1].toDouble()) // 좌우 기울기 값
            var roll = Math.toDegrees(values[2].toDouble()) // 앞뒤 기울기 값

            // 만약 방위값이 음수가 나온다면 360을 더해준다.
            if(azimuth<0){
                azimuth = azimuth + 360
            }

            activityMainBinding.apply {
                textView.text = "방위값 : $azimuth"
                textView2.text = "좌우 기울기 값 : $pitch"
                textView3.text = "앞뒤 기울기 값 : $roll"

                // 이미지 뷰 회전
                imageView.rotation = (360 - azimuth).toFloat()
            }
        }
    }
}