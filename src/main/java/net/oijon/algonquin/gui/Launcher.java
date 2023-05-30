package net.oijon.algonquin.gui;

import java.io.IOException;
import java.util.*;
import net.oijon.algonquin.console.Console;
import net.oijon.algonquin.tts.trm.TRM;

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
		
		System.out.println(Arrays.toString(args));
		try {
			TRM.makeTestSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Functions.copyFiles();
		
		if (Arrays.stream(args).anyMatch("-c"::equals)) {
			Console.run();
		} else {
			GUI.main(args);
		}
    }
}
	
