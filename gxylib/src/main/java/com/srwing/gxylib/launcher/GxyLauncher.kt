package com.srwing.b_applib.launch

import androidx.activity.result.ActivityResultCaller

/**
 * Description: kotlin页面中  以委托 的方式 使用
 * Created srwing
 * Date: 2023/2/9
 * Email: 694177407@qq.com
 *  参考： 使用方法：https://juejin.cn/post/7136359176564899877
 * class Demo16RecordActivity : BaseActivity, ISAFLauncher by SAFLauncher() { }
 * onCreate中直接初始化对象 initLauncher()
 * 使用：getLauncher()?.launch<Demo10Activity> { result -> .....}
 */
class GxyLauncher : IGxyLauncher {

    private var safLauncher: GetGxyLauncher? = null

    override fun <T : ActivityResultCaller> T.initLauncher() {
        safLauncher = GetGxyLauncher(this)
    }

    override fun getLauncher(): GetGxyLauncher? = safLauncher

}