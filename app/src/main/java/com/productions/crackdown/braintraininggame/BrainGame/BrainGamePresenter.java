package com.productions.crackdown.braintraininggame.BrainGame;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

import com.productions.crackdown.braintraininggame.User;

import java.util.Random;

/**
 * Created by Avinath on 2/25/2018.
 */

public class BrainGamePresenter implements BrainGame.Presenter {
    private static final int NOVICE_MAX = 2;
    private static final int NOVICE_MIN = 2;
    private static final int EASY_MAX = 3;
    private static final int EASY_MIN = 2;
    private static final int MEDIUM_MAX = 4;
    private static final int MEDIUM_MIN = 2;
    private static final int GURU_MAX = 6;
    private static final int GURU_MIN = 4;
    private static final int REDUCE_TIME = 101000;
    private static final int TIME_LIMIT = 11;
    private static final int MAX_ALLOWED_HINT_ATTEMPTS = 4;
    private static final int MAX_ALLOWED_GAMES = 10 ;


    private BrainGame.View view;
    private String input;
    private User user;
    private String[] operators;
    private Random random;
    private String question;
    private boolean continueWithCurrentGame = true;
    private CountDownTimer countDownTimer;

    public BrainGamePresenter(BrainGame.View view) {
        this.view = view;
        operators = new String[]{"/", "*", "-", "+"};
        random = new Random();
        input="";
    }

    @Override
    public void addInputFromKeyBoard(String input) { //executes whenever the user clicks a btn.
        input = input.replaceAll(" ",""); //clearing out any spaces in the text.
        switch (input){
            case "DEL":
                if(this.input.length()>0) {
                    this.input = this.input.substring(0, this.input.length() - 1);//deleting the last character
                    view.setAnswerHint(""); //removing the hint if the DEL btn is pressed.
                }
                break;
            case "#":
                if(continueWithCurrentGame) { //checks if the user is done or not with the game
                        try {
                            double answer = getQuestionAnswer(question.split(""), 1, 0, ""); //gettting the answer for the question.
                            double userAnswer = Double.parseDouble(view.getUserAnswer());//getting the suers answer
                            if (userAnswer == answer) { //answer is correct
                                view.showHint("");
                                userIsCorrect();
                                countDownTimer.cancel();
                                continueWithCurrentGame = false;
                                user.setCurrentGamesWon(user.getCurrentGamesWon()+1);
                                calculatePoints();
                            } else if(userAnswer>answer) { //users answer is greater than the actual answer
                                if(user.isHintsOn()) {
                                    view.showHint("Less!");
                                }
                                else {
                                    countDownTimer.cancel(); //stopping the timer if the user has not selected any hints
                                    continueWithCurrentGame = false; //stopping the game if the user has not selected any hints
                                    user.setCurrentGamesLost(user.getCurrentGamesLost()+1);
                                }
                                userIsWrong();

                            } else if(userAnswer<answer) { //users answer is lesser than the actial answer.
                                if(user.isHintsOn()) {
                                    view.showHint("Greater!");
                                }
                                else {
                                    countDownTimer.cancel(); //stopping the timer if the user has not selected any hints
                                    continueWithCurrentGame = false;  //stopping the game if the user has not selected any hints
                                    user.setCurrentGamesLost(user.getCurrentGamesLost()+1);
                                }
                                userIsWrong();
                            }
                            if(hasUserWon()){
                                endGame();
                                return;
                            }
                        } catch (NumberFormatException e) {
                            view.alertUser("Please enter a valid answer."); //the user has not entered a valid input.
                        }
                }else{
                    finishAndLoadNextGame();
                }
                break;
            case "-":
                if(!this.input.contains("-"))
                    this.input = "-"+this.input; //adding the negative in front of the users input
                else
                    this.input = this.input.substring(1);
                break;
            default:
                this.input+=input;
                break;
        }
    }

    private void calculatePoints() {
        double winnings = 0.0;
        if(user.getCurrentGameTimeRemaining()!=10) {
             winnings = (Math.round(100.0 / (10.0 - user.getCurrentGameTimeRemaining()))); //getting the winnings.
        }else{
            winnings = 100; //setting the winnings to 100 if the user answers the question with 10 secs remaining
        }
        user.setCurrentGamePoints(user.getCurrentGamePoints()+winnings);
        user.setTotalPoints(user.getTotalPoints()+winnings);
    }

    private void endGame() {
        view.loadScoreFragment(user); //loading the score fragment
    }

    private boolean hasUserWon() { //returns true if the user has finished 10 games
        if((user.getCurrentGamesWon()+user.getCurrentGamesLost())==MAX_ALLOWED_GAMES){
            return true;
        }else
            return false;
    }

    private void userIsCorrect() {
        view.showUserResult(true);
        user.setCurrentAttemptsWon(user.getCurrentAttemptsWon()+1);
    }

    private void userIsWrong(){
        view.showUserResult(false);
        user.setCurrentAttemptsLost(user.getCurrentAttemptsLost()+1);
        if(user.getCurrentAttemptsLost()>4){ //if the user has already had 4 attempts.
            user.setCurrentAttemptsLost(0);
        }
        if(user.isHintsOn()) //showing the chances the user has to guess if the hints are on.
            view.showChance(MAX_ALLOWED_HINT_ATTEMPTS-user.getCurrentAttemptsLost());
        if(user.getCurrentAttemptsLost()+1==MAX_ALLOWED_HINT_ATTEMPTS){ //if the user has lost four attempts, the next question will load.
            continueWithCurrentGame = false;
        }
    }

    /*
        In getQuestionAnswer, the question is taken as a string array where each of the
        characters in the question are split up into each index. This recursive method
        iterates through the entire array parsing integers, if a number format exception
        occurs, that means that an operator has been found. Each integer is concatenated into
        the string currrentNum, when an operator is found, currentNum will be the whole number to the left
        of the operator. If a previous value (previousVal) exists, then the arithmetic expression with the current number
        and current operator and the previous val will be calculated and that result will be the new previousVal.
        The previousVal and previousOperator are used as arguements so that this recursive method runs until there is no
        more operators, meaning the last number has been found.

     */
    private double getQuestionAnswer(String [] questionArray, int count, double previousVal,String previousOperator) {
        String currentNum = "";
        for(; count<questionArray.length;count++){
            try{
                currentNum+=Integer.parseInt(questionArray[count]);
            }catch (NumberFormatException e){
                if(previousVal!=0) {
                   previousVal = getArthOut(currentNum,previousVal,previousOperator);
                }else{
                    previousVal = Double.parseDouble(currentNum);
                }
                previousOperator = questionArray[count];
                return getQuestionAnswer(questionArray,++count,previousVal,previousOperator);
            }
        }
        previousVal = getArthOut(currentNum,previousVal,previousOperator);
        previousVal = Math.round(previousVal); //rounding the value to the nearest decimal
        return previousVal;
    }

    /*
    returns the result of two whole numbers and an operator
     */
    private double getArthOut(String currentNum, double previousVal, String previousOperator){
        switch (previousOperator) {
            case "+":
                previousVal = previousVal + Double.parseDouble(currentNum);
                break;
            case "-":
                previousVal = previousVal - Double.parseDouble(currentNum);
                break;
            case "*":
                previousVal = previousVal * Double.parseDouble(currentNum);
                break;
            case "/":
                previousVal = previousVal / Double.parseDouble(currentNum);
                break;
            default:
                break;
        }
        return previousVal;
    }

    //123+324/342

    @Override
    public String getUserInput() {
        return input;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
        if((user.getCurrentGamesLost()+user.getCurrentGamesWon())>=MAX_ALLOWED_GAMES){
            user.setCurrentGamesWon(0);
            user.setCurrentGamesLost(0);
        }
    }

    @Override
    public void loadGame(int currentGamesWon, int currentGamesLost,String questionBefore) {

        continueWithCurrentGame = true; //allows the game to work.
        if(user.isHintsOn())
            view.showChance(MAX_ALLOWED_HINT_ATTEMPTS-user.getCurrentAttemptsLost()); //showing the chances if the hints are on
        String question = "";
        int size;
        view.toggleHint(user.isHintsOn());
        toggleHints(user.isHintsOn());
        if(questionBefore!=null){ //if the user is continuing a game, then their previous question is loaded.
            question = user.getCurrentQuestion();
        }else {
            switch (user.getCurrentLevel()) { //switching through the levels.
                case User.LEVELS_NOVICE:
                    size = random.nextInt((NOVICE_MAX - NOVICE_MIN) + 1) + NOVICE_MIN;
                    question = loadNewQuestion(question, size);
                    break;
                case User.LEVELS_EASY:
                    size = random.nextInt((EASY_MAX - EASY_MIN) + 1) + EASY_MIN;
                    question = loadNewQuestion(question, size);
                    break;

                case User.LEVELS_MEDIUM:
                    size = random.nextInt((MEDIUM_MAX - MEDIUM_MIN) + 1) + MEDIUM_MIN;
                    question = loadNewQuestion(question, size);
                    break;
                case User.LEVELS_GURU:
                    size = random.nextInt((GURU_MAX - GURU_MIN) + 1) + GURU_MIN;
                    question = loadNewQuestion(question, size);
                    break;
            }
        }
        user.setCurrentQuestion(question);
        view.showQuestion(question);
        resetTimer(); //resetiting the timer.
        this.question = question;

    }

    public void resetTimer() {
        if(user.getCurrentGameTimeRemaining()==0){ //if the timer remaining is 0, reset it to the time limit.
            user.setCurrentGameTimeRemaining(TIME_LIMIT);
        }
          countDownTimer = new CountDownTimer(user.getCurrentGameTimeRemaining()*1000,1000) {
            @Override
            public void onTick(long l) {
                user.setCurrentGameTimeRemaining(user.getCurrentGameTimeRemaining() - 1); //reducing the users time
                Message msg = timeHandler.obtainMessage(REDUCE_TIME, user.getCurrentGameTimeRemaining()); //creating a message to be sent through the handler.
                timeHandler.sendMessage(msg);
            }

            @Override
            public void onFinish() {
                user.setCurrentGameTimeRemaining(user.getCurrentGameTimeRemaining() - 1); //reducing the users time
                view.setTime(user.getCurrentGameTimeRemaining());
                user.setCurrentGamesLost(user.getCurrentGamesLost()+1);
                finishAndLoadNextGame();//loading the next game when the timer is complete.
            }


        };
        countDownTimer.start();
    }

    private void finishAndLoadNextGame() {
        this.input = "";
        countDownTimer.cancel();
        view.clearFields();
        user.setCurrentGameTimeRemaining(TIME_LIMIT);
        user.setCurrentAttemptsLost(0);
        user.setCurrentAttemptsWon(0);
        if(user.isHintsOn())
            view.showChance(MAX_ALLOWED_HINT_ATTEMPTS);
        loadGame(0,0,null);
        view.showHint("");
    }

    private Handler timeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final int what = msg.what;
            switch (what){
                case REDUCE_TIME:
                    view.setTime(user.getCurrentGameTimeRemaining()); //showing the reduced time.
                    break;
            }
        }
    };

    @Override
    public void toggleHints(boolean val) {
        user.setHintsOn(val);
    }

    @Override
    public void stopTimer() {
        countDownTimer.cancel();
    }

    @Override
    public User getUser() {
        return user;
    }

    /*
    loadNewQuestion generates a string of integers and operators corresponding to the users level.
     */
    private String loadNewQuestion(String question, int level){
        for (int i = 0; i < level; i++) {
            int num = random.nextInt((999 - 1) + 1) + 1;
            int operator = random.nextInt(4);
            question += String.valueOf(num) + operators[operator];
        }
        return question.substring(0,question.length()-1); //removing the last operator
    }

}
