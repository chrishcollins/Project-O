package com.chriscollins.java1_wk3;

/**
 * Created by chriscollins on 11/9/15.
 */
public class PlayerData {
    private String mPlayer;
    private String mNumber;
    private String mTeam;

    public PlayerData(String player, String number, String team) {
        mPlayer = player;
        mNumber = number;
        mTeam = team;
    }
    public String getPlayer() {
        return mPlayer;
    }

    public String getNumber() {
        return mNumber;
    }

    public String getTeam() {
        return mTeam;
    }

}
