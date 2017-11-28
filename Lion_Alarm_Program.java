package lion_alarm_program;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Lion_Alarm_Program extends Application {
    
    private double xPosition = 0;
    private double yPosition = 0;
    
    
    @Override
    public void start(Stage stage) throws IOException {  
        
        Parent root = FXMLLoader.load(AlarmController.class.getResource("Alarm.fxml"));
         
         
        //make it movable
        //set on mousepress /drag
        
        //set mouse pressed
         root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xPosition = event.getSceneX();
                yPosition = event.getSceneY();
            }
        });
         //set mouse drag
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xPosition);
                stage.setY(event.getScreenY() - yPosition);
            }
        });
        
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        
        stage.setScene(scene);
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
