package com.example.gxylib.main.m;

import com.example.gxylib.main.m.MainEntity;

import java.util.WeakHashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MainService {

    @FormUrlEncoded
    @POST("") //TODO add url
    Observable<MainEntity> getMain(@FieldMap WeakHashMap<String, Object> params);

  }
