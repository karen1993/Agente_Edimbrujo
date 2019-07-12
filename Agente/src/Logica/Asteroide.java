/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import org.dyn4j.geometry.Vector2;

import org.json.simple.JSONObject;

/**
 *
 * @author emiliano
 */
public class Asteroide {

    String name;
    boolean destroy;
    String id;
    double x;
    double y;
    double width = 64;
    double height = 64;
    Vector2 velocidad;

    public Asteroide(String name, boolean destroy, String id, double x, double y, double velocidadX, double velocidadY) {
        this.name = name;
        this.destroy = destroy;
        this.id = id;
        this.x = x;
        this.y = y;
        this.velocidad = new Vector2(velocidadX, velocidadY);
    }

    public void fromJSON(JSONObject object) {
        JSONObject entity = (JSONObject) ((JSONObject) object.get("super")).get("Entity");
        JSONObject state = (JSONObject) ((JSONObject) entity.get("super")).get("State");
        
        this.x = (double) entity.get("x");
        this.y = (double) entity.get("y");
        this.velocidad = new Vector2((double) entity.get("velocidadX"), (double) entity.get("velocidadY"));
        
        
        this.id = (String) state.get("id");
        this.name = (String) state.get("name");
        this.destroy = (boolean) state.get("destroy");
    }

}
