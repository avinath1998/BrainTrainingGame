package com.productions.crackdown.braintraininggame.ScoreGame;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 3/2/2018.
 */

public interface Score {
    interface View{
        void setPoints(String points);
        void setMessage(String message);
        void alertUser(String message);
    }

    interface Presenter{
        void setUser(User user);
        void loadInformation();
    }
}
