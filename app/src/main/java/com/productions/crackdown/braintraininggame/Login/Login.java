package com.productions.crackdown.braintraininggame.Login;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 2/24/2018.
 */

public interface Login {
    interface View{
        void exitGame();
        void showHelp();
        void launchNewGame();
        void continueGame(User user);
        boolean showSaveInfoDialog();
        void alertUser(String s);
    }

    interface Presenter{
        void continueGame();
        void startNewGame();
        void startExit();
        void setCurrentUser(User user);
    }
}
