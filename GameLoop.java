package main;

import controllers.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import models.Entity;
import models.characters.Fighter;
import models.Projectile;
import view.ArenaView;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends AnimationTimer {

    private ArenaView view;
    private InputHandler input;
    private Fighter p1;
    private Fighter p2;
    private double screenWidth;
    private double screenHeight;
    private List<Entity> toRemove = new ArrayList<>();

    public GameLoop(ArenaView view, InputHandler input) {
        this.view = view;
        this.input = input;
        this.p1 = view.getPlayer1();
        this.p2 = view.getPlayer2();
        this.screenWidth = view.getScene().getWidth();
        this.screenHeight = view.getScene().getHeight();
    }

    @Override
    public void handle(long now) {
        handlePlayer1();
        handlePlayer2();
        p1.update();
        p2.update();
        updateProjectiles();
        view.render();
        checkGameOver();
    }

    private void handlePlayer1() {
        double dx = 0, dy = 0;
        if (input.isKeyPressed(KeyCode.W)) dy = -1;
        if (input.isKeyPressed(KeyCode.S)) dy = 1;
        if (input.isKeyPressed(KeyCode.A)) dx = -1;
        if (input.isKeyPressed(KeyCode.D)) dx = 1;

        p1.move(dx, dy, 0, screenWidth, 0, screenHeight);


        if (input.isKeyPressed(KeyCode.F)) {
            Projectile bullet = p1.attack(); 
            if (bullet != null) {
                view.getEntities().add(bullet);
            }
        }
        if (input.isKeyPressed(KeyCode.G)) {
            p1.switchWeapon();

        }
        if (input.isKeyPressed(KeyCode.H)) {
            p1.getWeapon().reload();
    }
    }
    private void handlePlayer2() {

        double dx = 0, dy = 0;
        if (input.isKeyPressed(KeyCode.UP))    dy = -1;
        if (input.isKeyPressed(KeyCode.DOWN))  dy = 1;
        if (input.isKeyPressed(KeyCode.LEFT))  dx = -1;
        if (input.isKeyPressed(KeyCode.RIGHT)) dx = 1;


        p2.move(dx, dy, 0, screenWidth, 0, screenHeight);


        if (input.isKeyPressed(KeyCode.L)) {

            Projectile bullet = p2.attack(); 
            if (bullet != null) {
                view.getEntities().add(bullet);
            }
        }
        if (input.isKeyPressed(KeyCode.K)) {
            p2.switchWeapon();
        }

        if (input.isKeyPressed(KeyCode.J)) {
            p2.getWeapon().reload();
        }

    }

    private void updateProjectiles() {

        toRemove.clear();
        
        List<Entity> allEntities = view.getEntities();

        for (int i = 0; i < allEntities.size(); i++) {
            Entity e = allEntities.get(i);
            
            if (e instanceof Projectile) {
                Projectile p = (Projectile) e;
                p.update();


                if (!p.isActive()) {
                    toRemove.add(p);
                    continue;
                }


                if (p.getHitbox().intersects(p1.getHitbox())) {
                    p1.takeDamage(p.getDamage());
                    p.deactivate(); 
                    toRemove.add(p);
                }
                else if (p.getHitbox().intersects(p2.getHitbox())) {
                    p2.takeDamage(p.getDamage());
                    p.deactivate();
                    toRemove.add(p);
                }
            }
        }
        allEntities.removeAll(toRemove);
    }
    
    private void checkGameOver() {
        if (!p1.isAlive()) {
            this.stop();
            view.showWinner("PLAYER 2"); 
        } else if (!p2.isAlive()) {
            this.stop();
            view.showWinner("PLAYER 1");
        }
    }
}