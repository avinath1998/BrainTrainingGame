package com.productions.crackdown.braintraininggame.ScoreGame;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.productions.crackdown.braintraininggame.LevelChoose.LevelChooseFragment;
import com.productions.crackdown.braintraininggame.Login.LoginActivity;
import com.productions.crackdown.braintraininggame.R;
import com.productions.crackdown.braintraininggame.User;
import com.productions.crackdown.braintraininggame.Utilities;

public class GameFinishFragment extends Fragment implements Score.View, View.OnClickListener {

    private TextView mScore, mMessage;
    private Button mPlayAgainBtn, mMainMenuBtn;
    private ScorePresenter presenter;
    private User user;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_game_finish_fragment,container,false);
        user = (User) getArguments().getSerializable("user");
        mScore = view.findViewById(R.id.score_points);
        mPlayAgainBtn = view.findViewById(R.id.score_btn_playagain);
        mMainMenuBtn = view.findViewById(R.id.score_btn_mainmenu);
        mMessage = view.findViewById(R.id.score_title);
        mPlayAgainBtn.setOnClickListener(this);
        mMainMenuBtn.setOnClickListener(this);
        presenter = new ScorePresenter(this);
        if(user!=null) {
            presenter.setUser(user);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.score_btn_playagain:
                playAgain();
                break;
            case R.id.score_btn_mainmenu:
                mainMenu();
                break;
        }
    }

    private void mainMenu() {
        Intent intent = new Intent(getActivity(), LoginActivity.class); //launching the main menu again
        resetUser();
        intent.putExtra("user",user);
        startActivity(intent);
    }

    private void resetUser(){ //resetting the user variables
        user.setCurrentGamesLost(0);
        user.setCurrentGamesWon(0);
        user.setCurrentAttemptsWon(0);
        user.setCurrentAttemptsLost(0);
        user.setCurrentGamePoints(0);
    }

    private void playAgain() { //launching the game again.
        resetUser();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        LevelChooseFragment levelChooseFragment = new LevelChooseFragment();
        levelChooseFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.game_layout_fragment,levelChooseFragment).commit();

    }


    @Override
    public void setPoints(String points) {
        mScore.setText(points);
    }

    @Override
    public void setMessage(String message) {
        mMessage.setText(message);
    }

    @Override
    public void alertUser(String message) {
        Utilities.alertUser(getContext(),message, Toast.LENGTH_SHORT);
    }
}
