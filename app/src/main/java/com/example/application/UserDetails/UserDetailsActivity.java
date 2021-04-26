

package com.example.application.UserDetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import com.example.application.R;
import com.example.application.model.User;

import static com.example.application.utils.Constants.KEY_USER_ID;


public class UserDetailsActivity extends AppCompatActivity implements UserDetailsContract.View {

    private ImageView ivBackdrop;
    private ProgressBar pbLoadBackdrop;
    private TextView tvuserTitle;
    private TextView tvuserReleaseDate;
    private TextView tvuserRatings;
    private TextView tvOverview;
    private TextView tvHomepageValue;
    private TextView tvTaglineValue;
    private TextView tvRuntimeValue, tvUserTypeValue, tvUserIdValue, tvUserNodeIdValue;

    private String userName;

    private UserDetailsPresenter userDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initCollapsingToolbar();

        initUI();

        userDetailsPresenter = new UserDetailsPresenter(this);
        userDetailsPresenter.requestUserData(getIntent().getIntExtra(KEY_USER_ID, 0));

    }

    /**
     * Initializing UI components
     */
    private void initUI() {

        ivBackdrop = findViewById(R.id.iv_backdrop);
        pbLoadBackdrop = findViewById(R.id.pb_load_backdrop);
        tvuserTitle = findViewById(R.id.tv_user_name);
        tvuserReleaseDate = findViewById(R.id.tv_user_desc);
        tvuserRatings = findViewById(R.id.tv_user_ratings);
        tvOverview = findViewById(R.id.tv_user_overview);


        tvHomepageValue = findViewById(R.id.tv_homepage_value);
        tvTaglineValue = findViewById(R.id.tv_tagline_value);
        tvRuntimeValue = findViewById(R.id.tv_runtime_value);
        tvUserTypeValue = findViewById(R.id.tv_userType_value);
        tvUserIdValue = findViewById(R.id.tv_id_value);
        tvUserNodeIdValue = findViewById(R.id.tv_nodeId_value);
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(userName);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void showProgress() {
        pbLoadBackdrop.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void setDataToViews(User user) {

        if (user != null) {

            userName = user.name;
            tvuserTitle.setText(user.name);
            tvuserReleaseDate.setText(user.full_name);
            // tvuserRatings.setText(String.valueOf(user.getRating()));
            tvOverview.setText(user.description);

            // loading album cover using Glide library
            Glide.with(this)
                    // .load(ApiClient.BACKDROP_BASE_URL + user.getBackdropPath())
                    .load(user.owner.avatar_url)

                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadBackdrop.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                    .into(ivBackdrop);


            tvTaglineValue.setText(user.full_name);
            tvHomepageValue.setText(user.name);
            tvRuntimeValue.setText(user.description);
            tvUserTypeValue.setText(user.owner.type);
            tvUserIdValue.setText(user.id);
            tvUserNodeIdValue.setText(user.node_id);
        }

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

        Snackbar.make(findViewById(R.id.main_content), getString(R.string.error_data), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDetailsPresenter.onDestroy();
    }
}
