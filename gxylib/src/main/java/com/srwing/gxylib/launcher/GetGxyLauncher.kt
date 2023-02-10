package com.srwing.b_applib.launch

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import com.srwing.gxylib.launcher.BaseResultLauncher
import java.io.Serializable

/**
 * Description: 一般我们用这一个-StartActivityForResult 的 Launcher
 * Created srwing
 * Date: 2023/2/9
 * Email: 694177407@qq.com
 */
class GetGxyLauncher(caller: ActivityResultCaller) :
    BaseResultLauncher<Intent, ActivityResult>(caller, ActivityResultContracts.StartActivityForResult()) {
    /**
     *  传 context，
     *  Array 的key-value ： arrayOf("data" to "来自于页面2 的 123 ")
     */
    inline fun <reified T> launch(
        context: Context,
        bundle: Array<out Pair<String, Any?>>? = null,
        callback: ActivityResultCallback<ActivityResult>
    ) {
        val intent = Intent(context, T::class.java).apply {
            if (bundle != null) {
                //调用自己的扩展方法-数组转Bundle
                putExtras(bundle.toBundle()!!)
            }
        }
        launch(intent, null, callback)
    }

    //java调用,第二个参数传递
    fun <T : Activity, O : Any> launch(
        context: Context, clazz: Class<T>,
        params: Map<String, O>?,
        callback: ActivityResultCallback<ActivityResult>
    ) {
        val intent = Intent(context, clazz)
        params?.apply {
            this.forEach {
                if (it.value is Int) {
                    intent.putExtra(it.key, it.value as Int)
                } else if (it.value is String) {
                    intent.putExtra(it.key, it.value as String)
                } else if (it.value is Serializable) {
                    intent.putExtra(it.key, it.value as Serializable)
                }
            }
        }
        launch(intent, null, callback)
    }

    /**
     *  传 intent
     */
    fun launch(
        intent: Intent,
        callback: ActivityResultCallback<ActivityResult>
    ) {
        launch(intent, null, callback)
    }

}