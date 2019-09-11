package me.lexichristiansen.lyrics;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class GlobalStuff {

    public static RetrofitStuff service;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lexichristiansen.me/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        service = retrofit.create(RetrofitStuff.class);
    }

}
