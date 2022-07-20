package net.oijon.algonquin.gui;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import net.oijon.algonquin.tts.trm.TRM;

/**
 * @author N3ther
 *
 */
public class GUILauncher {

	public static void copyFiles() throws URISyntaxException, IOException  {
		//Create AlgonquinTTS directory
				//Uses a lot of ifs, but should be ok...
				if (new File(System.getProperty("user.home") + "/AlgonquinTTS").exists() == false) {
		        	new File(System.getProperty("user.home") + "/AlgonquinTTS").mkdir();
		        	System.out.println("Created AlgonquinTTS dir");
		        }
				if (new File(System.getProperty("user.home") + "/AlgonquinTTS/packs").exists() == false) {
		        	new File(System.getProperty("user.home") + "/AlgonquinTTS/packs").mkdir();
		        	System.out.println("Created packs dir");
		        }
				if (new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/classic").exists() == false) {
		        	new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/classic").mkdir();
		        	System.out.println("Created classic pack dir");
		        }
				if (new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/newclassic").exists() == false) {
		        	new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/newclassic").mkdir();
		        	System.out.println("Created newclassic pack dir");
		        }
				//Copies resources over
				URI uri = GUILauncher.class.getResource("/classic").toURI();
		        Path myPath = null;
		        FileSystem fileSystem = null;
		        if (uri.getScheme().equals("jar")) {
			        fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
			        myPath = fileSystem.getPath("/classic");
		        } else {
		            myPath = Paths.get(uri);
		        }
		        Stream<Path> walk = Files.walk(myPath, 1);
		        Iterator<Path> it = walk.iterator();
		        it.next();
		        while (it.hasNext()){
			        try {
			        	Path filePath = it.next();
			        	String idStr = filePath.getFileName().toString();
			        	File newFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/classic/" + idStr);
			        	Files.copy(filePath, new FileOutputStream(newFile));
			        } catch (NoSuchElementException e) {
			        	e.printStackTrace();
			        }
		        }
		        walk.close();
		        if (fileSystem != null) {
		        	fileSystem.close();
		        }
		        
		        

		        URI uri2 = GUILauncher.class.getResource("/newclassic").toURI();
		        Path myPath2 = null;
		        FileSystem fileSystem2 = null;
		        if (uri2.getScheme().equals("jar")) {
			        fileSystem2 = FileSystems.newFileSystem(uri2, Collections.<String, Object>emptyMap());
			        myPath2 = fileSystem2.getPath("/newclassic");
		        } else {
		            myPath2 = Paths.get(uri2);
		        }
		        Stream<Path> walk2 = Files.walk(myPath2, 1);
		        Iterator<Path> it2 = walk2.iterator();
		        it2.next();
		        while (it2.hasNext()){
		        	try {
			        	Path filePath = it2.next();
			        	String idStr = filePath.getFileName().toString();
			        	File newFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/newclassic/" + idStr);
			        	Files.copy(filePath, new FileOutputStream(newFile));
		        	} catch (NoSuchElementException e) {
			        	e.printStackTrace();
			        }
		        }
		        walk2.close();
		        if (fileSystem2 != null) {
		        	fileSystem2.close();
		        }
	}
	
	/**
	 * Launches the GUI, as JavaFX cannot handle the GUI being launched directly.
	 * Also creates default files (packs classic and newclassic).
	 * @param args Passed on to GUI to run.
	 * @throws URISyntaxException Should never be thrown, however the compiler yelled at me if I didn't include this.
	 * @throws IOException Thrown if for some reason it cannot create directories. Should never be thrown.
	 */
	public static void main(String[] args) throws URISyntaxException, IOException {
		
		
		try {
			TRM.makeTestSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		copyFiles();
        GUI.main(args);
    }
}
	
