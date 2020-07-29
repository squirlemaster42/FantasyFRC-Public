package com.fantasyfrc.utils;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class TBAReqGenerator {

    //Makes HTTP request to TBA
    public static String makeRequest(final String reqURL) throws IOException {
        //System.out.println(reqURL);

        //Creates request with given URL and adds header with TBAAuthKey
        Request request = new Request.Builder()
                .url(reqURL)
                .addHeader("X-TBA-Auth-Key", Constants.getInstance().getTBAAuthKey())
                .build();

        Response response = Constants.getInstance().getClient().newCall(request).execute();
        return response.body().string();
    }

    //Used to make the request URL
    //Gets match list for team at given event
    public static String makeEventMatchRequest(final String eventID){
        return "https://www.thebluealliance.com/api/v3/event/" + eventID + "/matches";
    }

    //Used to get a list of matches for the event a team is attending
    public static String makeMatchListRequest(final String eventID, final String teamID){
        return "https://www.thebluealliance.com/api/v3/team/" + teamID + "/event/" + eventID + "/matches/keys";
    }

    public static String makeEventAllianceRequest(final String eventID){
        return String.format("https://www.thebluealliance.com/api/v3/event/%s/alliances", eventID);
    }
}
