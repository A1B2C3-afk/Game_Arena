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

        ImageView bgView = new ImageView();
        try {
            if (getClass().getResourceAsStream(ASSET_ROOT + "1.png") != null) {
                Image bgImage = new Image(getClass().getResourceAsStream(ASSET_ROOT + "1.png"));
                bgView.setImage(bgImage);
            }
            bgView.fitWidthProperty().bind(stage.widthProperty());
            bgView.fitHeightProperty().bind(stage.heightProperty());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);

        Button enterButton = new Button("⚔ ENTER ARENA ⚔");
        
        Label p1Label = new Label("Player 1 Character");
        p1Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        ComboBox<String> p1Select = new ComboBox<>();
        p1Select.getItems().addAll("Warrior", "Mage", "Sniper");
        p1Select.getSelectionModel().selectFirst();

        Label p2Label = new Label("Player 2 Character");
        p2Label.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

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
            contentBox.getChildren().clear();
            contentBox.getChildren().addAll(p1Label, p1Select, p2Label, p2Select, fightButton);
        });

        fightButton.setOnAction(e -> {
            overlay.setMouseTransparent(false);
            
            FadeTransition fadeToBlack = new FadeTransition(Duration.seconds(1.0), overlay);
            fadeToBlack.setFromValue(0.0);
            fadeToBlack.setToValue(1.0);

            fadeToBlack.setOnFinished(event -> {
                try {
                    String p1Type = p1Select.getValue();
                    String p2Type = p2Select.getValue();

                    ArenaView gameView = new ArenaView(stage, p1Type, p2Type);
                    gameView.setMenuScene(stage.getScene()); 

                    InputHandler input = new InputHandler(gameView.getScene());
                    GameLoop gameLoop = new GameLoop(gameView, input);

                    stage.setScene(gameView.getScene());
                    gameLoop.start(); 
                    
                    gameView.getScene().getRoot().requestFocus();
                    
                    overlay.setOpacity(0);
                    overlay.setMouseTransparent(true);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    overlay.setOpacity(0);
                    overlay.setMouseTransparent(true);
                }
            });

            fadeToBlack.play();
        });

        contentBox.getChildren().add(enterButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(bgView, contentBox, overlay);

        Scene scene = new Scene(root, width, height);
        
        try {
            if (getClass().getResource("/style.css") != null) {
                scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            } else if (getClass().getResource(ASSET_ROOT + "uistyle.css") != null) {
                scene.getStylesheets().add(getClass().getResource(ASSET_ROOT + "uistyle.css").toExternalForm());
            }
        } catch (Exception e) {}
        
        return scene;
    }
}
