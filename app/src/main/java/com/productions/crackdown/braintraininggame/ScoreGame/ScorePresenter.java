package com.productions.crackdown.braintraininggame.ScoreGame;

import com.productions.crackdown.braintraininggame.User;

/**
 * Created by Avinath on 3/2/2018.
 */

public class ScorePresenter implements Score.Presenter {
    private Score.View view;
    private User user;

    public ScorePresenter(Score.View view) {
        this.view = view;
    }


    @Override
    public void setUser(User user) {
        this.user = user;
        loadInformation();
    }

    @Override
    public void loadInformation() {
        view.setPoints(user.getCurrentGamePoints()+"");
        if(user.getCurrentGamePoints()>0){
            view.setMessage("Congrats!");
        }else{
            view.setMessage("Sorry :(");
        }
    }
}
