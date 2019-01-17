package com.xjm.wanandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CompoundButton
import com.xjm.wanandroid.widget.TimePickerView
import kotlinx.android.synthetic.main.activity_test.*

/**
 * Created by xjm on 2018/12/7.
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        switchTime.setOnCheckedChangeListener { _, isChecked ->
            tpView.setTimeMode(if (isChecked) 1 else 0)
        }

        tpView.setTimePickedListener(object : TimePickerView.OnTimePickedListener {
            override fun onTimePicked(position: Int) {
                if (!switchTime.isChecked) {
                    tvHour.text = if (position < 10) "0$position" else position.toString()
                } else {
                    tvMin.text = if (position < 10) "0$position" else position.toString()
                }
            }
        })

    }
}