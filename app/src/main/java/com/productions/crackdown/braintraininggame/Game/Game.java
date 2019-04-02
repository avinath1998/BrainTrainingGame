package com.productions.crackdown.braintraininggame.Game;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 2/24/2018.
 */

public interface Game {
    interface View{
        void continueGame();
        void setUserLevel(int level);
    }

    interface Presenter{
        void setUser(User user);
        User getUser();

    }

     interface FragmentOne {
        User getUser();
    }
}
