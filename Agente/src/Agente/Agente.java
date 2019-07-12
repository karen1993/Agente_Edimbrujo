/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agente;

import Conexion.Conexion;
import Logica.Desafio;
import Logica.Entidad;
import Logica.Manager;
import Logica.Moneda;
import Logica.NaveNeutra;
import Logica.NaveNeutra;
import Logica.NavePlayer;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import org.dyn4j.geometry.Vector2;

/**
 *
 * @author PC
 */
public class Agente
{

    public static final double MAX_VELOCITY = 50;

    private Manager manager;

    private NavePlayer myAgent;

    private ArrayList <Entidad> entidades;
    private Entidad objetivo;

    private String myID;
    private Conexion con;
    private ArrayList camino;
    

    public Agente(String nombre) throws IOException 
    {

        //con = new Conexion("http://10.0.20.157:8080/Edimbrujo/webservice/server");
        //con = new Conexion("http://edimbrujo.fi.uncoma.edu.ar/Edimbrujo/webservice/server");
        con = new Conexion("http://localhost:8080/Edimbrujo/webservice/server");

        this.myID = con.iniciar(nombre);
        this.manager = Manager.getManager();
        //listaMov = new LinkedList<>();
        manager.updateState(con.getFullState());
        camino = new ArrayList();
    }

    public void cargarJugador() 
    {
        myAgent = manager.getPlayer(myID);
        
        
    }
    public void jugar() throws IOException
    {
        manager.updateState(con.getFullState());
        cargarJugador();
        entidades = manager.getEntidades();
        if(!myAgent.getBloqueado() )
        {
            if(objetivo != null && objetivo.puedeSeguir() && !myAgent.getDead())
            {
               // System.out.println("seguir al anterior elegido de tipo "+objetivo.getName() +" "+objetivo.getId());
                double dist = distancia(objetivo.getPosition(),myAgent.getPosition());
                if(objetivo.getName().equalsIgnoreCase("NavePlayer") && dist < 50)
                    con.makeAction("fire");
                else
                {
                    Vector2 nuevaVelocidad = steer(objetivo);
                    con.makeMove("" + nuevaVelocidad.x, "" + nuevaVelocidad.y);
                }
            }
            else
            {
                objetivo = buscarObjetivo();
                if(objetivo != null && objetivo.puedeSeguir())
                {
                    double dist = distancia(objetivo.getPosition(),myAgent.getPosition());
                    if(objetivo.getName().equalsIgnoreCase("NavePlayer") && dist < 200)
                        con.makeAction("fire");
                    else
                    {
                        Vector2 nuevaVelocidad = steer(objetivo);
                        con.makeMove("" + nuevaVelocidad.x, "" + nuevaVelocidad.y);
                    }
                  //  System.out.println("seguir un nuevo objetivo  de tipo "+objetivo.getName());
                }
            }
        }  
        else
        {
            Desafio des = manager.getDesafio(myID);
            if(des!=null)
            {
                String preg = des.pregunta;
                String cad = "";
                int aux1=0,aux2;
                for (int i = 0; i < preg.length(); i++) 
                {
                    if(preg.charAt(i)=='+')
                    {
                        aux1 = Integer.parseInt(cad);
                        cad="";
                    }
                    else
                    {
                        cad+=preg.charAt(i);
                    }
                           
                }
                aux2 = Integer.parseInt(cad);
                int res = aux1 + aux2;
                if(res == Integer.parseInt(des.opciones[0]) )
                {
                    con.makeResponder(""+0);
                   
                }   
                else
                {
                    if(res == Integer.parseInt(des.opciones[1]))
                    {
                        con.makeResponder(""+1);
                        
                    }   
                    else
                    {
                        con.makeResponder(""+2);
                       
                    }
                }
            }      
        }
    } 
    
    private Entidad buscarObjetivo()
    {
        
        int random = -1 ;
        Entidad nuevoObj = null;
        boolean seguir = true;
                /*
        menor sera cero si a la izq hay una distancia menor
              sera 1 si la distancia es igual para las 3 opciones
              sera 2 si la distancia menor es hacia la derecha
        */
        int menor = 1;
        
        //se puede colgar???????????????????
        while(seguir)
        {
           
            random = (int)(Math.random()*entidades.size());
           System.out.println("********buscando********  "+random);
            if(entidades.get(random)!= myAgent && entidades.get(random).puedeSeguir())
            {
                seguir = false;
                nuevoObj = entidades.get(random);
            }
        }
        if(nuevoObj != null)
        {
            double dist0 = Integer.MAX_VALUE;
            double dist1 = distancia(nuevoObj.getPosition(),myAgent.getPosition());
            double dist2 = Integer.MAX_VALUE;
            int izq = buscarProximoDisp(random,-1);
            int der = buscarProximoDisp(random,1);
            if(izq != -1 )
            {
                dist0 = distancia(entidades.get(izq).getPosition(),myAgent.getPosition());
            }
            if(der != -1 )
            {
                dist2 = distancia(entidades.get(der).getPosition(),myAgent.getPosition());
            }
            if(dist0 < dist1 && dist0 < dist2 )
            {
                nuevoObj = entidadMenor(nuevoObj,izq,dist0,-1);
                        //entidades.get(izq);
                //
            }
            else
            {
                if(dist2 < dist1 && dist2 < dist1 )
                {
                    nuevoObj = entidadMenor(nuevoObj,der,dist2,1);
                            //entidades.get(der);
                }    
            }
            
        }
        return nuevoObj;
    }
    public Entidad entidadMenor(Entidad menor, int pos,double dist,int incrementa)
    {
        boolean seguir = true;
        int i = pos + incrementa;
        double distAux = Integer.MAX_VALUE;
        while(seguir && i > 0 && i < entidades.size())
        {
            System.out.println("entidadMenor");
            Entidad aux = entidades.get(i);
            if(aux.puedeSeguir() && aux != myAgent)
            {
                distAux = distancia(aux.getPosition(),myAgent.getPosition());
                if(distAux < dist )
                {
                    dist = distAux;
                    menor = aux;
                  
               
                }
                else
                {
                    seguir = false;
                }
            }
              i += incrementa;
        }
        return menor;
    }
    public int buscarProximoDisp(int posActual, int incrementa)
    {
        int posDis = -1;
        int i = posActual + incrementa;
        boolean seguir = true;
        while(i >= 0 && i < entidades.size() && seguir)
        {
            if(entidades.get(i).puedeSeguir() && entidades.get(i) != myAgent)
            {
                seguir = false;
                posDis = i;
            }
            i += incrementa;
        }
        return posDis;
    }
    
    // Este metodo basicamente es el seek
    private Vector2 steer(Object obj) 
    {
        int i = -1;
        switch(obj.getClass().getName())
        {
            case "Logica.NavePlayer":
                i = 0;
                break;
            case "Logica.Moneda":
                i = 1;
                break;
            case "Logica.NaveNeutra":
                i = 2;
                break;
        }
        Vector2 vectorDesired=null, vectorSteering;
        // 1. vector(desired velocity) = (target position) - (vehicle position)
        if(i == 0)
            vectorDesired = new Vector2(((NavePlayer)obj).getX(), ((NavePlayer)obj).getY()).subtract(myAgent.getX(), myAgent.getY());
        // 2. normalize vector(desired velocity)
        if(i == 1)
            vectorDesired = new Vector2(((Moneda)obj).getX(), ((Moneda)obj).getY()).subtract(myAgent.getX(), myAgent.getY());
        if( i == 2)
             vectorDesired = new Vector2(((NaveNeutra)obj).getX(), ((NaveNeutra)obj).getY()).subtract(myAgent.getX(), myAgent.getY());
        vectorDesired.normalize();
        // 3. scale vector(desired velocity) to maximum speed
        vectorDesired.setMagnitude(MAX_VELOCITY);
        // 4. vector(steering force) = vector(desired velocity) - vector(current velocity)
        vectorSteering = vectorDesired.subtract(myAgent.getVelocidad());

        // 5. limit the magnitude of vector(steering force) to maximum force
        //vectorSteering.scale(200);
        // 6. vector(new velocity) = vector(current velocity) + vector(steering force)
        // 7. limit the magnitude of vector(new velocity) to maximum speed
        vectorSteering.x = vectorSteering.x / 10;
        vectorSteering.y = vectorSteering.y / 10;
        //truncate(vectorSteering.add(myAgent.getVelocidad()), MAX_VELOCITY);
        vectorSteering.add(myAgent.getVelocidad());
        return vectorSteering;

    }

   

    private void truncate(Vector2 vector, double max) {
        double i;
        i = max / vector.getMagnitude();
        if (i > 1.0) {
            i = 1.0;
        }
        vector.setMagnitude(i);
    }

    public double distancia(Point a, Point b) {
        // Tambien se puede medir la distancia entre dos vectores (Vector2) utilizados como puntos
        int xA = a.x, yA = a.y, xB = b.x, yB = b.y;
        return Math.sqrt((xB - xA) * (xB - xA) + (yB - yA) * (yB - yA));
    }
  

}
