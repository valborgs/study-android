package kr.co.lion.android50_sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.co.lion.android50_sensor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 조도 센서의 리스너를 담을 프로퍼티
    var lightSensorListener:LightSensorListener? = null
    // 기압 센서의 리스너를 담을 프로퍼티
    var pressureSensorListener:PressureSensorListener? = null
    // 근접 센서의 리스너를 담을 프로퍼티
    var proximitySensorListener:ProximitySensorListener? = null
    // 자이로 스코프 센서의 리스너를 담을 프로퍼티
    var gyroscopeSensorListener:GyroscopeSensorListener? = null
    // 가속도 센서의 리스너를 담을 프로퍼티
    var accelerometerSensorListener:AccelerometerSensorListener? = null
    // 마그네틱 센서의 리스너를 담을 프로퍼티
    var magneticSensorListener:MagneticSensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {

            button.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(lightSensorListener == null){
                    button.text = "조도 센서 해제"
                    // 리스너 객체를 생성
                    lightSensorListener = LightSensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 조도 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
                    // 조도 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(lightSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        lightSensorListener = null
                        textView.text = "조도 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button.text = "조도 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 조도 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(lightSensorListener)

                    lightSensorListener = null
                }
            }

            button2.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(pressureSensorListener == null){
                    button2.text = "기압 센서 해제"
                    // 리스너 객체를 생성
                    pressureSensorListener = PressureSensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 기압 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
                    // 기압 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(pressureSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        pressureSensorListener = null
                        textView.text = "기압 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button2.text = "기압 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 기압 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(pressureSensorListener)

                    pressureSensorListener = null
                }
            }

            button3.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(proximitySensorListener == null){
                    button3.text = "근접 센서 해제"
                    // 리스너 객체를 생성
                    proximitySensorListener = ProximitySensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 근접 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
                    // 근접 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(proximitySensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        proximitySensorListener = null
                        textView.text = "근접 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button3.text = "근접 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 근접 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(proximitySensorListener)

                    proximitySensorListener = null
                }
            }

            button4.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(gyroscopeSensorListener == null){
                    button4.text = "자이로 스코프 센서 해제"
                    // 리스너 객체를 생성
                    gyroscopeSensorListener = GyroscopeSensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 자이로 스코프 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
                    // 자이로 스코프 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(gyroscopeSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        gyroscopeSensorListener = null
                        textView.text = "자이로 스코프 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button4.text = "자이로 스코프 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 자이로 스코프 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(gyroscopeSensorListener)

                    gyroscopeSensorListener = null
                }
            }

            button5.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(accelerometerSensorListener == null){
                    button5.text = "가속도 센서 해제"
                    // 리스너 객체를 생성
                    accelerometerSensorListener = AccelerometerSensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 가속도 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                    // 가속도 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(accelerometerSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        accelerometerSensorListener = null
                        textView.text = "가속도 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button5.text = "가속도 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 가속도 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(accelerometerSensorListener)

                    accelerometerSensorListener = null
                }
            }

            button6.setOnClickListener {
                // 리스너 연결이 되어 있지 않을 경우
                if(magneticSensorListener == null){
                    button6.text = "마그네틱 센서 해제"
                    // 리스너 객체를 생성
                    magneticSensorListener = MagneticSensorListener()
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 마그네틱 필드 센서 객체를 가져온다.
                    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
                    // 마그네틱 필드 센서와 리스너를 연결한다.
                    // 첫 번째 : 리스너
                    // 두 번째 : 연결할 센서 객체
                    // 세 번째 : 데이터를 받아올 주기
                    // 반환값 : 센서 연결에 성공했는지 여부
                    val chk = sensorManager.registerListener(magneticSensorListener, sensor, SensorManager.SENSOR_DELAY_UI)
                    // 센서 연결에 실패했다면
                    if(chk==false){
                        magneticSensorListener = null
                        textView.text = "마그네틱 필드 센서를 지원하지 않습니다."
                    }
                }
                // 리스너가 연결되어 있을 경우
                else {
                    button6.text = "마그네틱 센서 연결"
                    // 센서들을 관리하는 객체를 추출한다.
                    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                    // 마그네틱 필드 센서의 리스너를 해제한다.
                    sensorManager.unregisterListener(magneticSensorListener)

                    magneticSensorListener = null
                }
            }

        }
    }

    // 조도 센서의 리스너
    inner class LightSensorListener : SensorEventListener{
        // 센서에 변화가 일어날 때 호출되는 메서드
        // 이 메서드에서 측정된 값을 가져올 수 있다.
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // 조도 값을 가지고 온다.
                val a1 = event.values[0]
                activityMainBinding.textView.text = "주변 밝기 : $a1 lux"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 공기압 센서의 리스너
    inner class PressureSensorListener : SensorEventListener {
        // 센서에 변화가 일어날 때 호출되는 메서드
        // 이 메서드에서 측정된 값을 가져올 수 있다.
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // 공기압 값을 가지고 온다.
                val a1 = event.values[0]
                activityMainBinding.textView.text = "현재 기압 : $a1 millibar"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 근접 센서의 리스너
    inner class ProximitySensorListener : SensorEventListener {
        // 센서에 변화가 일어날 때 호출되는 메서드
        // 이 메서드에서 측정된 값을 가져올 수 있다.
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // 물체와의 거리를 가지고 온다.
                val a1 = event.values[0]
                activityMainBinding.textView.text = "물체와의 거리 : $a1 cm"
            }
        }

        // 센서의 감도가 변경되었을 때 호출되는 메서드
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 자이로 스코프 센서의 리스너
    inner class GyroscopeSensorListener : SensorEventListener{
        // 센서에 변화가 일어날 때..
        // 이 메서드에서 측정된 값을 가져올 수 있다
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // X 축의 각속도
                val a1 = event.values[0]
                // Y 축의 각속도
                val a2 = event.values[1]
                // Z 축의 각속도
                val a3 = event.values[2]

                activityMainBinding.textView.text = "X 축의 각속도 : $a1"
                activityMainBinding.textView2.text = "Y 축의 각속도 : $a2"
                activityMainBinding.textView3.text = "Z 축의 각속도 : $a3"
            }
        }
        // 센서의 감도가 변경되었을 때...
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 가속도 센서의 리스너
    inner class AccelerometerSensorListener : SensorEventListener{
        // 센서에 변화가 일어날 때..
        // 이 메서드에서 측정된 값을 가져올 수 있다
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // X 축의 기울기
                val a1 = event.values[0]
                // Y 축의 기울기
                val a2 = event.values[1]
                // Z 축의 기울기
                val a3 = event.values[2]

                activityMainBinding.textView.text = "X 축의 기울기 : $a1"
                activityMainBinding.textView2.text = "Y 축의 기울기 : $a2"
                activityMainBinding.textView3.text = "Z 축의 기울기 : $a3"
            }
        }
        // 센서의 감도가 변경되었을 때...
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    // 마그네틱 센서의 리스너
    inner class MagneticSensorListener : SensorEventListener{
        // 센서에 변화가 일어날 때..
        // 이 메서드에서 측정된 값을 가져올 수 있다
        override fun onSensorChanged(event: SensorEvent?) {
            if(event != null) {
                // X 축 주변 자기장
                val a1 = event.values[0]
                // Y 축 주변 자기장
                val a2 = event.values[1]
                // Z 축 주변 자기장
                val a3 = event.values[2]

                activityMainBinding.textView.text = "X 축의 주변 자기장 : $a1"
                activityMainBinding.textView2.text = "Y 축의 주변 자기장 : $a2"
                activityMainBinding.textView3.text = "Z 축의 주변 자기장 : $a3"
            }
        }
        // 센서의 감도가 변경되었을 때...
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }
}