package ru.sberbank.kka;
	
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ru.sberbank.kka.db.ConfigReader;
import ru.sberbank.kka.db.Repository;
import ru.sberbank.kka.model.Event;
import ru.sberbank.kka.view.EventOverviewController;
import ru.sberbank.kka.view.RootLayoutController;
import ru.sberbank.kka.view.ServerViewController;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private SplitPane rootSplitPane;
	private VBox rootVBox;
	private static Logger log = Logger.getLogger(Main.class.getName());
	public static Properties properties = new Properties();
	private static ArrayList<String> serverList;
	private Repository db;
	
	public ObservableList<Event> getEventData() {
		try {
			return db.getEvents();
		} catch (SQLException e) {
			log.log(Level.SEVERE,String.format("\tSQL query error.\n Error message: %s",e.getMessage()),e);
			return FXCollections.observableArrayList();
		}
	}
	
	public Main()
	{
		try {
			db = new Repository();
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE,String.format("\tUnable to find class \"Repository\".\n Error message: %s",e.getMessage()),e);
		} catch (SQLException e) {
			log.log(Level.SEVERE,String.format("\tSQL query error.\n Error message: %s",e.getMessage()),e);
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("KKA Monitoring v.1.6.1");
		initRootLayout();
		showEventOverview();
		showServiceLayout();
	}
	
	public void initRootLayout() {
		try {
			log.info("\tRoot layout initialization...");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane)loader.load();
			RootLayoutController rootContr = loader.getController();
			rootContr.setMain();
			rootSplitPane = rootContr.getRootSplitPane();
			rootVBox = rootContr.getRootVBox();
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			log.info("\tRoot layout initialization is done!");
		} catch(Exception e) {
			log.log(Level.SEVERE,String.format("\tOoops! Error happend in Root layout initialization.\n Error message: %s",e.getMessage()),e);
		}
	}
	
	public void showEventOverview() {
		try {
			log.info("\tEvent View initialization...");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/EventOverview.fxml"));
			AnchorPane eventOverview = (AnchorPane)loader.load();
			rootSplitPane.getItems().set(0,eventOverview);
			EventOverviewController eventContr = loader.getController();
			eventContr.setMain(this);
			log.info("\tEvent View initialization is done!");
		} catch(Exception e) {
			log.log(Level.SEVERE,String.format("\tOoops! Error happend in Event View initialization.\n Error message: %s",e.getMessage()),e);
		}
			
	}

	public void showServiceLayout() {
		log.info("\tServerView initialization...");
		serverList.forEach( server -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource("view/ServerView.fxml"));
				VBox serverView = (VBox)loader.load();
				rootVBox.getChildren().add(serverView);
				ServerViewController serverContr = loader.getController();
				serverContr.setMain(server);
			} catch(Exception e) {
				log.log(Level.SEVERE,String.format("\tOoops! Error happend while serverView loading.\n Error message: %s",e.getMessage()),e);
			}
		});
		log.info("\tServerView initialization is done!");	
	}
	
	public static void main(String[] args) {
		try{
			LogManager.getLogManager().readConfiguration(new FileInputStream(Paths.get("configuration\\logging.properties").toFile()));
		} catch(IOException e){
			System.err.print("Could not setup logger configuration: "+ e.getMessage()+"\n"+e.toString());
		}
		try{
			properties.loadFromXML(new FileInputStream((Paths.get("configuration\\config.xml").toFile())));
			} catch(IOException e){
				System.err.print("Could not setup properties configuration: "+ e.getMessage()+"\n"+e.toString());	
			}
		serverList = ConfigReader.stringToArrayList(Main.properties.getProperty("servers"));
		launch(args);
	}
}
