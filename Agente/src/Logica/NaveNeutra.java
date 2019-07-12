/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;

import java.util.LinkedList;
import org.json.simple.JSONObject;
import org.dyn4j.geometry.Vector2;

public class NaveNeutra extends Entidad
{
    
    protected Vector2 velocidad; // Velocidad de la nave
    protected int countProyectil;
    protected Vector2 direccion;
    protected double angulo;
   //protected int puntaje;
    //protected LinkedList<NaveNeutra> navesAliadas;
    protected int idBullets;
   
    private String idProp;
    private boolean disponible;
  
    //private String idPosP;

   
    public NaveNeutra()
    {
        super();
    }
    @Override
    public boolean puedeSeguir()
    {
        boolean dis = false;
        if(this.disponible && this.idProp.equalsIgnoreCase(""))
            dis = true;
        return dis;
    }
  
    public boolean getDisponible() 
    {
        return disponible;
    }
     public String getPropietario() {
        return this.idProp;
    }

    public void fromJSON(JSONObject object) { // Hay que terminar de definir el toJSON para pasar a esta parte
        JSONObject nave = (JSONObject) ((JSONObject) object.get("super")).get("Nave");
        JSONObject entity = (JSONObject) ((JSONObject) nave.get("super")).get("Entity");
        JSONObject state = (JSONObject) ((JSONObject) entity.get("super")).get("State");

       
        
        // State
        this.id = (String) state.get("id");
        this.name = (String) state.get("name");
        this.destroy = (Boolean) state.get("destroy");

        // Entity
        this.x = (double) entity.get("x");
        this.y = (double) entity.get("y");
        this.velocidad = new Vector2((double) entity.get("velocidadX"), (double) entity.get("velocidadY"));

        // Nave
        this.countProyectil = (int) (long) nave.get("countProyectil");
        this.direccion = new Vector2((double) nave.get("xDir"), (double) nave.get("yDir"));
        this.angulo = (double) nave.get("angulo");

        // NaveNeutra
       
        this.idBullets = (int) (long) object.get("idBullets");
        this.idProp = (String) object.get("idPropietario");
        this.disponible = (boolean) object.get("disponible");
              

    }
}
