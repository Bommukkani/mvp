

package com.example.application.UserDetails;

import com.example.application.model.User;

public class UserDetailsPresenter implements UserDetailsContract.Presenter, UserDetailsContract.Model.OnFinishedListener {

    private UserDetailsContract.View userDetailView;
    private UserDetailsContract.Model userDetailsModel;

    public UserDetailsPresenter(UserDetailsContract.View userDetailView) {
        this.userDetailView = userDetailView;
        this.userDetailsModel = new UserDetailsModel();
    }

    @Override
    public void onDestroy() {

        userDetailView = null;
    }

    @Override
    public void requestUserData(int userId) {

        if(userDetailView != null){
            userDetailView.showProgress();
        }
        userDetailsModel.getUserDetails(this, userId);
    }

    @Override
    public void onFinished(User user) {

        if(userDetailView != null){
            userDetailView.hideProgress();
        }
        userDetailView.setDataToViews(user);
    }

    @Override
    public void onFailure(Throwable t) {
        if(userDetailView != null){
            userDetailView.hideProgress();
        }
        userDetailView.onResponseFailure(t);
    }
}
