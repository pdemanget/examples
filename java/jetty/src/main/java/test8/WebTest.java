package test8;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WebTest extends JFrame {

    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;

    String url = "http://google.com";

    public WebTest() {
        super();
        initComponents();
        getContentPane().add(jfxPanel);
        setSize(500, 500);
        // Kill everything on closing the frame
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        Platform.runLater(new Runnable() {
            public void run() {
                WebView view = new WebView();
                engine = view.getEngine();
                engine.load(url);
                Scene scene = new Scene(view);
                jfxPanel.setScene(scene);
            }
        });
    }

    public static void main(String[] args) {
        WebTest main = new WebTest();
        main.setVisible(true);
    }
}