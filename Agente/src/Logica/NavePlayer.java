package Logica;


import org.dyn4j.geometry.Vector2;
import org.json.simple.JSONObject;

public class NavePlayer extends Entidad
{

    // State
    
    private Vector2 velocidad; // Velocidad de la nave

    //Nave
    private int countProyectil;
    private Vector2 direccion;
    private double angulo;

    // NavePlayer
    private int health;
    private int healthMax;
    private boolean leave;
    private boolean dead;
    private int puntaje;
    //private LinkedList<Nave> navesAliadas; // No esta 'Nave' 
    private int idBullets;
    private String pregunta;
    private int[] opciones;
    private boolean bloqueado;
    private int respuesta;

    

    public NavePlayer() 
    {
        super();
    }

    public void setVelocidad(Vector2 velocidad) {
        this.velocidad = velocidad;
    }
    @Override
    public boolean puedeSeguir()
    {
        boolean dis = false;
        if(!this.bloqueado && !this.dead)
            dis = true;
        return dis;
    }
    public boolean getDead()
    {
        return dead;
    }
    
    
    public Vector2 getVelocidad() {
        return velocidad;
    }
    public boolean getBloqueado()
    {
        return bloqueado;
    }

    public void fromJSON(JSONObject object) { // Temporal, falta definir bien el toJSON
        //JSONObject opciones = (JSONObject) object.get("opciones");
        JSONObject nave = (JSONObject) ((JSONObject) object.get("super")).get("Nave");
        JSONObject entity = (JSONObject) ((JSONObject) nave.get("super")).get("Entity");
        JSONObject state = (JSONObject) ((JSONObject) entity.get("super")).get("State");

        /*for (Iterator iterator = opciones.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();

        }*/
        
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

        // NavePlayer
        this.health = (int) (long) object.get("health");
        this.healthMax = (int) (long) object.get("healthMax");
        this.leave = (Boolean) object.get("leave");
        this.dead = (Boolean) object.get("dead");
        this.puntaje = (int) (long) object.get("puntaje");
        //this.navesAliadas; // El JSON trae los id de las navesAliadas
        this.idBullets = (int) (long) object.get("idBullets");
        this.pregunta = (String) object.get("pregunta");
        //this.opciones;
        this.bloqueado = (boolean) object.get("bloqueado");
        this.respuesta = (int) (long) object.get("respuesta");

    }

}
