package com.productions.crackdown.braintraininggame.Game;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 2/24/2018.
 */

public class GamePresenter implements Game.Presenter{
    private Game.View view;
    private User user;
    public GamePresenter(Game.View view) {
        this.view = view;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }


}
