package ru.sberbank.kka.db;

import java.io.Closeable;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.pool.OracleDataSource;
import ru.sberbank.kka.Main;
import ru.sberbank.kka.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Repository implements Closeable{

	private Connection conn;
	private static Logger log = Logger.getLogger(Main.class.getName());
	
	public Repository() throws ClassNotFoundException, SQLException
	{
		log.info("\tEstablishing connection to  DB...");
		OracleDataSource ods = new OracleDataSource();
		ods.setUser(Main.properties.getProperty("DBuser"));
	    ods.setPassword(Main.properties.getProperty("DBpass"));
	    ods.setURL(Main.properties.getProperty("DBconn"));
		conn = ods.getConnection();
		log.info("\tDB connected!");
	}
	
	public ObservableList<Event> getEvents() throws SQLException
	{
		log.info("\tGetting Events from DB...");
		ObservableList<Event> eventData = 
		FXCollections.observableArrayList();
		Statement cmd = conn.createStatement();
		String sql = "select * from EVENT_LOG t where t.eventdate <= to_date('21.01.2016','DD.MM.YYYY')";
		log.info(String.format("\tSQL query:\n\t %s",sql));
		ResultSet result = cmd.executeQuery(sql);
		int i=0;
		while (result.next())
		{
			int pkId = result.getInt("pk.id");
			Date eventDate = result.getDate("EVENTDATE");
			String username = result.getString("USERNAME");
			String eventLevel = result.getString("EVENTLEVEL");
			String classifier = result.getString("CLASSIFIER");
			String message = result.getString("MESSAGE");
			int revision = result.getInt("REVISION");
			String businessLine = result.getString("BUSINESSLINE");
			eventData.add(new Event(pkId, eventDate, username, eventLevel, classifier, message, revision, businessLine));
			i+=1;
		}
		result.close();
		log.info(String.format("\tEvents query is done! %d rows queried from DB.",i));
		return eventData;		
	}
	
	@Override
	public void close() {
		try {
			conn.close();
			log.info("\tDB disconnected!");
		} catch (SQLException e) {
			log.log(Level.SEVERE,String.format("\tSQL query error.\n Error message: %s",e.getMessage()),e);
		} 
		
	}

}
