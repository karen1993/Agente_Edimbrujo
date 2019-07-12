/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.Random;
import org.json.simple.JSONObject;

/**
 *
 * @author PC
 */
public class Desafio 
{
    public String idNaveNeutra;
    public String idNavePlayer;
    public String pregunta;
    public String[] opciones;
    public int correcta;
    public String id;
    public String name;
    public boolean destroy;

    public Desafio(String name, boolean destroy, String id, String idNeutra, String idPlayer,String pregunta, String op1,String op2,String op3) {
          
        this.id=id;
        this.destroy=destroy;
        this.name=name;
        this.idNaveNeutra = idNeutra;
        this.idNavePlayer = idPlayer;
        this.pregunta = pregunta;
        this.opciones = new String[3];
        this.opciones[0]=op1;
        this.opciones[1]=op2;
        this.opciones[2]=op3;
       
    }
    
    public Desafio()
    {
        this.opciones = new String[3];
        this.pregunta=null;
    };
    
     public void setState(Desafio desafio) {
        
        this.idNaveNeutra = ((Desafio) desafio).idNaveNeutra;
        this.idNavePlayer = ((Desafio) desafio).idNavePlayer;

    }
     public String getPregunta()
     {
         return this.pregunta;
     }
      public void fromJSON(JSONObject object) {
        
        JSONObject state = (JSONObject) ((JSONObject) object.get("super")).get("State");
        this.id = (String) state.get("id");
        this.name = (String) state.get("name");
        this.destroy = (boolean) state.get("destroy");
        this.idNaveNeutra = (String) (object.get("idNaveNeutra"));
        this.idNavePlayer = (String)(object.get("idNavePlayer"));
        this.pregunta = (String)(object.get("pregunta"));
        //falta buscar las opciones
        JSONObject opcion = (JSONObject) ((JSONObject) object.get("opciones"));
      //  System.out.println(opcion);
        this.opciones[0] = (String)(opcion.get("opcion0"));
        this.opciones[1] = (String)(opcion.get("opcion1"));
        this.opciones[2] = (String)(opcion.get("opcion2"));
         
    }

}
