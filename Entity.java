package models;

import javafx.geometry.Rectangle2D;

public abstract class Entity {
    protected double x, y;
    protected double width, height;
    
    
    protected double rotation = 0; 

    public Entity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();

    public Rectangle2D getHitbox() {
        return new Rectangle2D(x, y, width, height);
    }

  
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }


    public double getRotation() { return rotation; }
    public void setRotation(double rotation) { this.rotation = rotation; }
}