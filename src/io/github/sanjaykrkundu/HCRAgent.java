package io.github.sanjaykrkundu;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * 
 * @author sanjaykrkundu
 *
 */
public class HCRAgent {
	static String replaceTarget;
	static String replaceSource;

	protected static final DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
	private static final Logger LOGGER = Logger.getLogger(HCRAgent.class.getName());
	
	static {
		LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		LOGGER.addHandler(consoleHandler);
	}
	
	public static void premain(String agentArgs, Instrumentation inst) {
		replaceTarget = System.getProperty("replaceTarget");
		replaceSource = System.getProperty("replaceSource");

		if (replaceSource != null && replaceTarget != null) {
			replaceSource = replaceSource.replaceAll("\\.", File.separator).replaceAll("\\*", "");
			replaceTarget = replaceTarget.replaceAll("\\.", File.separator).replaceAll("\\*", "");
			LOGGER.info("************************************************************");
			LOGGER.info("Custom class transformer loaded for below configuration : ");
			LOGGER.info("replaceSource : " + replaceSource);
			LOGGER.info("replaceTarget :" + replaceTarget);
			LOGGER.info("************************************************************");
			
			final ClassTransformer transformer = new ClassTransformer();
			inst.addTransformer(transformer);
		}
		LOGGER.info("Agent Loaded");
	}
}
