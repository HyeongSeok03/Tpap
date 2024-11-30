package com.example.tpap.chat_gpt;

import com.example.tpap.BuildConfig;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GptService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer " + BuildConfig.GPT_API_KEY
    })
    @POST("v1/chat/completions")
    Call<GptResponse> generatePlan(@Body GptRequest request);
}
