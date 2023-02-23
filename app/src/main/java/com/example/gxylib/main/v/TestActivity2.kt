//package com.example.gxylib.main.v
//
//import android.content.Intent
//import android.view.View
//import androidx.appcompat.widget.AppCompatTextView
//import com.example.gxylib.R
//import com.example.gxylib.databinding.ActivityMainBinding
//import com.example.gxylib.main.base.BindingActivity
//import com.srwing.b_applib.launch.GxyLauncher
//import com.srwing.b_applib.launch.IGxyLauncher
//import com.srwing.t_network.utils.GxyLogger
//
///**
// * Description:kotlin页面中通过委托 来实现startActivityForResult
// * Created srwing
// * Date: 2023/2/9
// * Email: 694177407@qq.com
// */
//class TestActivity2 : BindingActivity<ActivityMainBinding?>(), IGxyLauncher by GxyLauncher() {
//    override fun getContentView(): Int {
//        return R.layout.activity_main
//    }
//    override fun getTitleLayout(): Int {
//        return R.layout.b_base_title_bar
//    }
//
//    override fun setTitleContent(view: View) {
//        // 添加标题的一些事件
//        val tv = view.findViewById<AppCompatTextView>(R.id.titleText)
//        tv.text = "页面2"
//    }
//    override fun initViewData() {
//        super.initViewData()
//        initLauncher()
//        dataBinding!!.tvText.text = "这是页面2 data: " + intent.getExtras()?.get("data")
//        dataBinding!!.tvText.setOnClickListener {
//            val intent = Intent()
//            intent.putExtra("data", "这是页面2的返回值")
//            setResult(100, intent)
//            finish()
//        }
//
//        dataBinding!!.tvText2.setOnClickListener {
//            getLauncher()?.launch<TestActivity3>(this@TestActivity2,
//                arrayOf("data" to "来自于页面2 的 123 ")) {
//                    GxyLogger.i("Test-LAUNCHER", "code:" + it.getResultCode() + " ; data: " +
//                            it.getData()?.getStringExtra("data"))
//            }
//        }
//    }
//}