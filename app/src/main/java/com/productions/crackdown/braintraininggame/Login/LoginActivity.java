package com.productions.crackdown.braintraininggame.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.productions.crackdown.braintraininggame.Game.GameActivity;
import com.productions.crackdown.braintraininggame.R;
import com.productions.crackdown.braintraininggame.User;
import com.productions.crackdown.braintraininggame.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Login.View,View.OnClickListener {

    private Button mContinueBtn,mNewGameBtn,mExitBtn;
    private ImageView mAboutBtn;
    private Login.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContinueBtn = findViewById(R.id.login_btn_continue);
        mNewGameBtn = findViewById(R.id.login_btn_new_game);
        mAboutBtn = findViewById(R.id.login_btn_about);
        mExitBtn = findViewById(R.id.login_btn_exit);

        mContinueBtn.setOnClickListener(this);
        mNewGameBtn.setOnClickListener(this);
        mAboutBtn.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);

        presenter = new LoginPresenter(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //getting the user from shared preferences.
        String userJson =  sharedPreferences.getString("user",""); //if the user doesnt exist, a blank string will be initalized to userJson
        if(!userJson.equals("")) {
            try {
                JSONObject jsonObject = new JSONObject(userJson);
                JSONObject userJsonObj = new JSONObject(jsonObject.get("user").toString()); //retrieving the values from json and setting it to a new user.
                User user = new User();
                user.setCurrentLevel(Integer.parseInt(userJsonObj.getString("currentLevel")));
                user.setCurrentGamesLost(Integer.parseInt(userJsonObj.getString("currentGamesLost")));
                user.setCurrentGamesWon(Integer.parseInt(userJsonObj.getString("currentGamesWon")));

                user.setWonGames(Integer.parseInt(userJsonObj.getString("wonGames")));
                user.setLossGames(Integer.parseInt(userJsonObj.getString("lossGames")));
                user.setHintsOn(userJsonObj.getBoolean("hintsOn"));
                user.setCurrentGamePoints(Double.parseDouble(userJsonObj.getString("currentGamePoints")));
                user.setCurrentAttemptsWon(Integer.parseInt(userJsonObj.getString("currentAttemptsWon")));
                user.setCurrentAttemptsLost(Integer.parseInt(userJsonObj.getString("currentAttemptsLost")));
                user.setTotalPoints(Double.parseDouble(userJsonObj.getString("totalPoints")));
                user.setCurrentGameTimeRemaining(Integer.parseInt(userJsonObj.getString("currentGameTimeRemaining")));
                user.setCurrentQuestion(userJsonObj.getString("currentQuestion"));
                presenter.setCurrentUser(user);
            } catch (JSONException e) {
                Toast.makeText(this,"Could not find a saved user!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Could not find a saved user!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn_continue:
                presenter.continueGame();
                break;
            case R.id.login_btn_new_game:
                presenter.startNewGame();
                break;
            case R.id.login_btn_about:
                showHelp();
                break;
            case R.id.login_btn_exit:
                exitGame();
                break;
        }
    }

    @Override
    public void exitGame() {
        this.finishAffinity();
        System.exit(0);
    } //ending the game

    @Override
    public void showHelp() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this); //creating a dialog for about.
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_about, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public void launchNewGame() {
        Intent intent = new Intent(LoginActivity.this, GameActivity.class); //creating a new game
        startActivity(intent);
    }

    @Override
    public void continueGame(User user) {
        Intent intent = new Intent(LoginActivity.this, GameActivity.class);//creating a new game with a user
        intent.putExtra("user",user);
        startActivity(intent);
    }

    @Override
    public boolean showSaveInfoDialog() {
        return false;
    }

    @Override
    public void alertUser(String s) {
        Utilities.alertUser(this,s, Toast.LENGTH_SHORT);
    }
}
