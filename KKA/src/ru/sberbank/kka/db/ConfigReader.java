package ru.sberbank.kka.db;

import java.util.ArrayList;
import java.util.logging.Logger;
import ru.sberbank.kka.Main;

public class ConfigReader {
	
	private static Logger log = Logger.getLogger(Main.class.getName());
	
	public static  ArrayList<String> stringToArrayList(String list){
		log.info(String.format("\tParsing string:\n%s",list));
		ArrayList<String> parsedString = new ArrayList<>();
		String str = list.replaceAll("\\n", "");
		String[] s = str.split(";");
		for(int i=0;i<s.length;i++)
			parsedString.add(s[i]);
		log.info(String.format("\tParsing finished!",list));
		return parsedString;
	}
}
