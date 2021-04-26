package com.example.application.UserList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.application.R;
import com.example.application.adapter.UserAdapter;
import com.example.application.model.User;
import com.example.application.UserDetails.UserDetailsActivity;
import com.example.application.utils.GridSpacingItemDecoration;


import static com.example.application.utils.Constants.KEY_USER_ID;
import static com.example.application.utils.GridSpacingItemDecoration.dpToPx;

public class UserListActivity extends AppCompatActivity implements UserListContract.View, UserItemClickListener{

    private static final String TAG = "UserListActivity";
    private UserListPresenter userListPresenter;
    private RecyclerView rvUserList;
    private List<User> userList;
    private UserAdapter userAdapter;
    private ProgressBar pbLoading;

    private int pageNo = 1;

    //Constants for load more
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private GridLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        getSupportActionBar().setTitle(getString(R.string.app_name));
        initUI();

        setListeners();

        //Initializing presenter
        userListPresenter = new UserListPresenter(this);

        userListPresenter.requestDataFromServer();
    }

    /**
     * This method will initialize the UI components
     */
    private void initUI() {

        rvUserList = findViewById(R.id.rv_user_list);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(this, userList);

        mLayoutManager = new GridLayoutManager(this, 1);
        rvUserList.setLayoutManager(mLayoutManager);
        rvUserList.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(this, 10), true));
        rvUserList.setItemAnimator(new DefaultItemAnimator());
        rvUserList.setAdapter(userAdapter);

        pbLoading = findViewById(R.id.pb_loading);


    }

    /**
     * This function will contain listeners for all views.
     */
    private void setListeners() {

        rvUserList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = rvUserList.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                // Handling the infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    userListPresenter.getMoreData(pageNo);
                    loading = true;
                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void showProgress() {

        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {

        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToRecyclerView(List<User> userArrayList) {

        userList.addAll(userArrayList);
        userAdapter.notifyDataSetChanged();

        // This will help us to fetch data from next page no.
        pageNo++;
    }


    @Override
    public void onResponseFailure(Throwable throwable) {

        Log.e(TAG, throwable.getMessage());
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userListPresenter.onDestroy();
    }

    @Override
    public void onUserItemClick(int position) {

        if (position == -1) {
            return;
        }
        Intent detailIntent = new Intent(this, UserDetailsActivity.class);
        detailIntent.putExtra(KEY_USER_ID, position);
        startActivity(detailIntent);
    }


}
