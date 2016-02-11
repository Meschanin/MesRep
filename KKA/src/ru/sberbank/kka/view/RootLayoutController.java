package ru.sberbank.kka.view;

//import java.util.ArrayList;
//import java.util.logging.Logger;

//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//import ru.sberbank.kka.Main;
//import ru.sberbank.kka.db.ConfigReader;

public class RootLayoutController {
	//private static Logger log = Logger.getLogger(Main.class.getName());
	//private static ArrayList<String> serverList;
	@FXML
	private ToolBar rootToolBar;
	@FXML
	private Button refreshAllBtn;
	@FXML
	private SplitPane rootSplitPane;
	@FXML
	private AnchorPane rootAnchor;
	@FXML
	private VBox rootVBox;

	@FXML
	private void initialize() {
	}
	
	public ToolBar getRootToolBar(){
		return rootToolBar;
	}
	
	public SplitPane getRootSplitPane(){
		return rootSplitPane;
	}
	
	public AnchorPane getRootAnchor(){
		return rootAnchor;
	}
	
	public VBox getRootVBox(){
		return rootVBox;
	}
	
	public void setMain(){}
}
