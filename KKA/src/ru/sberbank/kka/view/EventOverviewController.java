package ru.sberbank.kka.view;

import ru.sberbank.kka.model.Event;
import ru.sberbank.kka.Main;
import java.sql.Date;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EventOverviewController {
	
	private static Logger log = Logger.getLogger(Main.class.getName());
	private Main main;
	@FXML
	private TableView<Event> eventTable;
	@FXML
	private TableColumn<Event, Number> pkIdColumn;
	@FXML
	private TableColumn<Event, Date> eventDateColumn;
	@FXML
	private TableColumn<Event, String> usernameColumn;
	@FXML
	private TableColumn<Event, String> eventLevelColumn;
	@FXML
	private TableColumn<Event, String> classifierColumn;
	@FXML
	private TableColumn<Event, String> messageColumn;
	@FXML
	private TableColumn<Event, Number> revisionColumn;
	@FXML
	private TableColumn<Event, String> businessLineColumn;
	@FXML
	private DatePicker eventDatePicker;
	@FXML
	private Button eventRefreshBtn;
	@FXML
	private void initialize() {
		pkIdColumn.setCellValueFactory(
			cellData-> cellData.getValue().pkIdProperty());
		eventDateColumn.setCellValueFactory(
			cellData-> cellData.getValue().eventDateProperty());
		usernameColumn.setCellValueFactory(
			cellData-> cellData.getValue().usernameProperty());
		eventLevelColumn.setCellValueFactory(
			cellData-> cellData.getValue().eventLevelProperty());
		classifierColumn.setCellValueFactory(
			cellData-> cellData.getValue().classifierProperty());
		messageColumn.setCellValueFactory(
			cellData-> cellData.getValue().messageProperty());
		revisionColumn.setCellValueFactory(
			cellData-> cellData.getValue().revisionProperty());
		businessLineColumn.setCellValueFactory(
			cellData-> cellData.getValue().businessLineProperty());	
    }
	
	public void setMain(Main wnd) {
		log.info("\tFilling primary scene with Events...");
		main = wnd;
		FilteredList<Event> filteredData= new FilteredList<Event>(main.getEventData(), p -> true);
		eventDatePicker.valueProperty().addListener(
			(observable, oldValue,newValue)-> filteredData.setPredicate(
				event -> {
					if (newValue == null || newValue.toString().isEmpty())
						return true;
					return event.getEventDate().toString().contains(newValue.toString());
				}
			)
		);
		SortedList<Event> sortedData = new SortedList<Event>(filteredData);
		sortedData.comparatorProperty().bind(eventTable.comparatorProperty());
		eventTable.setItems(sortedData);
		
		eventRefreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FilteredList<Event> filteredData= new FilteredList<Event>(main.getEventData(), p -> true);
				eventDatePicker.valueProperty().addListener(
					(observable, oldValue,newValue)-> filteredData.setPredicate(
						eventRef -> {
							if (newValue == null || newValue.toString().isEmpty())
								return true;
							return eventRef.getEventDate().toString().contains(newValue.toString());
						}
					)
				);
				SortedList<Event> sortedData = new SortedList<>(filteredData);
				sortedData.comparatorProperty().bind(eventTable.comparatorProperty());
				eventTable.setItems(sortedData);
		}});
		log.info("\tPrimary scene filled!");
	}
}