package ru.sberbank.kka.view;

import java.util.logging.Logger;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import ru.sberbank.kka.Main;
import ru.sberbank.kka.model.Service;

public class ServiceViewController {
	private static Logger log = Logger.getLogger(Main.class.getName());
	private Service service;
	@FXML	
	private Label svcNameLabel;
	@FXML
	private Label svcStateLabel;
	@FXML
	private Button stopBtn;
	@FXML
	private Button startBtn;
	@FXML
	private TextField logField;

	ScheduledService<Void> refState = new ScheduledService<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					service.setLogState("test refresh");
					return null;
				}
			};
		}
	};
	
	@FXML
	private void initialize() {
		service = new Service();
		svcNameLabel.textProperty().bind(service.getServiceNameProp());
		svcStateLabel.textProperty().bind(service.getStateProp());
		svcStateLabel.textFillProperty();
		logField.textProperty().bind(service.getLogStateProp());
		refState.setPeriod(Duration.seconds(5));
	}
	
	public void refreshState() {
		service.setState(service.getServiceState());
	}
	
	public void setMain(String serverName, String serviceName){
		log.info("\tFilling primary scene with service "+serviceName+" on server "+serverName+" ...");
		service.setServerName(serverName);
		service.setServiceName(serviceName);
		refState.start();
		refState.setOnRunning(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				service.setState(service.getServiceState());
			}
		});
		
		startBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				log.info("\t"+serverName+" : Starting Service "+serviceName+"....");
				service.startService();
				log.info("\t"+serverName+" : Service "+serviceName+" is started!");
			}
		});
		
		stopBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				log.info("\t"+serverName+" : Stopping Service "+serviceName+"....");
				service.stopService();
				log.info("\t"+serverName+" : Service "+serviceName+" is stoped!");
			}
		});
		log.info("\tPrimary scene filled with service "+serviceName+" on server "+serverName+" !");
	}
}
