package com.srwing.gxylib.recycle_adapter.adapter.delegate

import android.content.Context
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.srwing.gxylib.recycle_adapter.adapter.delegate.RefreshLoadDelegate
import com.srwing.gxylib.recycle_adapter.adapter.delegate.listener.LoadListener
import com.srwing.gxylib.recycle_adapter.adapter.delegate.listener.RefreshListener

/**
 * Description:
 * Created by srwing
 * Date: 2022/7/7
 * Email: 694177407@qq.com
 */
class DelegateBuilder {
    private var adapterDelegate: AdapterDelegate<*>? = null
    private var smartRefreshLayout: SmartRefreshLayout? = null
    private var loadListener: LoadListener? = null
    private var refreshListener: RefreshListener? = null
    private var SET_START_INDEX = 0 //page的初始位置，有的接口从1开始
    private var SET_PAGE_SIZE = 15
    private var refreshHeader: RefreshHeader? = null
    private var refreshFooter: RefreshFooter? = null
    private var mContext: Context? = null

    constructor() {}

    //需要 添加默认footer 可以调用这个
    constructor(context: Context?) {
        mContext = context
        refreshHeader = ClassicsHeader(mContext)
        refreshFooter = ClassicsFooter(mContext)
    }

    fun setPageSize(size: Int): DelegateBuilder {
        SET_PAGE_SIZE = size
        return this
    }

    fun setStartIndex(index: Int): DelegateBuilder {
        SET_START_INDEX = index
        return this
    }

    fun addRefreshHeader(refreshHeader: RefreshHeader?): DelegateBuilder {
        adapterDelegate = adapterDelegate
        return this
    }

    fun addRefreshFooter(refreshFooter: RefreshFooter?): DelegateBuilder {
        adapterDelegate = adapterDelegate
        return this
    }

    fun observeAdapter(adapterDelegate: AdapterDelegate<*>?): DelegateBuilder {
        this.adapterDelegate = adapterDelegate
        return this
    }

    fun observeRefresh(smartRefreshLayout: SmartRefreshLayout?): DelegateBuilder {
        this.smartRefreshLayout = smartRefreshLayout
        return this
    }

    fun subscribeLoadMore(loadListener: LoadListener?): DelegateBuilder {
        this.loadListener = loadListener
        return this
    }

    fun subscribeRefresh(refreshListener: RefreshListener?): DelegateBuilder {
        this.refreshListener = refreshListener
        return this
    }

    fun build(): RefreshLoadDelegate {
        assert(adapterDelegate != null) { "you should call observeAdapter()" }
        assert(smartRefreshLayout != null) { "you should call observeRefresh()" }
        assert(loadListener != null) { "you should call subscribeLoadMore()" }
        assert(refreshListener != null) { "you should call subscribeRefresh()" }
        return RefreshLoadDelegate(
            adapterDelegate, smartRefreshLayout,
            loadListener, refreshListener,
            refreshHeader, refreshFooter,
            SET_START_INDEX, SET_PAGE_SIZE
        )
    }
}