package com.productions.crackdown.braintraininggame.Game;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.productions.crackdown.braintraininggame.BrainGame.BrainGameFragment;
import com.productions.crackdown.braintraininggame.LevelChoose.LevelChooseFragment;
import com.productions.crackdown.braintraininggame.R;
import com.productions.crackdown.braintraininggame.User;

import org.json.JSONException;
import org.json.JSONObject;

public class GameActivity extends AppCompatActivity implements Game.View {

    private FrameLayout mFragmentLayout;
    private Game.Presenter presenter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        presenter = new GamePresenter(this);
        fragmentManager = getSupportFragmentManager(); //getting the support fragment manager.
        mFragmentLayout = findViewById(R.id.game_layout_fragment);
        User user = (User) getIntent().getSerializableExtra("user");
        if(user == null){
            user = new User();
            presenter.setUser(user);
            startLevelChooser();
        }else{
            presenter.setUser(user);
            continueGame();
        }
    }


    private void startLevelChooser() { //starting the level choosing
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",presenter.getUser());
        LevelChooseFragment levelChooseFragment = new LevelChooseFragment();
        levelChooseFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.game_layout_fragment, levelChooseFragment).commit();
    }

    @Override
    public void continueGame() { //continueing the game
        Bundle bundle = new Bundle();
        bundle.putSerializable("user",presenter.getUser());
        BrainGameFragment brainGameFragment = new BrainGameFragment();
        brainGameFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.game_layout_fragment, brainGameFragment,"brain_game").commit();

    }

    @Override
    public void setUserLevel(int level) {
        presenter.getUser().setCurrentLevel(level);
    }

    @Override
    public void onBackPressed() {
        BrainGameFragment fragment = (BrainGameFragment) getSupportFragmentManager().findFragmentByTag("brain_game");
        if(fragment!=null) {// checks if the game fragment has been created.
            final User user = fragment.getUser();
            new AlertDialog.Builder(this) //creating a dialog to check if the user wants to save the current game
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.dialog_message)
                    .setMessage(R.string.dialog_message_new)
                    .setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);//data is private only to the app
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("user",user.toJSON()); //writing the user to json
                                editor.putString("user",jsonObject.toString());
                                editor.apply(); //saving the json in sharedPreferences.
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            GameActivity.super.onBackPressed();//continuing the onBackPressed()
                        }

                    })
                    .setNegativeButton(R.string.dialog_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GameActivity.super.onBackPressed();
                        }
                    })
                    .show();
        }else{
            GameActivity.super.onBackPressed();
        }
    }
}
