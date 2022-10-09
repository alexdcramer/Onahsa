package net.oijon.algonquin.console;

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
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import net.oijon.algonquin.gui.Launcher;

// Meant to keep GUI and console actions the same
public class Functions {

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

	public static String[] getPacks() {
		File packsDirFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/");
		String[] packnames = packsDirFile.list();
		return packnames;
	}
}