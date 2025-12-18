package models.weapons;

import models.Projectile;

public class Pistol extends Weapon {

    public Pistol() {
        super("Pistol", 15, 12.0, 500, 12); 
    }

    @Override
    public Projectile createProjectile(double x, double y, double dirX, double dirY) {
        return new Projectile(x, y, dirX, dirY, this.projectileSpeed, this.damage);
    }
}