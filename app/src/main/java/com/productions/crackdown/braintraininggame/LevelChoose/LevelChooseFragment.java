package com.productions.crackdown.braintraininggame.LevelChoose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.productions.crackdown.braintraininggame.BrainGame.BrainGameFragment;
import com.productions.crackdown.braintraininggame.R;
import com.productions.crackdown.braintraininggame.User;


public class LevelChooseFragment extends Fragment implements View.OnClickListener {

    private Button mNoviceBtn,mEasyBtn,mMediumBtn,mGuruBtn;
    private User user;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            user = (User) getArguments().getSerializable("user");
        }catch (NullPointerException e){
            user = new User(); //a new user is created if there is no user.
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_level_choose_fragment,container,false);
        mNoviceBtn = view.findViewById(R.id.levels_btn_novice);
        mEasyBtn = view.findViewById(R.id.levels_btn_easy);
        mMediumBtn = view.findViewById(R.id.levels_btn_medium);
        mGuruBtn = view.findViewById(R.id.levels_btn_guru);
        mNoviceBtn.setOnClickListener(this);
        mEasyBtn.setOnClickListener(this);
        mMediumBtn.setOnClickListener(this);
        mGuruBtn.setOnClickListener(this);
        return view;
    }

    public void launchGame(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",user);
        BrainGameFragment brainGameFragment = new BrainGameFragment();
        brainGameFragment.setArguments(bundle);
        try {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.game_layout_fragment, brainGameFragment, "brain_game").commit();
        }catch (NullPointerException e ){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.levels_btn_novice:
                user.setCurrentLevel(User.LEVELS_NOVICE); //setting the users level and launnching the game
                launchGame();
                break;
            case R.id.levels_btn_easy:
                user.setCurrentLevel(User.LEVELS_EASY);
                launchGame();
                break;
            case R.id.levels_btn_medium:
                user.setCurrentLevel(User.LEVELS_MEDIUM);
                launchGame();
                break;
            case R.id.levels_btn_guru:
                user.setCurrentLevel(User.LEVELS_GURU);
                launchGame();
                break;
        }
    }
}
