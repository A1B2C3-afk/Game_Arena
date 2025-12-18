package view;
import controllers.InputHandler; 
import main.GameLoop;           
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import view.ArenaView; 

public class MenuScene {

    private Stage stage;
    private final String ASSET_ROOT = "/imagesandstyles/";
    public MenuScene(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth();
        double height = screenBounds.getHeight();

        Image bgImage = new Image(getClass().getResourceAsStream(ASSET_ROOT + "1.png"));
        ImageView bgView = new ImageView(bgImage);

        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        Button enterButton = new Button("⚔ ENTER ARENA ⚔");
        
         PHASE 2: SELECTION ELEMENTS (Defined here, added to VBox later) 
        
        Label p1Label = new Label("Player 1 Character");
        p1Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);");
        
        ComboBox<String> p1Select = new ComboBox<>();
        p1Select.getItems().addAll("Warrior", "Mage", "Sniper");
        p1Select.getSelectionModel().selectFirst();

        Label p2Label = new Label("Player 2 Character");
        p2Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, black, 2, 1, 0, 0);");

        ComboBox<String> p2Select = new ComboBox<>();
        p2Select.getItems().addAll("Warrior", "Mage", "Sniper");
        p2Select.getSelectionModel().selectFirst();

        Button fightButton = new Button("FIGHT!");

        Rectangle overlay = new Rectangle(width, height, Color.BLACK);
        overlay.setOpacity(0);
        overlay.setMouseTransparent(true);
        overlay.widthProperty().bind(stage.widthProperty());
        overlay.heightProperty().bind(stage.heightProperty());

 
        enterButton.setOnAction(e -> {
            overlay.setMouseTransparent(false);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.8), overlay);
            fadeOut.setFromValue(0);
            fadeOut.setToValue(1);

            fadeOut.setOnFinished(event -> {
                contentBox.getChildren().clear();
                contentBox.getChildren().addAll(p1Label, p1Select, p2Label, p2Select, fightButton);
                
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.8), overlay);
                fadeIn.setFromValue(1);
                fadeIn.setToValue(0);
                fadeIn.setOnFinished(ev -> overlay.setMouseTransparent(true));
                fadeIn.play();
            });
            fadeOut.play();
        });

       fightButton.setOnAction(e -> {
            
        
            FadeTransition fadeToBlack = new FadeTransition(Duration.seconds(1.0), overlay);
            fadeToBlack.setFromValue(0.0);
            fadeToBlack.setToValue(1.0);

            fadeToBlack.setOnFinished(event -> {
                String p1Type = p1Select.getValue();
                String p2Type = p2Select.getValue();

                ArenaView gameView = new ArenaView(stage, p1Type, p2Type);

              
                InputHandler input = new InputHandler(gameView.getScene());

              
                GameLoop gameLoop = new GameLoop(gameView, input);

              
                stage.setScene(gameView.getScene());


                gameLoop.start(); 
                
               
                gameView.getScene().getRoot().requestFocus();
                
               
            });

            fadeToBlack.play();
        });
     
        contentBox.getChildren().add(enterButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(bgView, contentBox, overlay);

       
        Scene scene = new Scene(root, width, height);
        
       
        scene.getStylesheets().add(getClass().getResource(ASSET_ROOT + "uistyle.css").toExternalForm());
        return scene;
    }
}
