/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.Point;

/**
 *
 * @author PC
 */
public abstract class Entidad 
{
    protected String id;
    protected String name;
    protected boolean destroy;
    protected double x; 
    protected double y;
    public Entidad()
    {
    }
    public boolean puedeSeguir()
    {
        return false;
    }

    public String getId()
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public boolean isDestroy() 
    {
        return destroy;
    }

    public double getX() 
    {
        return x;
    }

    public double getY() 
    {
        return y;
    }
    public Point getPosition() 
    {
        return new Point((int) x, (int) y);
    }

    
}
