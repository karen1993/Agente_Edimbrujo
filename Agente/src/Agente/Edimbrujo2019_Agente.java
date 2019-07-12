/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Agente;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emiliano
 */
public class Edimbrujo2019_Agente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            //AgenteMoneda ia = new Agente();
            Agente ia = new Agente("katy");
            Agente ia2 = new Agente("karen");
           Agente ia3 = new Agente("ia3");
            Agente ia4 = new Agente("ia4");
            Agente ia5 = new Agente("ia5");
           while (true) {

                ia.jugar();
                ia2.jugar();
                ia3.jugar();
                ia4.jugar();
                ia5.jugar();
                
            }

        } catch (IOException ex) {
            Logger.getLogger(Edimbrujo2019_Agente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
