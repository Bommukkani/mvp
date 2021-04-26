
package com.example.application.UserList;

import java.util.List;

import com.example.application.model.User;

public class UserListPresenter implements UserListContract.Presenter, UserListContract.Model.OnFinishedListener {

    private UserListContract.View userListView;

    private UserListContract.Model userListModel;

    public UserListPresenter(UserListContract.View userListView) {
        this.userListView = userListView;
        userListModel = new UserListModel();
    }

    @Override
    public void onDestroy() {
        this.userListView = null;
    }

    @Override
    public void getMoreData(int pageNo) {

        if (userListView != null) {
            userListView.showProgress();
        }
        userListModel.getUserList(this, pageNo);
    }

    @Override
    public void requestDataFromServer() {

        if (userListView != null) {
            userListView.showProgress();
        }
        userListModel.getUserList(this, 1);
    }

    @Override
    public void onFinished(List<User> userArrayList) {
        userListView.setDataToRecyclerView(userArrayList);
        if (userListView != null) {
            userListView.hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {

        userListView.onResponseFailure(t);
        if (userListView != null) {
            userListView.hideProgress();
        }
    }
}
