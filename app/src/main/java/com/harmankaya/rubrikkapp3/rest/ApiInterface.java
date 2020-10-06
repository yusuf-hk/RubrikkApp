package com.harmankaya.rubrikkapp3.rest;

import com.harmankaya.rubrikkapp3.model.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface
{
    @FormUrlEncoded
    @POST("auth/register")
    public Call<ResponseBody> registerUser(@Field("firstName") String firstName,
                                           @Field("lastName") String lastName,
                                           @Field("email") String email,
                                           @Field("password") String password);

    @FormUrlEncoded
    @GET("auth/login")
    public Call<ResponseBody> loginUser(@Field("email") String email,
                                        @Field("password") String password);

    @GET("items")
    public Call<List<Item>> getItems();
}
