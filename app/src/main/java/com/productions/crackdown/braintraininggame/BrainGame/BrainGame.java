package com.productions.crackdown.braintraininggame.BrainGame;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 2/25/2018.
 */

public interface BrainGame {

    interface View{
        void setUser(User user);
        void showQuestion(String question);
        void setAnswerHint(String hint);
        void alertUser(String message);
        String getUserAnswer();
        void showUserResult(boolean val);
        void clearFields();
        void showHint(String s);
        void setTime(int time);
        void toggleHint(boolean val);
        void loadScoreFragment(User user);
        void showChance(int i);
    }

    interface Presenter{
        void addInputFromKeyBoard(String input);
        String getUserInput();
        void setUser(User user);
        void loadGame(int currentGamesWon, int currentGamesLost,String question);
        void toggleHints(boolean val);
        void stopTimer();
        User getUser();
        void resetTimer();
    }
}
