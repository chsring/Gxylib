package com.srwing.gxylib.coreui.mvvm;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/23
 * Email: 694177407@qq.com
 */
public class DataLoadStatus {

    // 状态
    public enum Status {
        RUNNING,
        SUCCESS,
        FAILED
    }

    //状态
    private final Status status;
    //信息
    private final String msg;
    private final int errorType;

    public int getErrorType() {
        return errorType;
    }

    //已加载数据条数、当前页码
    private int loadCount, pageIndex;

    private GolaxyPage page;

    /**
     * 标签
     * 用于多接口调用时对状态进行标记
     */
    private String tag;

    public DataLoadStatus(Status status, String msg, int errorType) {
        this.status = status;
        this.msg = msg;
        this.errorType = errorType;
    }

    /**
     * 请求成功
     * 注：此处为请求成功的状态实例化，若在状态监控中需要处理总数、加载数、页码等信息，
     * 请在调用此方法实例化之后调用 set 方法设置数据
     */
    public static DataLoadStatus createLoadedStatus(int loadCount, int pageIndex, GolaxyPage page) {
        DataLoadStatus status = new DataLoadStatus(Status.SUCCESS, "Success", 0);
        status.loadCount = loadCount;
        status.pageIndex = pageIndex;
        status.page = page;
        return status;
    }

    /**
     * 请求中
     */
    public static DataLoadStatus createLoadingStatus() {
        return new DataLoadStatus(Status.RUNNING, "Running", 0);
    }

    /**
     * 请求失败
     */
    public static DataLoadStatus createFailStatus(String msg, int errorType) {
        return new DataLoadStatus(Status.FAILED, msg, errorType);
    }

    /**
     * 设置请求标签
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 获取请求标签
     * 此处做非空处理，避免在使用switch时需要先进行判空
     */
    public @NonNull
    String getTag() {
        if (tag == null) return "";
        return tag;
    }

    /**
     * 获取请求状态
     */
    public @NonNull
    Status getStatus() {
        return status;
    }

    /**
     * 获取请求的结果信息
     * 若请求成功，返回值为字符串 Success
     * 若请求中，返回值为字符串 Running
     * 若请求失败，返回请求失败错误信息
     */
    public @NonNull
    String getMsg() {
        //为避免在请求失败时返回的错误信息为空，此处对msg进行非空处理
        if (this.msg == null) return "";
        return this.msg;
    }

    /**
     * 获取数据的总数
     */
    public int getTotalCount() {
        if (page == null) return 0;
        return page.totalCou;
    }

    /**
     * 获取数据加载数
     */
    public int getLoadCount() {
        return loadCount;
    }

    /**
     * 是否已经加载完成
     */
    public boolean isLoadFinished() {
        if (page == null) return false;
        return loadCount == page.totalCou;
    }

    /**
     * 获取每页加载数量
     */
    public int getPageSize() {
        if (page == null) return 0;
        return page.pageSize;
    }

    /**
     * 获取当前加载数据的页码
     */
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * 获取下一页页码
     */
    public int getPageNext() {
        if (page == null) return 0;
        return page.nextPage;
    }

    /**
     * 获取总页数
     */
    public int getTotalPage() {
        if (page == null) return 0;
        return page.totalPage;
    }

    public static class GolaxyPage implements Serializable {
        /**
         * 总页数
         */
        public int totalPage;
        /**
         * 每页条数
         */
        public int pageSize;
        /**
         * 下一页
         */
        public int nextPage;
        /**
         * 总条数
         */
        public int totalCou;
    }

}
