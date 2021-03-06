package fil;

import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import netscape.javascript.JSObject;
/**
 * http://stackoverflow.com/questions/17522343/custom-javafx-webview-protocol-handler
 * http://stackoverflow.com/questions/16215844/javafx-webview-disable-same-origin-policy-allow-cross-domain-requests
 * 
 * http://stackoverflow.com/questions/14385233/setting-a-cookie-using-javafxs-webengine-webview
 * WebView webView = new WebView();
URI uri = URI.create("http://mysite.com");
Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
headers.put("Set-Cookie", Arrays.asList("name=value"));
java.net.CookieHandler.getDefault().put(uri, headers);
webView.getEngine().load("http://mysite.com");
 * 
 * @author pdemanget
 *
 */
public class WebkitApplication extends Application {

	private static final String INDEX = "myapp://resource/test.html";

	@Override
	public void start(Stage stage) {
		URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());
		stage.setTitle("HTML");
		//stage.setFullScreen(true);

		stage.setMaximized(true);
//		stage.setWidth(500);
//		stage.setHeight(500);
		Scene scene = new Scene(new Group(),Color.TRANSPARENT);
		VBox root = new VBox();
		if(false){
		    root.setStyle("-fx-background-color: transparent;");
		    stage.initStyle(StageStyle.TRANSPARENT);
		    stage.setMaximized(true);
		}else{
			stage.setMaximized(true);
		}

		final WebView browser = new WebView();
		browser.setPrefHeight(1600);

		final WebEngine webEngine = browser.getEngine();
		Hyperlink hpl = new Hyperlink("www.google.com");
		hpl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				webEngine.load("http://www.google.com");
			}
		});

		MenuButton menuButton = new MenuButton("go", null,getMenuItem(webEngine,"google","http://www.google.com")
				,getMenuItem(webEngine,"local","http://localhost:8001"), getMenuItem(webEngine,"index",INDEX));
		menuButton.setOnAction((e)->{webEngine.load("http://www.google.com");});
		root.getChildren().addAll( menuButton, browser);
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		//webEngine.load("http://localhost:8001/index.html");
		//webEngine.loadContent("<html><head><title>IWH</title></head><body><h1>test</h1><script>alert('toto');</script></body></html>");
		webEngine.load(INDEX);
		//webEngine.on
		scene.setRoot(root);

		stage.setScene(scene);
		stage.show();
		JSObject window = (JSObject) webEngine.executeScript("window");
		window.setMember("app", this);

	}

	private MenuItem getMenuItem(final WebEngine webEngine, String label, String url) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.setOnAction((e)->{webEngine.load(url);});
		return menuItem;
	}

	public void log(String msg){
		System.out.println(msg);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
