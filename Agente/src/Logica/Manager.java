package Logica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Manager {

    private static Manager manager;
    private HashMap<String, NavePlayer> navePlayers;
    private HashMap<String, Moneda> monedas; // No estoy seguro todavia de esto
    private Match match;
    private ArrayList<NavePlayer> navePlayerList;
    private ArrayList<Proyectil> proyectilList;
    private ArrayList<Asteroide> asteroideList;
    private ArrayList<Moneda> monedaList;
    private ArrayList<NaveNeutra> neutraList;
    private HashMap<String,NaveNeutra> neutras;
    private HashMap<String,Desafio> desafios;
    private ArrayList <Entidad> entidades;
    
    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Manager() {
        // match = new Match();
        navePlayers = new HashMap<>();
        monedas = new HashMap<>();
        navePlayerList = new ArrayList<>();
        proyectilList = new ArrayList<>();
        asteroideList = new ArrayList<>();
        monedaList = new ArrayList<>();
        desafios = new HashMap<>();
        neutraList = new ArrayList<>();
        neutras = new HashMap<>();
        entidades = new ArrayList();
    }

    public NavePlayer getPlayer(String id) {
        return navePlayers.get(id);
    }

    public ArrayList<NavePlayer> getPlayers() {
        return navePlayerList;
    }
     public ArrayList<NaveNeutra> getNeutras() {
        return neutraList;
    }
    public ArrayList<Entidad> getEntidades() 
    {
        return entidades;
    }
    public ArrayList<Proyectil> getProyectiles() {
        return proyectilList;
    }

    public ArrayList<Asteroide> getAsteroides() {
        return asteroideList;
    }

    public ArrayList<Moneda> getMonedas() {
        return monedaList;
    }
    public Desafio getDesafio(String id) 
    {
        return desafios.get(id);
        
    }

    public Match getMatch() {
        return match;
    }

    /*
    // No lo necesitamos porque no tenemos estados estaticos
    public void updateStaticState(String state) {
        try {
            spawnList = new ArrayList<>();
            JSONObject json = (JSONObject) new JSONParser().parse(state);
            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonObject = (JSONObject) json.get(Integer.toString(i));
                JSONObject object;
                if ((object = (JSONObject) jsonObject.get("Map")) != null) {
                    map.fromJSON(object);
                }
                if ((object = (JSONObject) jsonObject.get("Spawn")) != null) {
                    spawnList.add(new Spawn(object));
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
    public void updateState(String state) {
        try {
            proyectilList = new ArrayList<>();
            JSONObject json = (JSONObject) new JSONParser().parse(state);
            for (int i = 0; i < json.size(); i++) {
                JSONObject jsonObject = (JSONObject) json.get(Integer.toString(i));
                JSONObject object;
                if ((object = (JSONObject) jsonObject.get("NavePlayer")) != null) {
                    JSONObject nave = (JSONObject) ((JSONObject) object.get("super")).get("Nave");
                    JSONObject entity = (JSONObject) ((JSONObject) nave.get("super")).get("Entity");
                    JSONObject naveState = (JSONObject) ((JSONObject) entity.get("super")).get("State");
                    String id = (String) naveState.get("id");
                    NavePlayer player = navePlayers.get(id);
                    if (player == null) {
                        player = new NavePlayer();
                        navePlayers.put(id, player);
                        entidades.add(player);
                        navePlayerList.add(player);
                    }
                    player.fromJSON(object);
                } else if ((object = (JSONObject) jsonObject.get("Moneda")) != null) {
                    JSONObject entity = (JSONObject) ((JSONObject) object.get("super")).get("Entity");
                    JSONObject monedaState = (JSONObject) ((JSONObject) entity.get("super")).get("State");
                    String id = (String) monedaState.get("id");
                    Moneda moneda = monedas.get(id);
                    if (moneda == null) {
                        moneda = new Moneda();
                        monedas.put(id, moneda);
                        entidades.add(moneda);
                        monedaList.add(moneda);
                    }
                    moneda.fromJSON(object);
                } else if ((object = (JSONObject) jsonObject.get("Proyectil")) != null) {
                    JSONObject entity = (JSONObject) ((JSONObject) object.get("super")).get("Entity");
                    JSONObject proyecState = (JSONObject) ((JSONObject) entity.get("super")).get("State");
                    String id = (String) proyecState.get("id");
                    Proyectil proyectil = new Proyectil();
                    proyectilList.add(proyectil);
                    proyectil.fromJSON(object);
                } else if ((object = (JSONObject) jsonObject.get("Match")) != null) { // No se usa por ahora
                    match.fromJSON(object);
                }else if ((object = (JSONObject) jsonObject.get("Desafio")) != null) 
                { 
                   // System.out.println("desafio "+jsonObject.get("Desafio"));
                   // desafio.get((String)(object.get("idNavePlayer"))).fromJSON(object);
                    
                    JSONObject desa = (JSONObject) ((JSONObject) object.get("super")).get("State");
                    String id = (String) object.get("idNavePlayer");
                    Desafio desafio  = desafios.get(id);
                    if (desafio == null) {
                        System.out.println("id desafio "+id);
                        desafio = new Desafio();
                        desafios.put(id, desafio);
                        
                    }
                    desafios.get(id).fromJSON(object);
                }else if ((object = (JSONObject) jsonObject.get("NaveNeutra")) != null)
                {
                    JSONObject nave = (JSONObject) ((JSONObject) object.get("super")).get("Nave");
                    JSONObject entity = (JSONObject) ((JSONObject) nave.get("super")).get("Entity");
                    JSONObject naveState = (JSONObject) ((JSONObject) entity.get("super")).get("State");
                    String id = (String) naveState.get("id");
                    NaveNeutra neutra = neutras.get(id);
                    if (neutra == null) 
                    {
                        neutra = new NaveNeutra();
                        neutraList.add(neutra);
                        neutras.put(id, neutra);
                        entidades.add(neutra);
                    }    
                    neutra.fromJSON(object);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
