package com.productions.crackdown.braintraininggame;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Avinath on 2/24/2018.
 */

public class User implements Serializable {
    public static final int LEVELS_NOVICE = 1;
    public static final int LEVELS_EASY = 2;
    public static final int LEVELS_MEDIUM = 3;
    public static final int LEVELS_GURU = 4;

    private int wonGames;
    private int lossGames;
    private double totalPoints;

    private int currentLevel;
    private int currentGamesWon;
    private int currentGamesLost;
    private boolean hintsOn;
    private int currentGameTimeRemaining;
    private double currentGamePoints;

    private int currentAttemptsWon;
    private int currentAttemptsLost;
    private String currentQuestion;
    private String currentAnswer;


    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getCurrentAnswer() {
        return currentAnswer;
    }

    public void setCurrentAnswer(String currentAnswer) {
        this.currentAnswer = currentAnswer;
    }

    public double getCurrentGamePoints() {
        return currentGamePoints;
    }

    public void setCurrentGamePoints(double currentGamePoints) {
        this.currentGamePoints = currentGamePoints;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCurrentGameTimeRemaining() {
        return currentGameTimeRemaining;
    }

    public void setCurrentGameTimeRemaining(int currentGameTimeRemaining) {
        this.currentGameTimeRemaining = currentGameTimeRemaining;
    }

    public boolean isHintsOn() {
        return hintsOn;
    }

    public void setHintsOn(boolean hintsOn) {
        this.hintsOn = hintsOn;
    }

    public int getWonGames() {
        return wonGames;
    }

    public void setWonGames(int wonGames) {
        this.wonGames = wonGames;
    }

    public int getLossGames() {
        return lossGames;
    }

    public void setLossGames(int lossGames) {
        this.lossGames = lossGames;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }


    public int getCurrentGamesWon() {
        return currentGamesWon;
    }

    public void setCurrentGamesWon(int currentGamesWon) {
        this.currentGamesWon = currentGamesWon;
    }

    public void setCurrentGamesLost(int currentGamesLost) {
        this.currentGamesLost = currentGamesLost;
    }


    public int getCurrentGamesLost() {
        return currentGamesLost;
    }


    public int getCurrentAttemptsLost() {
        return currentAttemptsLost;
    }

    public int getCurrentAttemptsWon() {
        return currentAttemptsWon;
    }

    public void setCurrentAttemptsLost(int currentGamesWon) {
        this.currentAttemptsLost = currentGamesWon;
    }

    public void setCurrentAttemptsWon(int currentGamesLost) {
        this.currentAttemptsLost = currentGamesLost;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("wonGames", wonGames);
            jsonObject.put("lossGames",lossGames);
            jsonObject.put("totalPoints", totalPoints);
            jsonObject.put("currentLevel", currentLevel);
            jsonObject.put("currentGamesWon",currentGamesWon);
            jsonObject.put("currentGamesLost", currentGamesLost);
            jsonObject.put("currentLevel", currentLevel);
            jsonObject.put("currentGamesWon",currentGamesWon);
            jsonObject.put("currentGamesLost", currentGamesLost);
            jsonObject.put("hintsOn", hintsOn);
            jsonObject.put("currentGameTimeRemaining",currentGameTimeRemaining);
            jsonObject.put("currentGamePoints", currentGamePoints);
            jsonObject.put("currentAttemptsWon", currentAttemptsWon);
            jsonObject.put("currentGamesWon",currentGamesWon);
            jsonObject.put("currentAttemptsLost", currentAttemptsLost);
            jsonObject.put("currentQuestion", currentQuestion);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
