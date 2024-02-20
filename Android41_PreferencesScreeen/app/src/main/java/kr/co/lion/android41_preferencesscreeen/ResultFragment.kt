package kr.co.lion.android41_preferencesscreeen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import kr.co.lion.android41_preferencesscreeen.databinding.FragmentResultBinding

// build.gradle 에 androidx.preference:perference 라이브러리를 추가한다.
class ResultFragment : Fragment() {

    lateinit var fragmentResultBinding: FragmentResultBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentResultBinding = FragmentResultBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentResultBinding.apply {
            // PreferenceScreen을 관리하는 Preferencec 객체를 가져온다.
            val pref = PreferenceManager.getDefaultSharedPreferences(mainActivity)
            // 저장된 데이터를 가져온다.
            // EditTextPreference
            val data1 = pref.getString("data1",null)
            // CheckBoxPreference
            val data2 = pref.getBoolean("data2", false)
            // SwitchPreference
            val data3 = pref.getBoolean("data3", false)
            // ListPreference
            val data4 = pref.getString("data4", null)
            // MultiSelectListPreference
            val data5 = pref.getStringSet("data5", null)

            textViewResult.apply {
                text = "data1 : ${data1}\n"
                append("data2 : ${data2}\n")
                append("data3 : ${data3}\n")
                append("data4 : ${data4}\n")
                data5?.forEach {
                    append("data5 : ${it}\n")
                }
            }
        }

        return fragmentResultBinding.root
    }

}