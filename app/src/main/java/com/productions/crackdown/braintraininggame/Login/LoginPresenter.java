package com.productions.crackdown.braintraininggame.Login;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 2/24/2018.
 */

public class LoginPresenter implements Login.Presenter {

    private Login.View view;
    private User user;

    public LoginPresenter(Login.View view) {
        this.view = view;
    }

    @Override
    public void continueGame() {
        if(user!=null)
         view.continueGame(user);
        else
            view.alertUser("Sorry, no saved games. ");
    }

    @Override
    public void startNewGame() {
        view.launchNewGame();
    }

    @Override
    public void startExit() {
        view.exitGame();
    }

    @Override
    public void setCurrentUser(User user) {
        this.user = user;
    }
}
