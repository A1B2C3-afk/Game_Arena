package models.characters;

import models.Entity;
import models.Projectile;
import models.weapons.Weapon;
import java.util.ArrayList; 
import java.util.List;      

public abstract class Fighter extends Entity {

    protected double speed;
    protected double health;
    protected double maxHealth;
    
    protected List<Weapon> weapons = new ArrayList<>();
    protected int currentWeaponIndex = 0;
    protected Weapon weapon; 
    private long lastSwitchTime = 0;
    
    public Fighter(double x, double y, double width, double height, double health, double speed) {
        super(x, y, width, height); 
        this.health = health;
        this.maxHealth = health;
        this.speed = speed;
    }

    @Override
    public void update() {
        if (weapon != null) weapon.updateReload();
    }

  
    public void addWeapon(Weapon w) {
        weapons.add(w);
        if (weapons.size() == 1) {
            this.weapon = w;
        }
    }

    public void switchWeapon() {
        if (weapons.isEmpty()) return;
        long now = System.currentTimeMillis();   
        if (now - lastSwitchTime > 200) {
            currentWeaponIndex = (currentWeaponIndex + 1) % weapons.size();
            this.weapon = weapons.get(currentWeaponIndex);
            this.lastSwitchTime = now;
            System.out.println("Switched to: " + weapon.getName());
        }
    }

    public void move(double dx, double dy, double minX, double maxX, double minY, double maxY) {
        double nextX = this.x + (dx * speed);
        double nextY = this.y + (dy * speed);
        if (nextX >= minX && nextX <= maxX - width) this.x = nextX;
        if (nextY >= minY && nextY <= maxY - height) this.y = nextY;

        if (dx > 0) rotation = 0;       
        else if (dx < 0) rotation = 180;
        else if (dy > 0) rotation = 90;  
        else if (dy < 0) rotation = 270; 
    }

    public Projectile attack() {
        if (weapon == null) return null;

        double dirX = 0;
        double dirY = 0;

        if (rotation == 0) {
            dirX = 1; dirY = 0;
        } else if (rotation == 90) {
            dirX = 0; dirY = 1;
        } else if (rotation == 180) {
            dirX = -1; dirY = 0;
        } else if (rotation == 270) {
            dirX = 0; dirY = -1;
        }

        double startX = this.x + (this.width / 2) - 10 + (dirX * 30);
        double startY = this.y + (this.height / 2) - 10 + (dirY * 30);

        return weapon.shoot(startX, startY, dirX, dirY);
    }

   
    public Projectile attack(double dirX, double dirY) {
        if (dirX > 0) rotation = 0;
        else if (dirX < 0) rotation = 180;
        
        if (weapon != null) {
            double startX = this.x + (this.width / 2) - 10; 
            double startY = this.y + (this.height / 2) - 10;
            
            return weapon.shoot(startX, startY, dirX, dirY);
        }
        return null;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0) this.health = 0;
    }
    
    public boolean isAlive() { return this.health > 0; }
    public double getHealth() { return health; }
    public double getMaxHealth() { return maxHealth; }
    public Weapon getWeapon() { return weapon; }
    
    
    public void setWeapon(Weapon weapon) { 
        addWeapon(weapon); 
    }
}
