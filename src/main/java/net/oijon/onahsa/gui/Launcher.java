package net.oijon.onahsa.gui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import net.oijon.onahsa.console.Console;
import net.oijon.onahsa.console.Functions;
import net.oijon.onahsa.tts.trm.TRM;

/**
 * @author N3ther
 *
 */
public class Launcher {

	/**
	 * Launches the GUI, as JavaFX cannot handle the GUI being launched directly.
	 * Also creates default files (packs classic and newclassic).
	 * @param args Passed on to GUI to run.
	 */
	public static void main(String[] args) {
		
		Functions.checkLegacyFiles();
		
		System.out.println(Arrays.toString(args));
		try {
			TRM.makeTestSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Functions.copyFiles();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (Arrays.stream(args).anyMatch("-c"::equals)) {
			Console.run();
		} else {
			GUI.main(args);
		}
    }
}
	
