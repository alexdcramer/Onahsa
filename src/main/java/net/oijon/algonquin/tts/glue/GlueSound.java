package net.oijon.algonquin.tts.glue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import net.oijon.algonquin.console.Console;
import net.oijon.utils.logger.Log;
import net.oijon.utils.parser.Parser;
import net.oijon.utils.parser.data.PhonoSystem;

public class GlueSound {

	private static Log log = Console.getLog();
	
	private String packName = "";
	
	public GlueSound(String packName) {
		this.packName = packName;
	}
	
	private String[] getFileNames(String input) {
		//TODO: find a way to get this read in from a file
		
		//g and ɡ are the same sound, however two different points in unicode. as such, they need to both be in there to prevent disappearing chars
    	ArrayList<String> fileNames = new ArrayList<String>();
    	PhonoSystem ps;
        try {
			ps = loadPack();
			log.info("Loaded phono system " + ps.getName());
			log.debug(ps.toString());
		} catch (Exception e) {
			log.warn("Unable to load pack! Defaulting to IPA...");
			ps = PhonoSystem.IPA;
		}
        
        for (int i = 0; i < input.length(); i++) {
        	boolean isDiacritic = false;
        	for (int j = 0; j < ps.getDiacritics().size(); j++) {
        		if (Character.toString(input.charAt(i)).equals(ps.getDiacritics().get(j))) {
        			//TODO: handle prediacritics
        			isDiacritic = true;
        			fileNames.set(fileNames.size() - 1, fileNames.get(fileNames.size() - 1) + Character.toString(input.charAt(i)));
        		}
        	}
        	if (input.charAt(i) == ' ') {
        		
        	} else if (!isDiacritic) {
        		if (ps.isIn(Character.toString(input.charAt(i)))) {
        			fileNames.add(Character.toString(input.charAt(i)));
        		}
        	}
        }
        
        String[] fileNamesArray = new String[fileNames.size()];
        for (int i = 0; i < fileNames.size(); i++) {
        	fileNamesArray[i] = fileNames.get(i);
        }
        
        log.debug(Arrays.toString(fileNamesArray));
		return fileNamesArray;
	}
	
	public void createAudio(String input, String name){
		
		String[] fileNames = getFileNames(input);
		
		PhonoSystem ps;
		try {
			ps = loadPack();
		} catch (Exception e1) {
			log.warn("Given pack is invalid! Reverting to IPA... " + e1.toString());
			e1.printStackTrace();
			ps = PhonoSystem.IPA;
		}
		AudioInputStream allStreams[] = new AudioInputStream[fileNames.length];
		try {
			for (int i = 0; i < fileNames.length; i++) {
				URL url;
				File clipFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName + "/" + fileNames[i] + ".wav");
				try {
					url = clipFile.toURI().toURL();
					log.debug("URL found for clip: " + url);
				} catch (MalformedURLException e1) {
					url = GlueSound.class.getResource("/" + packName + "/" + fileNames[i] + ".wav");
					log.warn("url is malformed! Attempting to get resources from jar instead... " + e1.toString());
					e1.printStackTrace();
				}
				if (clipFile.exists() == false) {
					if (fileNames[i] != null) {
						boolean foundValid = false;
						for (int j = 0; j < fileNames[i].length(); j++) {
							for (int k = 0; k < ps.getTables().size(); k++) {
								for (int l = 0; l < ps.getTables().get(k).size(); l++) {
									for (int m = 0; m < ps.getTables().get(k).getRow(l).size(); m++) {
										if (Character.toString(fileNames[i].charAt(j)).equals(ps.getTables().get(k).getRow(l).getSound(m))) {
											File newClipFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName + "/" + Character.toString(fileNames[i].charAt(j)) + ".wav"); 
											if (newClipFile.exists()) {
												foundValid = true;
												log.warn("Invalid sound " + fileNames[i] + " detected! This usually means the sound hasn't been added yet. Reverting to " + fileNames[i].charAt(j));
												fileNames[i] = Character.toString(fileNames[i].charAt(j));
											}
										}
									}
								}
							}
						}
						if (foundValid == false) {
							log.err("Invalid sound " + fileNames[i] + " detected! No valid replacement found, skipping...");
							fileNames[i] = "space";
						}
					}
					else {
						fileNames[i] = "space";
					}
					
				}
				AudioInputStream ais = AudioSystem.getAudioInputStream(new File(
						System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName + "/" + fileNames[i] + ".wav").getAbsoluteFile());
				allStreams[i] = ais;
			}
			for (int i = 1; i < allStreams.length; i++) {
				AudioInputStream temp = new AudioInputStream(
						new SequenceInputStream(allStreams[0], allStreams[i]),
							allStreams[0].getFormat(),
							allStreams[0].getFrameLength() + allStreams[1].getFrameLength());
				allStreams[0] = temp;
			}
				
			AudioSystem.write(allStreams[0], AudioFileFormat.Type.WAVE, new File(System.getProperty("user.home") + 
					"/AlgonquinTTS/" + name + ".wav"));
			log.info("Created file " + System.getProperty("user.home") + 
					"/AlgonquinTTS/" + name + ".wav");
			
			this.pronounce(name);
			
		} catch (Exception e) {
			log.err(e.toString());
			e.printStackTrace();
		}
		
	}
	
	private PhonoSystem loadPack() throws Exception {
		File packFolder = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName);
		File[] files = packFolder.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(".phosys");
		    }
		});
		
		File packFile;
		if (files.length > 0) {
			packFile = files[0];
		} else {
			throw new FileNotFoundException();
		}
		
		log.debug("Using pack at " + packFolder.toURI().toURL());
		Parser parser = new Parser(packFile);
		PhonoSystem ps = parser.parsePhonoSys();
		return ps;
	}
	
	private void pronounce(String name) {
		
		final int BUFFER_SIZE = 128000;
		AudioInputStream audioStream;
		SourceDataLine sourceLine;
		
		File soundFile = new File(System.getProperty("user.home") + 
				"/AlgonquinTTS/" + name + ".wav");
		try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat audioFormat = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
            
            sourceLine.start();

            int nBytesRead = 0;
            // this one in particular likes to cause memory leaks
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    sourceLine.write(abData, 0, nBytesRead);
                }
            }

            // one of these should help fix the memory leak
            sourceLine.drain();
            sourceLine.close();
            audioStream.close();
            abData = null;
            System.gc();
        } catch (Exception e){
            e.printStackTrace();
        }
		
	}
	
}
