package lion_alarm_program;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.UnaryOperator;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AlarmController implements Initializable {
    
    
    @FXML
    private ImageView close;
    
    @FXML
    private JFXSpinner jSpinner1;

    @FXML
    private ImageView alarmAtImg;
    
    @FXML
    private ImageView alarmAfterImg;

    @FXML
    private AnchorPane alarmAt;

    @FXML
    private JFXButton butH_Up1;

    @FXML
    private TextField hTextField1;

    @FXML
    private JFXButton butH_Down1;

    @FXML
    private JFXButton butM_Up1;

    @FXML
    private TextField mTextField1;

    @FXML
    private JFXButton butM_Down1;

    @FXML
    private JFXButton validAlarm1;

    @FXML
    private JFXTextField text;

    @FXML
    private JFXComboBox<String> sonAlarm;

    @FXML
    private AnchorPane alarmAfter;

    @FXML
    private JFXButton butH_Up;

    @FXML
    private TextField hTextField;

    @FXML
    private JFXButton butH_Down;

    @FXML
    private JFXButton butM_Up;

    @FXML
    private TextField mTextField;

    @FXML
    private JFXButton butM_Down;

    @FXML
    private JFXButton butS_Up;

    @FXML
    private TextField sTextField;

    @FXML
    private JFXButton butS_Down;

    @FXML
    private JFXButton validAlarm;

    @FXML
    private AnchorPane firstPane;
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView chevronAfter;

    @FXML
    private ImageView chevronAt;
    
    TextFormatter<String> textFormatter;
    
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String txt = change.getText();
        if( txt.matches("[0-9]*") ){
            return change;
        }
        return null;
    };
    
    
    @FXML
    void addAlarmSon(ActionEvent event) {
        
        try{
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Select an audio");
            filechooser.setInitialDirectory(new File(System.getProperty("user.home")));
            filechooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("audio file, .mp3, .aif, .ogg, .wav", "*.wav","*.mp3","*.ogg","*.aif")
                );
            File file = filechooser.showOpenDialog( new Stage() );
            System.out.print("\nFile selected: "+file.toString());
            String folderPathToDelete = "";
            String[] executeCommand = {"/bin/bash","-c","cp -R \"" + file.toString() + "\"  \"/mnt/Nouveau_Nom/STUDY/MES_PROGS/mes_progrs_JAVA FX/Lion_Alarm_Program/src/lion_alarm_program/audio/\""};
            Process proc = Runtime.getRuntime().exec(executeCommand);
            proc.waitFor();
            if( proc.exitValue() == 0 ){
                System.out.println("\nCopie du file: "+file.toString());
            }else{
                System.out.println("\nNo Copie du file: "+file.toString());
                // get the error stream of the process and print it
                InputStream error = proc.getErrorStream();
                for (int i = 0; i < error.available(); i++) {
                   System.out.println(error.read());
                }
            }
            sonAlarm.getItems().remove(0, sonAlarm.getItems().size());
//            chargementAudio();
        
        File[] listfile = new File("/mnt/Nouveau_Nom/STUDY/MES_PROGS/mes_progrs_JAVA FX/Lion_Alarm_Program/src/lion_alarm_program/audio/").listFiles();
        for (File file1 : listfile) {
            System.out.println("\n" + file1.getName()); 
            if (verifyFileExtension(file1.getName())) {
                sonAlarm.getItems().add(file1.getName());
            }
        }
//        if( file.length == 0 ){     
//            return ;
//        }
        sonAlarm.getSelectionModel().selectFirst();
            
        }catch(IOException | InterruptedException e){
            
        }
    }

    @FXML
    void addHeure1(ActionEvent event) {
        if( Integer.parseInt( hTextField1.getText() ) == 23 ){
            hTextField1.setText( 0+"" );
        }else{
            hTextField1.setText( (Integer.parseInt( hTextField1.getText() )+1)+"" );
        }
    }

    @FXML
    void addMinute1(ActionEvent event) {
        if( Integer.parseInt( mTextField1.getText() ) == 59 ){
            mTextField1.setText( 0+"" );
        }else{
            mTextField1.setText( (Integer.parseInt( mTextField1.getText() )+1)+"" );
        }
    }

    @FXML
    void reduceHeure1(ActionEvent event) {
        if( Integer.parseInt( hTextField1.getText() ) == 00 ){
            hTextField1.setText( 23+"" );
        }else{
            hTextField1.setText( (Integer.parseInt( hTextField1.getText() )-1)+"" );
        }
    }

    @FXML
    void reduceMinute1(ActionEvent event) {
        if( Integer.parseInt( mTextField1.getText() ) == 00 ){
            mTextField1.setText( 59+"" );
        }else{
            mTextField1.setText( (Integer.parseInt( mTextField1.getText() )-1)+"" );
        }
    }


    @FXML
    void modifyInput_H1(KeyEvent event) {
        hTextField1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if( convertToInt('h',newValue,oldValue) > 23 ){
                hTextField1.setText(oldValue);
            }
        });
    }

    @FXML
    void modifyInput_M1(KeyEvent event) {
        mTextField1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if( convertToInt('m',newValue,oldValue) > 59 ){
                mTextField1.setText(oldValue);
            }
        });
    }
    
    public int convertToInt(char chr, String string, String newString ){
        try{
            System.out.println("\n\n\n"+string+"\n\n\n");
            return Integer.parseInt(string);
        }catch(Exception e){
            System.out.println("\n\n\n0\n\n\n");
            if( chr == 'h' )
                hTextField1.setText(newString);
            if( chr == 'm' )
                mTextField1.setText(newString);
            return 0;
        }
    }
    
    @FXML
    void alarmMe1(ActionEvent event) {
        
        //Reel fonctionnement
        
        Date dateNow = new Date();
        dateNow.setSeconds(0);
        Date datePourAlarmer = new Date();
        datePourAlarmer.setHours(Integer.parseInt(hTextField1.getText()));
        datePourAlarmer.setMinutes(Integer.parseInt(mTextField1.getText()));
        System.out.println("RESULT: "+datePourAlarmer.compareTo(dateNow));
        int timePourAlarmer =  datePourAlarmer.getHours()*3600 +  datePourAlarmer.getMinutes()*60;
        int timeNow = dateNow.getHours()*3600 + dateNow.getMinutes()*60;
        int timeToAlarm = timePourAlarmer - timeNow;
        
        System.out.println("tmeToAlram choose: "+timeToAlarm+"\n::::::: "+timePourAlarmer+"\n::::::" +timeNow );
        if( timeToAlarm < 0 ){
            System.out.println("tmeToAlram choose: "+timeToAlarm+"\n::::::: "+timePourAlarmer+"\n::::::" +timeNow );
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Temps choisi invalid");
            alert.showAndWait();            
        }else{
            if (timeToAlarm == 0){       
                Mon_Timer1 mon_Timer = new Mon_Timer1(0, "audio/"+sonAlarm.getSelectionModel().getSelectedItem(), text.getText() );
//                Mon_Timer1 mon_Timer = new Mon_Timer1(0, AlarmAt.class.getResource("audio/"+sonAlarm.getSelectionModel().getSelectedItem()).toString(), text.getText() );
            }else{       
                Mon_Timer1 mon_Timer = new Mon_Timer1(timeToAlarm, "audio/"+sonAlarm.getSelectionModel().getSelectedItem(), text.getText() );
            }
        }
//        System.out.println("\nNow: "+dateNow+"\nAlarm: "+datePourAlarmer+"\n DIFF: "+diff);
//        System.out.println("\nDIFF in seconds: "+((diff/1000)));
//        System.exit(0);
       
//        int timeToAlarm = ( datePourAlarmer.getHours()-dateNow.getHours() )*3600 + ( datePourAlarmer.getMinutes()-dateNow.getMinutes() )*60;
        
        
        //Fonctionnement test pour tester la recuperation et la lecture du ficher audio
        
//        System.out.println("\nName of the file choose is: "+ "audio/"+sonAlarm.getSelectionModel().getSelectedItem());
//        Mon_Timer1 mon_Timer = new Mon_Timer1(0, "audio/"+sonAlarm.getSelectionModel().getSelectedItem());
    
    }

    private boolean verifyFileExtension(String name) {
        System.out.println("verifyFileExtension: "+name);
        String fileExtension = "";
        for( int i = name.length() - 4; i< name.length(); i++ ){
            fileExtension += name.charAt(i);
        }
        if( !fileExtension.equals(".wav") && !fileExtension.equals(".mp3") && !fileExtension.equals(".aif") && !fileExtension.equals(".ogg")  ){
            System.out.println("\n.............NOT CORRECT");
            return false;
        }    
            
        System.out.println("\nCORRECT");
        return true;
    }
    
    private boolean audioFolderPath(String name) {
        System.out.println("jar Folder: "+name);
        String[] filePath = name.split("/");
//        for( int i = name.length() - 4; i< name.length(); i++ ){
//            filePath += name.charAt(i);
//        }
        if( !filePath.equals(".wav") && !filePath.equals(".mp3") && !filePath.equals(".aif") && !filePath.equals(".ogg")  ){
            System.out.println("\n.............NOT CORRECT");
            return false;
        }    
            
        System.out.println("\nCORRECT");
        return true;
    }
    
    private void chargementAudio() {
        File[] file = new File("/mnt/Nouveau_Nom/STUDY/MES_PROGS/mes_progrs_JAVA FX/Lion_Alarm_Program/src/lion_alarm_program/audio/").listFiles();
        for (File file1 : file) {
            System.out.println("\n" + file1.getName()); 
            if (verifyFileExtension(file1.getName())) {
                sonAlarm.getItems().add(file1.getName());
            }
        }
//        if( file.length == 0 ){     
//            return ;
//        }
        sonAlarm.getSelectionModel().selectFirst();
        
    }    

    @FXML
    void addHeure(ActionEvent event) {
        if( Integer.parseInt( hTextField.getText() ) == 23 ){
            hTextField.setText( 0+"" );
        }else{
            hTextField.setText( (Integer.parseInt( hTextField.getText() )+1)+"" );
        }
    }

    @FXML
    void addMinute(ActionEvent event) {
        if( Integer.parseInt( mTextField.getText() ) == 59 ){
            mTextField.setText( 0+"" );
        }else{
            mTextField.setText( (Integer.parseInt( mTextField.getText() )+1)+"" );
        }
    }

    @FXML
    void addSeconde(ActionEvent event) {
        if( Integer.parseInt( sTextField.getText() ) == 59 ){
            sTextField.setText( 0+"" );
        }else{
            sTextField.setText( (Integer.parseInt( sTextField.getText() )+1)+"" );
        }
    }

    @FXML
    void reduceHeure(ActionEvent event) {
        if( Integer.parseInt( hTextField.getText() ) == 00 ){
            hTextField.setText( 23+"" );
        }else{
            hTextField.setText( (Integer.parseInt( hTextField.getText() )-1)+"" );
        }
    }

    @FXML
    void reduceMinute(ActionEvent event) {
        if( Integer.parseInt( mTextField.getText() ) == 00 ){
            mTextField.setText( 59+"" );
        }else{
            mTextField.setText( (Integer.parseInt( mTextField.getText() )-1)+"" );
        }
    }

    @FXML
    void reduceSeconde(ActionEvent event) {
        if( Integer.parseInt( sTextField.getText() ) == 00 ){
            sTextField.setText( 59+"" );
        }else{
            sTextField.setText( (Integer.parseInt( sTextField.getText() )-1)+"" );
        }
    }
    
    public void close(){
        System.exit(0);
    }

    @FXML
    void modifyInput_H(KeyEvent event) {
        hTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if( convertToInt('h',newValue,oldValue) > 23 ){
                hTextField.setText(oldValue);
            }
        });
    }

    @FXML
    void modifyInput_M(KeyEvent event) {
        mTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if( convertToInt('m',newValue,oldValue) > 59 ){
                    mTextField.setText(oldValue);
                }
            }
        });
    }
    
    @FXML
    void modifyInput_S(KeyEvent event) {
        sTextField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if( convertToInt('s',newValue,oldValue) > 59 ){
                    sTextField.setText(oldValue);
                }
            }
        });
    }

    @FXML
    void alarmMe(ActionEvent event) {
        int timeToAlarm = Integer.parseInt(hTextField.getText())*3600 + Integer.parseInt(mTextField.getText())*60+ Integer.parseInt(sTextField.getText()) ;
        System.out.println("tmeToAlram choose: "+timeToAlarm);
        Mon_Timer mon_Timer = new Mon_Timer(timeToAlarm);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater( () -> {
            mainPane.setStyle(  
                    "-fx-background-color: rgba(255, 255, 255, 0);" +
                    "-fx-background-insets: 5;"
            );
        });
        
//        mainPane.initStyle(StageStyle.TRANSPARENT);
        firstPane.setVisible(false);
        alarmAfter.setVisible(false);
        alarmAt.setVisible(false);
        
        chevronAfter.setVisible(false);        
        chevronAt.setVisible(false);        
                
        hTextField1.setText( "0" );
        mTextField1.setText( "0" );
        
        textFormatter = new TextFormatter<>(filter);
        hTextField1.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(filter);
        mTextField1.setTextFormatter(textFormatter);
        
        File[] listfile = new File("/mnt/Nouveau_Nom/STUDY/MES_PROGS/mes_progrs_JAVA FX/Lion_Alarm_Program/src/lion_alarm_program/audio/").listFiles();
        for (File file1 : listfile) {
            System.out.println("\n" + file1.getName()); 
            if (verifyFileExtension(file1.getName())) {
                sonAlarm.getItems().add(file1.getName());
            }
        }
        sonAlarm.getSelectionModel().selectFirst();
        
        validAlarm1.disableProperty().bind( 
            Bindings.createBooleanBinding( () -> ( text.getText().isEmpty() || sonAlarm.getItems().isEmpty()  ) ,
                    text.textProperty(),
                    sonAlarm.itemsProperty()
            )
        );
        
        sonAlarm.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if( sonAlarm.getSelectionModel().isEmpty() ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERREUR");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez ajouter au moins un audio.");
                    alert.showAndWait();
                }
            }
        });
        
        
        hTextField.setText("0");
        mTextField.setText("0");
        sTextField.setText("0");
        
        textFormatter = new TextFormatter<>(filter);
        hTextField.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(filter);
        mTextField.setTextFormatter(textFormatter);
        textFormatter = new TextFormatter<>(filter);
        sTextField.setTextFormatter(textFormatter);
     
        alarmAtImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                alarmAfter.setVisible(false);
                firstPane.setVisible(false);
                alarmAt.setVisible(true);
                chevronAt.setVisible(true);
                chevronAfter.setVisible(false);
            }
        });
        
        alarmAfterImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                alarmAt.setVisible(false);
                firstPane.setVisible(false);
                alarmAfter.setVisible(true);
                chevronAfter.setVisible(true);
                chevronAt.setVisible(false);
            }
        });
        
    }
}
    
    
    
class Mon_Timer1 {
    
    Timer mon_Timer;
    String audioFile;
    String message;
    
    public Mon_Timer1(int Secondes, String file, String message) {
        mon_Timer = new java.util.Timer();
        // La tache sera activee dans 5 secondes,
//        mon_Timer.schedule(null, Secondes, Secondes);
        this.audioFile = file;
        this.message = message;
        mon_Timer.schedule(new Tache_Mon_Timer(),  Secondes * 1000);
    }
    
    class Tache_Mon_Timer extends TimerTask {

        @Override
        public void run() {
            System.out.println("Debout! lionel");
            
            //Reel fonctionnement
            Platform.runLater( () ->{
                AudioClip notifyMe = new AudioClip((getClass().getResource( audioFile ) ).toString());
                notifyMe.play();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
                mon_Timer.cancel();
                }
            );
        }
    }

}

class Mon_Timer {
    
    Timer mon_Timer;
    
    public Mon_Timer(int Secondes) {
        mon_Timer = new java.util.Timer();
        // La tache sera activee dans 5 secondes,
//        mon_Timer.schedule(null, Secondes, Secondes);
        mon_Timer.schedule(new Tache_Mon_Timer(),  Secondes * 1000);
    }
    
    class Tache_Mon_Timer extends TimerTask {

        public void run() {
            System.out.println("Debout! lionel");
            AudioClip notifyMe = new AudioClip((getClass().getResource("tombe_sur_elle.mp3")).toString());
            notifyMe.play();
            mon_Timer.cancel();
        }
    }

    
}
