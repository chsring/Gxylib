package com.srwing.gxylib.recycle_adapter;

import java.util.ArrayList;

/**
 * Description:
 * Created by srwing
 * Date: 2022/6/27
 * Email: 694177407@qq.com
 */
public abstract class MutipleDataConverter <T>  {

    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private T mEntity = null ;

    public abstract ArrayList<MultipleItemEntity> convert();

    public void clearHistoryData(){
        ENTITIES.clear();
    }
    public MutipleDataConverter setEntityData(T entity){
        this.mEntity = entity ;
        return this ;
    }
    public T getEntityData(){
        if(mEntity == null){
            throw new NullPointerException("DATA IS NULL");
        }
        return mEntity;
    }

}