package Logica;

import java.util.HashMap;
import java.util.LinkedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Match {

    protected LinkedList<String> players;
    protected LinkedList<String> playingPlayers;

    public Match(String id, LinkedList<String> players, LinkedList<String> playingPlayers, boolean destroy) {
        //super("Match", destroy, id);
        this.players = players;
        this.playingPlayers = playingPlayers;
    }

    public void fromJSON(JSONObject object) {

    }

}
