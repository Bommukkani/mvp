
package com.example.application.UserList;

import java.util.List;

import com.example.application.model.User;

public interface UserListContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(List<User> userArrayList);

            void onFailure(Throwable t);
        }

        void getUserList(OnFinishedListener onFinishedListener, int pageNo);

    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToRecyclerView(List<User> userArrayList);

        void onResponseFailure(Throwable throwable);

    }

    interface Presenter {

        void onDestroy();

        void getMoreData(int pageNo);

        void requestDataFromServer();

    }
}
