package models.characters;

import models.weapons.MagicStaff;
import models.weapons.Pistol;
import models.weapons.SniperRifle;

public class Mage extends Fighter {

    public Mage(double x, double y) {
        
        super(x, y, 50, 50, 80, 4.0);
        
        
        this.setWeapon(new MagicStaff());
        this.addWeapon(new Pistol());
        this.addWeapon(new SniperRifle());
    }

    
    public void update() {
        
        super.update();
    }
}