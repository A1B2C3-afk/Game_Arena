package controllers;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashSet;
import java.util.Set;

public class InputHandle
    private Set<KeyCode> activeKeys = new HashSet<>();

    public InputHandler(Scene scene) {

        scene.setOnKeyPressed(event -> {
            activeKeys.add(event.getCode());
        });


        scene.setOnKeyReleased(event -> {
            activeKeys.remove(event.getCode());
        });
    }


    public boolean isKeyPressed(KeyCode key) {
        return activeKeys.contains(key);
    }
    
    public void clear() {
        activeKeys.clear();
    }
}
