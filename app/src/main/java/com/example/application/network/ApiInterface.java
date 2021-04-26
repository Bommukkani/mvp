
package com.example.application.network;

import com.example.application.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("repositories")
    Call<List<User>> getRepositoryData();


}
