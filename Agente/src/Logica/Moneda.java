/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;

import org.json.simple.JSONObject;

/**
 *
 * @author emiliano
 */
public class Moneda extends Entidad
{
   private boolean dis;
    
    public Moneda() 
    {
        super();
    }
    @Override
    public boolean puedeSeguir()
    {
        return dis;
    }  

    public void fromJSON(JSONObject object) {
        JSONObject entity = (JSONObject) ((JSONObject) object.get("super")).get("Entity");
        JSONObject state = (JSONObject) ((JSONObject) entity.get("super")).get("State");
        double nuevoX = (double) entity.get("x");
        double nuevoY = (double) entity.get("y");
        if(x != nuevoX || y != nuevoY)
        {
            dis = false;
        }
        else
        {
            dis = true;
        }
        this.x = nuevoX;
        this.y = nuevoY;

        this.id = (String) state.get("id");
        this.name = (String) state.get("name");
        this.destroy = (boolean) state.get("destroy");

    }

}
