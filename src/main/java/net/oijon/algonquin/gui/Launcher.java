package net.oijon.algonquin.gui;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import net.oijon.algonquin.console.Functions;
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
	 * @throws URISyntaxException Should never be thrown, however the compiler yelled at me if I didn't include this.
	 * @throws IOException Thrown if for some reason it cannot create directories. Should never be thrown.
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		System.out.println(Arrays.toString(args));
		try {
			TRM.makeTestSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Functions.copyFiles();
		
		if (Arrays.stream(args).anyMatch("-c"::equals)) {
			
		} else {
			GUI.main(args);
		}
    }
}
	
