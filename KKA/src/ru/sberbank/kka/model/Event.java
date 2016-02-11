package ru.sberbank.kka.model;

import javafx.beans.property.*;
import java.sql.Date;

public class Event {

	private final IntegerProperty pkId;
	private final ObjectProperty<Date> eventDate;
	private final StringProperty username;
	private final StringProperty eventLevel;
	private final StringProperty classifier;
	private final StringProperty message;
	private final IntegerProperty revision;
	private final StringProperty businessLine;
	
	public Event()
	{
		this(0, null, null, null, null, null, 0, null);
	}
	
	public Event(Integer pkId, Date eventDate, String username, String eventLevel, String classifier, String message, Integer revision, String businessLine)
	{
		this.pkId = new SimpleIntegerProperty(pkId);
		this.eventDate = new SimpleObjectProperty<Date>(eventDate);
		this.username = new SimpleStringProperty(username);
		this.eventLevel = new SimpleStringProperty(eventLevel);
		this.classifier = new SimpleStringProperty(classifier);
		this.message = new SimpleStringProperty(message);
		this.revision = new SimpleIntegerProperty(revision);
		this.businessLine = new SimpleStringProperty(businessLine);
	}

	public IntegerProperty pkIdProperty() {
		return this.pkId;
	}
	public Integer getPkId() {
		return this.pkIdProperty().get();
	}
	public void setPkId(Integer pkId) {
		this.pkIdProperty().set(pkId);
	}
	
	
	public ObjectProperty<Date> eventDateProperty() {
		return this.eventDate;
	}
	public Date getEventDate() {
		return this.eventDateProperty().get();
	}
	public void setEventDate(Date eventDate) {
		this.eventDateProperty().set(eventDate);
	}
	
	
	public StringProperty usernameProperty() {
		return this.username;
	}
	public String getUsername() {
		return this.usernameProperty().get();
	}
	public void setUsername(String username) {
		this.usernameProperty().set(username);
	}
	
	
	public StringProperty eventLevelProperty() {
		return this.eventLevel;
	}
	public String getEventLevel() {
		return this.eventLevelProperty().get();
	}
	public void setEventLevel(String eventLevel) {
		this.eventLevelProperty().set(eventLevel);
	}
	
	
	public StringProperty classifierProperty() {
		return this.classifier;
	}
	public String getClassifier() {
		return this.classifierProperty().get();
	}
	public void setClassifier(String classifier) {
		this.classifierProperty().set(classifier);
	}
	
	
	public StringProperty messageProperty() {
		return this.message;
	}
	public String getMessage() {
		return this.messageProperty().get();
	}
	public void setMessage(String message) {
		this.messageProperty().set(message);
	}


	public IntegerProperty revisionProperty() {
		return this.revision;
	}
	public Integer getRevision() {
		return this.revisionProperty().get();
	}
	public void setRevision(Integer revision) {
		this.revisionProperty().set(revision);
	}
	
	
	public StringProperty businessLineProperty() {
		return this.businessLine;
	}
	public String getBusinessLine() {
		return this.businessLineProperty().get();
	}
	public void setBusinessLine(String businessLine) {
		this.businessLineProperty().set(businessLine);
	}
}
