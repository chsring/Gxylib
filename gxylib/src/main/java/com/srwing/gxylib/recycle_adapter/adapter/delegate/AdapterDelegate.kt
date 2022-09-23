package com.srwing.gxylib.recycle_adapter.adapter.delegate

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/7
 * Email: 694177407@qq.com
 */
interface AdapterDelegate<T> {
    fun setList(datas: List<T>?)
    fun addList(datas: List<T>?)
}