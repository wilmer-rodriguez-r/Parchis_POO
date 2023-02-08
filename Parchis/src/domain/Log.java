package domain;

import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

/**
 * Clase que atrapa las excepciones y las pone en un log
 * 
 * @author Wilmer Rodríguez, Miguel Monroy
 * @version 1.0.0
 */
public class Log {
	public static String nombre = "Poobchis";

	/**
	 * Atrapa una exception y la pone un log para el desarrollador
	 * 
	 * @param e de tipo Exception
	 */
	public static void record(Exception e) {
		try {
			Logger logger = Logger.getLogger(nombre);
			logger.setUseParentHandlers(false);
			FileHandler file = new FileHandler(nombre + ".log", true);
			file.setFormatter(new SimpleFormatter());
			logger.addHandler(file);
			logger.log(Level.SEVERE, e.toString(), e);
			file.close();
		} catch (Exception oe) {
			oe.printStackTrace();
			System.exit(0);
		}
	}
}
