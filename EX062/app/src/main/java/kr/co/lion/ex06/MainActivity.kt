package kr.co.lion.ex06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Switch
import com.google.android.material.materialswitch.MaterialSwitch
import kr.co.lion.ex06.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.apply {

            // switch on/off에 따라서 체크박스 활성화
            val checkBoxes = arrayListOf<CheckBox>(checkBox,checkBox2,checkBox3)
            checkboxActivation(switch1,checkBoxes)
            
            button.setOnClickListener {
                val id = inputId.text
                val password = inputPassword.text
                val name = inputName.text
                var hobby = checkHobby(switch1.isChecked, checkBox, checkBox2, checkBox3)

                textView.text = "아이디 : ${id}\n" +
                        "패스워드 : ${password}\n" +
                        "이름 : ${name}\n" +
                        "취미 : ${hobby}"
            }
        }
    }

    fun checkHobby(isChecked: Boolean, box1:CheckBox, box2:CheckBox, box3:CheckBox):String{
        var hobby = ""
        if(isChecked){
            if(box1.isChecked){
                hobby += ",축구"
            }
            if(box2.isChecked){
                hobby += ",농구"
            }
            if(box3.isChecked){
                hobby += ",야구"
            }
            hobby = hobby.substring(1)
        }else{
            hobby = "취미가 없다"
        }

        return hobby
    }

    fun checkboxActivation(switch:MaterialSwitch, checkBoxes:ArrayList<CheckBox>){
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                checkBoxes.forEach {
                    it.isClickable = true
                }
            }else{
                checkBoxes.forEach {
                    it.isChecked = false
                    it.isClickable = false
                }
            }
        }
    }
}