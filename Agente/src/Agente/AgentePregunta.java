/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agente;


import Conexion.Conexion;
import Logica.Desafio;
import Logica.Manager;
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
public class AgentePregunta
{

    public static final double MAX_VELOCITY = 50;

    private Manager manager;

    private NavePlayer myAgent;

    private ArrayList<NavePlayer> navePlayers;
    private ArrayList<NaveNeutra> neutras;

    private String myID;
    private Conexion con;
    private ArrayList<NaveNeutra> caminoNeutras;
    

    public AgentePregunta() throws IOException {

        //con = new Conexion("http://10.0.20.157:8080/Edimbrujo/webservice/server");
        //con = new Conexion("http://edimbrujo.fi.uncoma.edu.ar/Edimbrujo/webservice/server");
        con = new Conexion("http://localhost:8080/Edimbrujo/webservice/server");

        this.myID = con.iniciar("agente007");
        this.manager = Manager.getManager();
        //listaMov = new LinkedList<>();
        manager.updateState(con.getFullState());
        caminoNeutras = new ArrayList();
    }

    public void cargarJugador() 
    {
        myAgent = manager.getPlayer(myID);
        
        
    }
    public void jugar() throws IOException
    {
        manager.updateState(con.getFullState());
        cargarJugador();
        navePlayers = manager.getPlayers();
        neutras = manager.getNeutras();
        if(!myAgent.getBloqueado())
        {
            
            if(!caminoNeutras.isEmpty()&& caminoNeutras.get(0).getDisponible() && 
                    caminoNeutras.get(0).getPropietario().equalsIgnoreCase(""))
            {
                Vector2 nuevaVelocidad = steer(caminoNeutras.get(0));
                con.makeMove("" + nuevaVelocidad.x, "" + nuevaVelocidad.y);
            }
            else
            {
               ArrayList<NaveNeutra> listAux = new ArrayList();
               caminoNeutras.clear();
               for (NaveNeutra neutra : neutras)
               {
                   if(neutra.getDisponible() && neutra.getPropietario().equalsIgnoreCase(""))
                   {
                       listAux.add(neutra);
                       System.out.println(listAux.toString());
                   }
               }
               posibleCamino(listAux);
            }
        }
        else
        {
            if(myAgent.getBloqueado())
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
                    System.out.println("pregunta "+des.getPregunta());
                   int res = aux1 + aux2;
                    System.out.println(aux1+" + "+aux2+" respuesta "+res);
                  if(res == Integer.parseInt(des.opciones[0]) )
                   {
                        con.makeResponder(""+0);
                        System.out.println("elegida "+0);
                   }   
                   else
                   {
                       if(res == Integer.parseInt(des.opciones[1]))
                       {
                           con.makeResponder(""+1);
                           System.out.println("elegida "+1);
                       }   
                       else
                       {
                           con.makeResponder(""+2);
                           System.out.println("elegida "+2);
                       }
                   }
                 
                   // System.out.println("corecta "+des.correcta);
                }
                caminoNeutras.remove(0);
            }
            

        }
    } 
    private void posibleCamino(ArrayList<NaveNeutra> listAux)
    {
        NaveNeutra neutra = null;
        if(!listAux.isEmpty())
        {
            neutra = listAux.get(0);
            double distanciaActual = distancia(neutra.getPosition(), myAgent.getPosition());
            double distanciaAux;
            for (int j = 1; j < listAux.size(); j++) 
            {
                distanciaAux = distancia(listAux.get(j).getPosition(), myAgent.getPosition());
                if(distanciaAux < distanciaActual)
                {
                    distanciaActual = distanciaAux;
                    neutra = listAux.get(j);
                }
            }
            caminoNeutras.add(neutra);
            listAux.remove(neutra);
          //  System.out.println("posible camino ");
            posibleCaminoAux(listAux,neutra);
        }
    }
    private void posibleCaminoAux(ArrayList<NaveNeutra> listAux,NaveNeutra nave)
    {
        NaveNeutra neutra = null;
        if(!listAux.isEmpty())
        {
            neutra = listAux.get(0);
            double distanciaActual = distancia(neutra.getPosition(), nave.getPosition());
            double distanciaAux;
            for (int j = 1; j < listAux.size(); j++) 
            {
                distanciaAux = distancia(listAux.get(j).getPosition(), nave.getPosition());
                if(distanciaAux < distanciaActual)
                {
                    distanciaActual = distanciaAux;
                    neutra = listAux.get(j);
                }
            }
            caminoNeutras.add(neutra);
            listAux.remove(neutra);
            System.out.println("posible caminoAux ");
            posibleCaminoAux(listAux,neutra);
        }
    }
   

    private NaveNeutra neutraMasCercana() {
        NaveNeutra masCercana = null;
        double distanciaMasCercana = Integer.MAX_VALUE;
        for (NaveNeutra neutra : neutras) {
            if (neutra != null && myAgent != null && neutra.getDisponible() && neutra.getPropietario().equalsIgnoreCase("")) 
            {
                double distanciaActual = distancia(neutra.getPosition(), myAgent.getPosition());
                if (masCercana == null) {
                    masCercana = neutra;
                    distanciaMasCercana = distanciaActual;
                } else {
                    if (distanciaActual < distanciaMasCercana) {
                        masCercana = neutra;
                        distanciaMasCercana = distanciaActual;
                    }
                }
            }
        }

        return masCercana;
    }

    private NavePlayer enemigoMasCercano() {
        NavePlayer masCercano = null;
        double distanciaMasCercana = Integer.MAX_VALUE;
        for (NavePlayer player : navePlayers) {
            if (player != null && myAgent != null) {
                if (player != myAgent) {
                    double distanciaActual = distancia(player.getPosition(), myAgent.getPosition());
                    if (masCercano == null) {
                        masCercano = player;
                        distanciaMasCercana = distanciaActual;
                    } else {
                        if (distanciaActual < distanciaMasCercana) {
                            masCercano = player;
                            distanciaMasCercana = distanciaActual;
                        }
                    }
                }
            }
        }

        return masCercano;
    }

    // Este metodo basicamente es el seek
    private Vector2 steer(NaveNeutra entidad) {
        Vector2 vectorDesired, vectorSteering;
        // 1. vector(desired velocity) = (target position) - (vehicle position)
        vectorDesired = new Vector2(entidad.getX(), entidad.getY()).subtract(myAgent.getX(), myAgent.getY());
        // 2. normalize vector(desired velocity)
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

    private Vector2 steer(NavePlayer entidad) {
        Vector2 vectorDesired, vectorSteering;
        // 1. vector(desired velocity) = (target position) - (vehicle position)
        vectorDesired = new Vector2(entidad.getX(), entidad.getY()).subtract(myAgent.getX(), myAgent.getY());
        // 2. normalize vector(desired velocity)
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
    /* public void jugar() throws IOException {
        manager.updateState(con.getFullState());
        cargarJugador();
        navePlayers = manager.getPlayers();
        neutras = manager.getNeutras();

        double distanciaEnemigo = Integer.MAX_VALUE;
        double distanciaNaveNeutra = Integer.MAX_VALUE;

        NaveNeutra neutraObjetivo = neutraMasCercana();
        NavePlayer oponente = enemigoMasCercano();
        
        
        if (neutraObjetivo != null && !myAgent.getBloqueado()) 
        {
            Vector2 nuevaVelocidad = steer(neutraObjetivo);
            con.makeMove("" + nuevaVelocidad.x, "" + nuevaVelocidad.y);
        }
        else
        {
            if(myAgent.getBloqueado())
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
                    System.out.println("pregunta "+des.getPregunta());
                   int res = aux1 + aux2;
                    System.out.println(aux1+" + "+aux2+" respuesta "+res);
                  if(res == Integer.parseInt(des.opciones[0]) )
                   {
                        con.makeResponder(""+0);
                        System.out.println("elegida "+0);
                   }   
                   else
                   {
                       if(res == Integer.parseInt(des.opciones[1]))
                       {
                           con.makeResponder(""+1);
                           System.out.println("elegida "+1);
                       }   
                       else
                       {
                           con.makeResponder(""+2);
                           System.out.println("elegida "+2);
                       }
                   }
                 
                   // System.out.println("corecta "+des.correcta);
                }
                
            }
        }

    }*/

}
