package com.srwing.b_applib.launch

import androidx.activity.result.ActivityResultCaller

/**
 * Description:
 * Created srwing
 * Date: 2023/2/9
 * Email: 694177407@qq.com
 */
interface IGxyLauncher {

    fun <T : ActivityResultCaller> T.initLauncher()

    fun getLauncher(): JLauncher?

}