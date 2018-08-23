package davin;

import davin.pokerbot.Bot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField runtime_input;
    @FXML private TextArea log_viewer;

    public static String img = "./src/img";


    public void initialize(URL location, ResourceBundle resources) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
    }

    @FXML private void start() throws IOException,AWTException {
        Bot bot;
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
    @FXML private void pause(){

    }
    @FXML private void stop(){


    }
    public void appendText(String text){
        //log_viewer.appendText(text);
        Platform.runLater(() -> log_viewer.appendText(text));

    }
}
