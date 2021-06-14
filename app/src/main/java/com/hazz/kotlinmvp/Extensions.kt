package com.hazz.kotlinmvp

import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import kotlin.math.roundToInt

/**
 * Created by xuhao on 2017/11/14.
 */

fun Fragment.showToast(content: String): Toast {
    val toast = Toast.makeText(this.activity?.applicationContext, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

fun showToast(content: String): Toast {
    val toast = Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}


fun View.dip2px(dipValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun View.px2dip(pxValue: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}

/**
 * 数据流量格式化
 */
fun dataFormat(total: Long): String {
    val result: String
    val speedReal: Int = (total / (1024)).toInt()
    result = if (speedReal < 512) {
        "$speedReal KB"
    } else {
        val mSpeed = speedReal / 1024.0
        ((mSpeed * 100).roundToInt() / 100.0).toString() + " MB"
    }
    return result
}




