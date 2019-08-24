package com.example.prueba2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/posts")
    Call<List<Posts>> getPostList();

    @GET("/posts/{id}")
    Call<Posts> getSinglePost(@Path("id") int postId);

    @GET("/posts/")
    Call<Posts> getSinglePostQuery(@Query("userId") int userId);

    @PUT("/posts/{id}")
    Call<Posts> putSinglePost(@Path("id") int postId);

    @DELETE("/posts/{id}")
    Call<String> deletePost(@Path("id") int postId);

    @POST("users/new")
    Call<Posts> createPost(@Body Posts post);

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com")
            .build();
}