package com.srwing.gxylib.recycle_adapter.adapter.delegate;

import android.content.Context;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.listener.LoadListener;
import com.srwing.gxylib.recycle_adapter.adapter.delegate.listener.RefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:加载更多的代理接口
 * Created by srwing
 * Date: 2022/7/7
 * Email: 694177407@qq.com
 */
/*  step 1:
        RecycleView 的adapter需要实现 AdapterDelegate 接口
    step 2:
        refreshLoadDelegate = RefreshLoadDelegate.builder()
                .observeRefresh(dataBinding.refresh)
                .observeAdapter(vHomeArticleReportAdapter)
                .subscribeRefresh((pageIndex, pageSize) -> {
                    //请求banner
                    viewModel.getBannerList();
                    //请求live
                    viewModel.getHomeLiveList();
                    //请求文章 报告
                    viewModel.getArticleAndReportList(pageIndex, pageSize);
                }).subscribeLoadMore(viewModel::getArticleAndReportList)
                .build();
      step 2:
            请求成功的时候:
                refreshLoadDelegate.addRLData();
            请求失败的时候:
                refreshLoadDelegate.onRequestFail();

 */
public class RefreshLoadDelegate {

    private final AdapterDelegate adapterDelegate;
    private final SmartRefreshLayout smartRefreshLayout;
    private final LoadListener loadListener;
    private final RefreshListener refreshListener;

    private LOADMODE loadMode = LOADMODE.MODE_REFRESH;//刷新 或者 加载的类型

    private int SET_START_INDEX;
    private int SET_PAGE_SIZE;
    private int pageSize;
    private int pageIndex;
    private RefreshHeader refreshHeader;
    private RefreshFooter refreshFooter;

    public RefreshLoadDelegate(AdapterDelegate delegate, SmartRefreshLayout refreshLayout,
                               LoadListener load, RefreshListener refresh,
                               RefreshHeader header, RefreshFooter footer,
                               int START_INDEX, int PAGE_SIZE) {
        this.adapterDelegate = delegate;
        this.smartRefreshLayout = refreshLayout;
        this.loadListener = load;
        this.refreshListener = refresh;
        this.SET_START_INDEX = START_INDEX;
        this.SET_PAGE_SIZE = PAGE_SIZE;
        this.refreshHeader = header;
        this.refreshFooter = footer;
        this.pageIndex = SET_START_INDEX;
        this.pageSize = SET_PAGE_SIZE;
        if (refreshFooter != null) {
            this.smartRefreshLayout.setRefreshFooter(refreshFooter);
        }
        if (refreshHeader != null) {
            this.smartRefreshLayout.setRefreshHeader(refreshHeader);
        }
        this.smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMode = LOADMODE.MODE_LOAD;
                loadListener.loadmore(++pageIndex, pageSize);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = SET_START_INDEX;
                loadMode = LOADMODE.MODE_REFRESH;
                refreshListener.refresh(pageIndex, pageSize);
            }
        });
    }

    public void requestRefresh() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.autoRefresh();
        }
    }

    public void requestLoadMore() {
        if (smartRefreshLayout != null) {
            smartRefreshLayout.autoLoadMore();
        }
    }

    public static DelegateBuilder builder() {
        return new DelegateBuilder();
    }

    //添加了默认的header和footer
    public static DelegateBuilder builder(Context context) {
        return new DelegateBuilder(context);
    }

    public LOADMODE getLoadMode() {
        return loadMode;
    }

    //加载数据
    public <T> void addRLData(List<T> mData) {
        if (adapterDelegate == null) {
            return;
        }
        if (loadMode == LOADMODE.MODE_REFRESH) {
            if (mData == null || mData.isEmpty())
                //数据为空时 并且刷新时 清空数据
                adapterDelegate.setList(new ArrayList<>());
            else
                //数据不为空时 并且刷新时 加载数据
                adapterDelegate.setList(mData);

        } else if (loadMode == LOADMODE.MODE_LOAD) {
            if (mData != null && !mData.isEmpty())
                //加载更多时 数据不为空时 加载数据
                adapterDelegate.addList(mData);
            else
                //加载更多时 数据为空时 重置pageIndex
                pageIndex--;

        }
        finishLoadRefresh(loadMode);
    }

    //请求失败后 还原pageIndex
    public void onRequestFail() {
        if (loadMode == LOADMODE.MODE_LOAD) {
            pageIndex--;
        }
        if (pageIndex < SET_START_INDEX)
            pageIndex = SET_START_INDEX;
        finishLoadRefresh(loadMode);
    }

    private void finishLoadRefresh(LOADMODE mode) {
        if (smartRefreshLayout == null) {
            return;
        }
        if (mode == LOADMODE.MODE_LOAD) {
            smartRefreshLayout.finishLoadMore();
        } else {
            smartRefreshLayout.finishRefresh();
        }
    }
}
