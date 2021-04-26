
package com.example.application.UserDetails;

import com.example.application.model.User;

public interface UserDetailsContract {

    interface Model {

        interface OnFinishedListener {
            void onFinished(User user);

            void onFailure(Throwable t);
        }

        void getUserDetails(OnFinishedListener onFinishedListener, int userId);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews(User user);

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestUserData(int userId);
    }
}
