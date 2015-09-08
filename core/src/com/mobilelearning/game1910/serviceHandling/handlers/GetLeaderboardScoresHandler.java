package com.mobilelearning.game1910.serviceHandling.handlers;

import com.mobilelearning.game1910.serviceHandling.json.LeaderboardScoresArray;

/**
 * Created by AFFonseca on 23/07/2015.
 */
public interface GetLeaderboardScoresHandler {

    void onGetLeaderboardScoresSuccess(LeaderboardScoresArray response);

    void onGetLeaderboardScoresClassError();

    void onGetLeaderboardScoresUserError();

    void onGetLeaderboardScoresError(String error);
}
