package Conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conexion {

    String servidor;
    URL url;
    HttpURLConnection conn;
    String token;

    public Conexion(String s) {
        servidor = s;
    }

    //Metodo que permite conectarse al servidor y obtiene el token como respuesta del mismo, el token solo sirve para conexiones
    public String iniciar(String user) throws MalformedURLException, IOException {
        token = "";

        url = new URL(servidor + "/enter?rol=" + user);
        System.out.println("Inicia Conexión");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        System.out.println("iniciando...");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        token = reader.readLine();

        System.out.println("Inicio con Exito");

        return token;
    }

    public String getFullState() throws MalformedURLException, IOException {
        url = new URL(servidor + "/getFullState");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        return res;
    }

    public String getFullStaticState() throws MalformedURLException, IOException {
        url = new URL(servidor + "/getFullStaticState");
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        return res;
    }

    public String makeAction(String action) throws MalformedURLException, IOException {
        url = new URL(servidor + "/action?action=" + action + "&session=" + token);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        return res;
    }

    public String makeMove(String x, String y) throws MalformedURLException, IOException {
        System.out.println("quiero moverme");
        url = new URL(servidor + "/actionMove?x=" + x + "&y=" + y + "&session=" + token);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        return res;
    }
    
    public String makeResponder(String x) throws MalformedURLException, IOException {
        System.out.println("quiero responder ");
        url = new URL(servidor + "/actionAnswer?respuesta=" + x + "&session=" + token);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        return res;
    }

}
