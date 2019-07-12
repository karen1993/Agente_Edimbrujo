package Logica;

import java.util.HashMap;
import java.util.LinkedList;
import org.dyn4j.geometry.Vector2;
import org.json.simple.JSONObject;

public class Proyectil {

    private int number;
    private String idPlayer;
    private Vector2 direccion;
    private double angulo;
    private String id;
    private boolean destroy;
    private String name;
    private double x;
    private double y;
    private Vector2 velocidad;
    private double width = 64;
    private double height = 12;

    public Proyectil(String name, boolean destroy, String id, String idPlayer, double x, double y, double velocidadX, double velocidadY, double xDir, double yDir, double angulo, int number) {
        this.destroy = destroy;
        this.name = name;
        this.id = id;
        this.x = x;
        this.y = y;
        this.number = number;
        this.idPlayer = idPlayer;
        this.angulo = angulo;
        this.direccion = new Vector2(xDir, yDir);
        this.velocidad = new Vector2(velocidadX, velocidadY);
    }

    public Proyectil() {

    }

    public void fromJSON(JSONObject object) {
        JSONObject entity = (JSONObject) ((JSONObject) object.get("super")).get("Entity");
        JSONObject state = (JSONObject) ((JSONObject) entity.get("super")).get("State");

        this.idPlayer = (String) object.get("idPlayer");
        this.direccion = new Vector2((double) object.get("xDir"), (double) object.get("yDir"));
        this.angulo = (double) object.get("angulo");
        this.number = (int) (long) object.get("number");

        this.x = (double) entity.get("x");
        this.y = (double) entity.get("y");
        this.velocidad = new Vector2((double) entity.get("velocidadX"), (double) entity.get("velocidadY"));

        this.id = (String) state.get("id");
        this.name = (String) state.get("name");
        this.destroy = (boolean) state.get("destroy");

    }

}
