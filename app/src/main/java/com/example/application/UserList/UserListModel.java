
package com.example.application.UserList;

import android.util.Log;

import java.util.List;

import com.example.application.model.User;
import com.example.application.network.ApiClient;
import com.example.application.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListModel implements UserListContract.Model {

    private final String TAG = "UserListModel";


    @Override
    public void getUserList(final OnFinishedListener onFinishedListener, int pageNo) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<User>> call = apiService.getRepositoryData();
        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> people = response.body();
                onFinishedListener.onFinished(people);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }


}
