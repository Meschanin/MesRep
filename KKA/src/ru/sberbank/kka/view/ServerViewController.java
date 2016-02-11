package ru.sberbank.kka.view;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ru.sberbank.kka.Main;
import ru.sberbank.kka.db.ConfigReader;

public class ServerViewController {
	private static Logger log = Logger.getLogger(Main.class.getName());
	private static ArrayList<String> serviceList = ConfigReader.stringToArrayList(Main.properties.getProperty("services"));
	private ArrayList<ServiceViewController> svcCtrlList = new ArrayList<ServiceViewController>();
	@FXML
	private VBox srvVBox;
	@FXML
	private HBox srvHBox;
	@FXML
	private Button srvRefreshBtn;
	@FXML
	private Label srvName;
	
	@FXML
	private void initialize() {
		srvName.setText("unknown");
	}
	
	public void setMain(String serverName) {
		log.info("\t"+serverName+": ServiceView initialization...");
		srvName.setText(serverName);
		serviceList.forEach( service -> {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ServerViewController.class.getResource("ServiceView.fxml"));
				HBox serviceView = (HBox)loader.load();
				srvVBox.getChildren().add(serviceView);
				ServiceViewController serviceContr = loader.getController();
				serviceContr.setMain(serverName,service);
				svcCtrlList.add(serviceContr);
			} catch(Exception e) {
				log.log(Level.SEVERE,String.format("\t"+serverName+": Ooops! Error happend in ServiceView while loading.\n Error message: %s",e.getMessage()),e);
			}	
		});
		log.info("\t"+serverName+": ServiceView initialization is done!");
		
		srvRefreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				log.info("\t"+serverName+" : Refreshing Services ...");
				svcCtrlList.forEach(controller -> controller.refreshState());
				log.info("\t"+serverName+" : Services refreshed!");
			}
		});
	}
}
