package models.characters;

import models.weapons.*;
import models.characters.*;


public class Warrior extends Fighter {

    public Warrior(double x, double y) {
        
        super(x, y, 60, 60, 150, 3.0);
        
        
        this.addWeapon(new Pistol());
        this.addWeapon(new SniperRifle());
        this.addWeapon(new MagicStaff());
    }

    
    public void update() {
        
        super.update();
        
        
    }
}
