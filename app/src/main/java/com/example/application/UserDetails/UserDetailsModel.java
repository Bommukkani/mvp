
package com.example.application.UserDetails;

import android.util.Log;


import com.example.application.model.User;
import com.example.application.network.ApiClient;
import com.example.application.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsModel implements UserDetailsContract.Model {

    private final String TAG = "UserDetailsModel";

    @Override
    public void getUserDetails(final OnFinishedListener onFinishedListener, final int userId) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<User>> call = apiService.getRepositoryData();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                onFinishedListener.onFinished(users.get(userId));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });

    }
}
