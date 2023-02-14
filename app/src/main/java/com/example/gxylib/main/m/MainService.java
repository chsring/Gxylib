package com.example.gxylib.main.m;

import com.example.gxylib.main.m.MainEntity;

import java.util.WeakHashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MainService {

    @GET("api/engine/mall/goods/list")
    Observable<MainEntity> getMain(@QueryMap WeakHashMap<String, Object> params);

  }
