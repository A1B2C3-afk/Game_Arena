package models;

import javafx.geometry.Rectangle2D;

public class Projectile extends Entity {

    private double speed;
    private int damage;
    private double directionX; 
    private double directionY; 
    private boolean active;    

    public Projectile(double x, double y, double dirX, double dirY, double speed, int damage) {
        
        
        super(x, y, 20, 20); 
        
        this.directionX = dirX;
        this.directionY = dirY;
        this.speed = speed;
        this.damage = damage;
        this.active = true;
    }

    @Override
    public void update() {
        
        this.x += directionX * speed;
        this.y += directionY * speed;

       
        if (x < -50 || x > 2000 || y < -50 || y > 1500) {
            this.active = false;
        }
    }

    
    public int getDamage() { 
        return damage; 
    }

    public boolean isActive() { 
        return active; 
    }

    public void deactivate() { 
        this.active = false; 
    }

	public double getDirectionX() {
		
		return directionX;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getDirectionY() {
		return directionY;
	}

	public void setDirectionY(double directionY) {
		this.directionY = directionY;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setDirectionX(double directionX) {
		this.directionX = directionX;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
    public Rectangle2D getHitbox() {
        
        double padding = width * 0.25; 
        
        return new Rectangle2D(
            x + padding, 
            y + padding, 
            width - (padding * 2), 
            height - (padding * 2)
        );
    }
	
	
}