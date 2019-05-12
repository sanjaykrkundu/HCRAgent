package io.github.sanjaykrkundu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * 
 * @author sanjaykrkundu
 *
 */
public class ClassTransformer implements ClassFileTransformer {
	
	private static final Logger LOGGER = Logger.getLogger(ClassTransformer.class.getName());
	
	static {
		LOGGER.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		LOGGER.addHandler(consoleHandler);
	}
	
	@SuppressWarnings("resource")
	@Override
	public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined,
			final ProtectionDomain protectionDomain, final byte[] classfileBuffer) throws IllegalClassFormatException {

		if (className.startsWith(HCRAgent.replaceSource)) {
			File file = new File(HCRAgent.replaceTarget + File.separator + className + ".class");
			LOGGER.info("--> Replacing " + className + " class, class modified on "
					+ HCRAgent.dateFormatter.format(new Date(file.lastModified())) + ".");
			byte[] data = new byte[(int) file.length()];
			try {
				new FileInputStream(file).read(data);
				LOGGER.info("---> " + className + " loaded.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return data;
		}
		return null;

	}

	void print() {

		System.out.println("this is print");
		LOGGER.info("this is logger");
		LOGGER.info("this is logger 1");
	}
	
	public static void main(String[] args) {
		ClassTransformer c = new ClassTransformer();
		c.print();
		
	}
	
}