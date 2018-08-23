package davin;

import davin.pokerbot.Bot;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {
    @FXML
    private TextField runtime_input;
    @FXML private TextArea log_viewer;
    @FXML private Button btn_pause;
    @FXML private Label lbl_remain;


    private int paused = 1000;
    public static String img = "./src/img";
    Bot bot;
    private void setRemainTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                String rem = String.valueOf(bot.getRemain());
                                System.out.println(rem);
                                lbl_remain.setText(rem);
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void initialize(URL location, ResourceBundle resources) {
        //setRemainTime();
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));

    }

    @FXML private void start() throws IOException,AWTException {
        Bot robot = null;
        int run = Integer.parseInt(runtime_input.getText());
        Properties properties = new Properties();
        String filename = "settings.txt";
        //InputStream input = new FileInputStream(new File(filename));
        InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
        //File inFile = new File(filename);
        //String path = inFile.getAbsolutePath();
        //InputStream input = new FileInputStream(path);
        try {
            if (input == null) {
                System.out.println("ERROR: "+filename+" file not found in current folder");
                System.out.println("Using default settings");
            } else {
                properties = new Properties();
                //load a properties
                properties.load(input);
            }
        } catch (IOException ex) {
            System.out.println("Error while reading file");
        }
        new Thread(new Bot(properties,run)).start();

        System.out.println("Start");
    }
    @FXML private void pause() throws Exception{
        if(btn_pause.getText().equals("Pause")){

        }else if (btn_pause.getText().equals("Resume")){
            paused = 0;
            Thread.sleep(paused);
            btn_pause.setText("Pause");
        }
    }
    @FXML private void stop(){


    }
    public void appendText(String text){
        //log_viewer.appendText(text);
        Platform.runLater(() -> log_viewer.appendText(text));

    }
}
