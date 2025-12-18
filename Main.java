package main;
	import javafx.application.Application;
	import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.image.Image;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.scene.text.FontPosture;
    import javafx.scene.image.ImageView;
    import javafx.scene.media.Media;
    import javafx.scene.media.MediaPlayer;
    import java.nio.file.Paths;
    import javafx.scene.input.KeyEvent;
    import javafx.event.EventHandler;
    import javafx.scene.shape.Rectangle;
    import javafx.scene.paint.ImagePattern;
    import javafx.animation.AnimationTimer;
    import javafx.scene.control.Button;
    import javafx.scene.layout.VBox;
    import javafx.geometry.Pos;
    import javafx.scene.control.Label;
    import javafx.scene.layout.HBox;
    import javafx.scene.text.TextAlignment;
    import javafx.scene.control.TextField;
    import javafx.scene.control.PasswordField;
	import view.MenuScene;
	
public class Main extends Application {
	 
		
	
	public static void main(String[] args) { 
			Application.launch(args);
			

		
	}

	@Override
	public void start(Stage stage) throws Exception {
        
		MenuScene menu = new MenuScene(stage);
		
		stage.setScene(menu.getScene());
		
		
		stage.show();
		stage.setFullScreen(false);
	
	}
}
