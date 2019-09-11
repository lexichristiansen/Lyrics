package me.lexichristiansen.lyrics;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitStuff {

    @GET("lyrics/AfterRainEnglish.txt")
    Call<String> getEnglish();

    @GET("lyrics/AfterRainKanji.txt")
    Call<String> getKanji();

    @GET("lyrics/AfterRainRomaji.txt")
    Call<String> getRomaji();

    @GET("lyrics/AfterRainHeader.txt")
    Call<String> getHeader();
}
