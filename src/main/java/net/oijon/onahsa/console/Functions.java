package net.oijon.onahsa.console;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import net.oijon.onahsa.gui.Launcher;
import net.oijon.onahsa.tts.glue.GlueSound;

// Meant to keep GUI and console actions the same
// TODO: get rid of this class and move all actions to the various commands, as the GUI is now
// just using the commands :)
public class Functions {

	public static void checkLegacyFiles() {
		File legacyDir = new File(System.getProperty("user.home") + "/AlgonquinTTS");
		File newDir = new File(System.getProperty("user.home") + "/Onahsa");
		if (legacyDir.exists()) {
			System.out.println("Copying old files over to new directory!");
			legacyDir.renameTo(newDir);
		}
		if (legacyDir.exists() | !newDir.exists()) {
			System.out.println("Copy over did not work!");
		}
	}
	
	public static void copyFiles() throws URISyntaxException, IOException  {
		//Create Onahsa directory
		//Uses a lot of ifs, but should be ok...
		if (new File(System.getProperty("user.home") + "/Onahsa").exists() == false) {
        	new File(System.getProperty("user.home") + "/Onahsa").mkdir();
        	System.out.println("Created Onahsa dir");
        }
		if (new File(System.getProperty("user.home") + "/Onahsa/packs").exists() == false) {
        	new File(System.getProperty("user.home") + "/Onahsa/packs").mkdir();
        	System.out.println("Created packs dir");
        }
		if (new File(System.getProperty("user.home") + "/Onahsa/packs/classic").exists() == false) {
        	new File(System.getProperty("user.home") + "/Onahsa/packs/classic").mkdir();
        	System.out.println("Created classic pack dir");
        }
		if (new File(System.getProperty("user.home") + "/Onahsa/packs/newclassic").exists() == false) {
        	new File(System.getProperty("user.home") + "/Onahsa/packs/newclassic").mkdir();
        	System.out.println("Created newclassic pack dir");
        }
		//Copies resources over
		URI uri = Launcher.class.getResource("/classic").toURI();
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
	        	File newFile = new File(System.getProperty("user.home") + "/Onahsa/packs/classic/" + idStr);
	        	Files.copy(filePath, new FileOutputStream(newFile));
	        } catch (NoSuchElementException e) {
	        	e.printStackTrace();
	        }
        }
        walk.close();
        if (fileSystem != null) {
        	fileSystem.close();
        }
        
        

        URI uri2 = Launcher.class.getResource("/newclassic").toURI();
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
	        	File newFile = new File(System.getProperty("user.home") + "/Onahsa/packs/newclassic/" + idStr);
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

	public static String[] getPacks() {
		File packsDirFile = new File(System.getProperty("user.home") + "/Onahsa/packs/");
		String[] packnames = packsDirFile.list();
		return packnames;
	}
	
	public static void pronounce(String packname, String input, String outputFile) {
		GlueSound gs = new GlueSound(packname);
		gs.createAudio(input, outputFile);
		System.gc();
	}
	
	public static String selectPack(String packname) {
		String[] packnames = getPacks();
		if (Arrays.stream(packnames).anyMatch(packname::equals)) {
			return packname;
		}
		return "newclassic";
	}
}
