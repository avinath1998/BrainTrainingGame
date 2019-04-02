package com.productions.crackdown.braintraininggame.BrainGame;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.productions.crackdown.braintraininggame.Game.Game;
import com.productions.crackdown.braintraininggame.R;
import com.productions.crackdown.braintraininggame.ScoreGame.GameFinishFragment;
import com.productions.crackdown.braintraininggame.User;
import com.productions.crackdown.braintraininggame.Utilities;

public class BrainGameFragment extends Fragment implements BrainGame.View,Game.FragmentOne, CompoundButton.OnCheckedChangeListener {

    private BrainGame.Presenter presenter;
    private Button mKeyOne,mKeyTwo,mKeyThree,mKeyFour,mKeyFive,mKeySix,mKeySeven,mKeyEight,mKeyNine,mKeyDEL,mKeyZero,mKeyHash,mKeyLine;
    private GridLayout mKeyboard;
    private EditText mAnswer,mQuestion;
    private User user;
    private TextView mUserResult,mHint,mTimeRemaining,mChances;
    private Switch mHintSwitch;

    public BrainGameFragment(){
        presenter = new BrainGamePresenter(this); //creating a presenter
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_brain_game_fragment,container,false);
        mKeyOne = view.findViewById(R.id.brain_game_keyboard_1);
        mKeyTwo = view.findViewById(R.id.brain_game_keyboard_2);
        mKeyThree = view.findViewById(R.id.brain_game_keyboard_3);
        mKeyFour = view.findViewById(R.id.brain_game_keyboard_4);
        mKeyFive = view.findViewById(R.id.brain_game_keyboard_5);
        mKeySix = view.findViewById(R.id.brain_game_keyboard_6);
        mKeySeven = view.findViewById(R.id.brain_game_keyboard_7);
        mKeyEight = view.findViewById(R.id.brain_game_keyboard_8);
        mKeyNine = view.findViewById(R.id.brain_game_keyboard_9);
        mKeyDEL = view.findViewById(R.id.brain_game_keyboard_delete);
        mKeyZero = view.findViewById(R.id.brain_game_keyboard_0);
        mKeyHash = view.findViewById(R.id.brain_game_keyboard_hash);
        mKeyLine = view.findViewById(R.id.brain_game_keyboard_line);
        mKeyboard = view.findViewById(R.id.brain_game_keyboard);
        mAnswer = view.findViewById(R.id.brain_game_answer);
        mQuestion = view.findViewById(R.id.brain_game_question);
        mUserResult = view.findViewById(R.id.brain_game_user_correct);
        mHintSwitch = view.findViewById(R.id.brain_game_switch);
        mHint = view.findViewById(R.id.brain_game_hint);
        mChances = view.findViewById(R.id.brain_game_chances);
        mChances.setVisibility(View.GONE);
        mTimeRemaining = view.findViewById(R.id.brain_game_time);
        mHintSwitch.setOnCheckedChangeListener(this);
        mAnswer.setShowSoftInputOnFocus(false); //stops the keyboard from popping up.

        try {
            User user = (User) getArguments().getSerializable("user"); //getting the user.
            presenter.setUser(user);
            presenter.loadGame(user.getCurrentGamesWon(),user.getCurrentGamesLost(),user.getCurrentQuestion());
        }catch (NullPointerException e){ //if the user is not found, a new user is created.
            user = new User();
            presenter.setUser(user);
            presenter.loadGame(user.getCurrentGamesWon(),user.getCurrentGamesLost(),user.getCurrentQuestion());
        }

        for(int i=0; i<mKeyboard.getChildCount();i++){  //adding an onclick listener to each of the btns on the keyboard
            if(mKeyboard.getChildAt(i) instanceof Button)
                mKeyboard.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.addInputFromKeyBoard(((Button)view).getText().toString());
                        mAnswer.setText(presenter.getUserInput());
                        mAnswer.setSelection(mAnswer.getText().toString().length()); //setting the cursor to the end of the edittext
                    }
                });
        }
        return view;
    }


    @Override
    public void setUser(User user) {
        presenter.setUser(user);
        this.user = user;

    }

    @Override
    public void showQuestion(String question) {
        mQuestion.setText(question);
        mUserResult.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setAnswerHint(String hint) {
        mAnswer.setHint(hint);
    }

    @Override
    public void alertUser(String message) {
        Utilities.alertUser(getContext(),message, Toast.LENGTH_SHORT);
    }

    @Override
    public String getUserAnswer() {
        return mAnswer.getText().toString();
    }//returns the answer the user inputted.

    @Override
    public void showUserResult(boolean val) { //showing if the user is correct or incorrect.
        if(val){
            mUserResult.setVisibility(View.VISIBLE);
            mUserResult.setText(R.string.game_answer_correct);
            mUserResult.setTextColor(ContextCompat.getColor(getContext(),R.color.alertCorrect)); //changing the color to green

        }else{
            mUserResult.setVisibility(View.VISIBLE);
            mUserResult.setText(R.string.game_answer_incorrect);
            mUserResult.setTextColor(ContextCompat.getColor(getContext(),R.color.alertWrong));// changing the color to red.

        }
    }

    @Override
    public void clearFields() {
        mAnswer.setText(presenter.getUserInput());
    }

    @Override
    public void showHint(String s) {
        mHint.setText(s);
    }

    @Override
    public void setTime(int time) {
        mTimeRemaining.setText("Time Remaining:" + time+"s");
    }

    @Override
    public void toggleHint(boolean val) {
       mHintSwitch.setChecked(val);
    } //enabling the hint or not

    @Override
    public void loadScoreFragment(User user) {
        Bundle bundle = new Bundle();   //creating a bundle
        bundle.putSerializable("user",user);
        GameFinishFragment fragment = new GameFinishFragment();
        fragment.setArguments(bundle);
        try {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.game_layout_fragment, fragment).commit();
        }catch (NullPointerException e){
            alertUser("Sorry, could not load score.");
            e.printStackTrace();
        }
    }

    @Override
    public void showChance(int i) {
        mChances.setVisibility(View.VISIBLE);
        mChances.setText("Chances: "+i);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stopTimer(); //stopping the timer when
    }


    @Override
    public User getUser() {
        return presenter.getUser();
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        presenter.toggleHints(b);
        if(b) {
            alertUser("Hints on!");
            mHint.setVisibility(View.VISIBLE);
            mChances.setVisibility(View.VISIBLE);
        }else{
            alertUser("Hints off!");
            mHint.setVisibility(View.GONE);
            mChances.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.stopTimer(); //cancelling the timer
        presenter.resetTimer(); //resetting the timer when the game continues again.
    }
}
