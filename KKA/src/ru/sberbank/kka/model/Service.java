package ru.sberbank.kka.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.sberbank.kka.Main;

public class Service {
	private static Logger log = Logger.getLogger(Main.class.getName());
	private StringProperty serverName;
	private StringProperty serviceName;	
	private StringProperty state;
	private StringProperty logState;
	
	public String getServerName(){
		return serverName.get();
	}
	public StringProperty getServerNameProp(){
		return serverName;
	}
	public void setServerName(String serverName){
		this.serverName.set(serverName);
	}
	public String getServiceName(){
		return serviceName.get();
	}
	public StringProperty getServiceNameProp(){
		return serviceName;
	}
	public void setServiceName(String serviceName){
		this.serviceName.set(serviceName);
	}
	public String getState(){
		return state.get();
	}
	public StringProperty getStateProp(){
		return state;
	}
	public void setState(String st){
		state.set(st);
	}
	public String getLogState(){
		return logState.get();
	}
	public StringProperty getLogStateProp(){
		return logState;
	}
	public void setLogState(String log){
		logState.set(log);
	}
	
	public Service(){
		serverName = new SimpleStringProperty("UNKNOWN");
		serviceName = new SimpleStringProperty("UNKNOWN");
		state = new SimpleStringProperty("UNKNOWN");
		logState = new SimpleStringProperty("TEST");
	}

	public String getServiceState(){
		log.info("\tStart getServiceState() for "+serviceName.get()+" on server "+serverName.get()+" ...");
		//state.set(execServiceController(serverName.get(),"query",serviceName.get()));
		//log.info("\tgetServiceState() is finished!");
		return execServiceController(serverName.get(),"query",serviceName.get());
	}
	
	public void startService(){
		log.info("\tStart startService() for "+serviceName.get()+" on server "+serverName.get()+" ...");
		logState.set("test start");
		state.set(execServiceController(serverName.get(),"start",serviceName.get()));
		log.info("\tstartService() is finished!");
	}
	
	public void stopService(){
		log.info("\tStart stopService() for "+serviceName.get()+" on server "+serverName.get()+" ...");
		logState.set("test stop");
		state.set(execServiceController(serverName.get(),"stop",serviceName.get()));
		log.info("\tstopService() is finished!");
	}
	
	private String execServiceController(String serverName, String cmd, String serviceName){
		log.info(String.format("\tServer %s : Service %s : executing %s .",serverName,serviceName,cmd));
		String line;
		String st = "STATE_UNKNOWN";
		try {
			Process proc = Runtime.getRuntime().exec("sc \\\\"+serverName+".ca.sbrf.ru " + cmd + " " + serviceName);
			BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream(),"CP866"));
			log.info("\tParsing cmd answer...");
			while ((line = br.readLine()) != null) {
				int p;
				if (((p = line.indexOf("STATE")) != -1) || ((p = line.indexOf("Состояние")) != -1)){
					if ((p = line.indexOf(" : ", p)) != -1) {
						st = line.substring(p + 5, line.length());
					}
				}
			}
			log.info("\tCmd answer parsed!");
			br.close();
			int retCode = proc.waitFor();
			if (retCode != 0)
				log.log(Level.SEVERE,String.format("\tError code of 'sc' is : %s",retCode));
		} catch (IOException e) {
			log.log(Level.SEVERE,String.format("\tCannot execude command %s. Error: %s",cmd,e.getMessage()),e);
		} catch (InterruptedException e) {
			log.log(Level.SEVERE,String.format("\tParsing interrupted! Error: %s",e.getMessage()),e);
		}
		return st;
	}
}
