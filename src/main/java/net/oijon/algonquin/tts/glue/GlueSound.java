package net.oijon.algonquin.tts.glue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import net.oijon.algonquin.console.Console;
import net.oijon.utils.logger.Log;
import net.oijon.utils.parser.Parser;
import net.oijon.utils.parser.data.Multitag;
import net.oijon.utils.parser.data.PhonoSystem;

public class GlueSound {

	private static Log log = Console.getLog();
	
	public static void createAudio(String[] fileNames, String name, String packName){
		
		PhonoSystem ps;
		try {
			ps = loadPack(packName);
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
											foundValid = true;
											log.warn("Invalid sound " + fileNames[i] + " detected! This usually means the sound hasn't been added yet. Reverting to " + fileNames[i].charAt(j));
											fileNames[i] = Character.toString(fileNames[i].charAt(j));
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
			
			pronounce(name);
			
		} catch (Exception e) {
			log.err(e.toString());
			e.printStackTrace();
		}
		
	}
	
	private static PhonoSystem loadPack(String packName) throws Exception {
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
	
	private static void pronounce(String name) {
		
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
            byte[] abData = new byte[BUFFER_SIZE];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioStream.read(abData, 0, abData.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    @SuppressWarnings("unused")
                    int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
                }
            }

            sourceLine.drain();
            sourceLine.close();
        } catch (Exception e){
            e.printStackTrace();
        }
		
	}
	
}
