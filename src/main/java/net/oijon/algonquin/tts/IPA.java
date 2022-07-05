package net.oijon.algonquin.tts;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author N3ther
 *
 */
public class IPA 
{
	static char[] ipaList = {'p', 'b', 't', 'd', 'ʈ', 'ɖ', 'c', 'ɟ', 'k', 'g', 'ɡ', 'q', 'ɢ', 'ʔ', 'm', 'ɱ', 'n', 'ɳ', 'ɲ', 'ŋ', 'ɴ', 'ʙ', 'r', 'ʀ', 'ⱱ', 'ɾ', 'ɽ', 'ɸ', 'β', 'f', 'v', 'θ', 'ð', 's', 'z', 'ʃ', 'ʒ', 'ʂ', 'ʐ', 'ç', 'ʝ', 'x', 'ɣ', 'χ', 'ʁ', 'ħ', 'ʕ', 'h', 'ɦ', 'ɬ', 'ɮ', 'ʋ', 'ɹ', 'ɻ', 'j', 'ɰ', 'l', 'ɭ', 'ʎ', 'ʟ', 'ʍ', 'w', 'ɥ', 'ʜ', 'ʢ', 'ʡ', 'ɕ', 'ʑ', 'ɺ', 'ɧ', 'i', 'y', 'ɨ', 'ʉ', 'ɯ', 'u', 'ɪ', 'ʏ', 'ʊ', 'e', 'ø', 'ɘ', 'ɵ', 'ɤ', 'o', 'ə', 'ɛ', 'œ', 'ɜ', 'ɞ', 'ʌ', 'ɔ', 'æ', 'ɐ', 'a', 'ɶ', 'ɑ', 'ɒ'};
	static char[] preDiacriticList = {'ᵐ', 'ⁿ', 'ᶯ', 'ᶮ', 'ᵑ'};
	static char[] postDiacriticList = {'̥', 'ː', '̊', '̬', 'ʰ', '̹', '̜', '̟', '̠', '̈', '̽', '̩', '̯', '˞', '̤', '̰', '̼', 'ʷ', 'ʲ', 'ˠ', 'ˤ', '̴', '̝', '̞', '̘', '̙', '̪', '̺', '̻','̃', 'ˡ', '̚', '-'};
	/** 
	 * Creates a String array of all characters inputted, adding diacritics to their respective sound.
	 * @param input The raw IPA input
	 * @return A string array with each file's name
	 */
	public static String[] getFileNames(String input) {
		//i dont like how this is hard-coded in, extIPA exists....
		//TODO: find a way to get this read in from a file
		
		//g and ɡ are the same sound, however two different points in unicode. as such, they need to both be in there to prevent disappearing chars
    	String[] fileNames = new String[input.length()];
        
   
        int inputLength = input.length();
        int currentFileName = 0;
        
        for (int i = 0; i < inputLength; i++) {
        	char c = input.charAt(i);
        	boolean isPreDiacritic = false;
        	boolean isPostDiacritic = false;
        	
        	//handles spaces.
        	if (c == ' ') {
        		//if space, set to space.wav
        		fileNames[currentFileName] = "space";
        		currentFileName++;
        	}
        	
        	
        	for (int j = 0; j < postDiacriticList.length; j++) {
        		if (c == postDiacriticList[j]) {
        			isPostDiacritic = true;
        			//shouldnt actually be a problem, but just in case...]
        			if (currentFileName != 0) {
            			//if diacritic, add to file name of previous char.
        				currentFileName--;
        				fileNames[currentFileName] += Character.toString(c);
        				currentFileName++;
        			} else {
        				System.err.println("Postdiacritic \'" + c + "\' attempted to be added to non-existant character! Skipping...");
        			}
        			
        		}
        	}
        	
        	for (int l = 0; l < preDiacriticList.length; l++) {
        		if (c == preDiacriticList[l]) {
        			System.out.println(preDiacriticList[l]);
        			isPreDiacritic = true;
        			if (currentFileName != fileNames.length) {
        				//if prediacritic, add to file name of next char.
        				fileNames[currentFileName] = Character.toString(c);
	        		} else {
	    				System.err.println("Prediacritic \'" + c + "\' attempted to be added to non-existant character! Skipping...");
	    			}
        		}
        	}
        	
        	//skips if the character was a diacritic, should speed things up...
        	if (isPostDiacritic == false && isPreDiacritic == false) {
	        	for (int k = 0; k < ipaList.length; k++) {
	        		if (c == ipaList[k]) {
	        			//sets file name to character and goes to the next file name
	        			//checks if null because if not, prediacritics would be overwritten.
	        			if (fileNames[currentFileName] == null) {
	        				fileNames[currentFileName] = Character.toString(c);
	        			} else {
	        				fileNames[currentFileName] += Character.toString(c);
	        			}
	        			
	        			currentFileName++;
	        		}
	        	}
        	}
        	
        	//TODO: handle supersegmentals
        	//i am very much hoping that there is some way to simply edit the .wav files in batch
        	// and that i will not have to say É¢Ì°Ìžâ�¿Ê°Ì©Ê·Ê²Ë Ë¤Ë¡
        	
        	// UPDATE: 6/29/2022
        	// Turns out, there is! It's called the Tube Resonance Model (TRM)!
        	// (thank you Leonard Manzara from the University of Calgary!)
        
        }
		return fileNames;
	}
	
	/**
	 * Takes file names, glues them together, creates a file, and plays it.
	 * @param fileNames The processed input, see getFileNames()
	 * @param name The name of the file to output to, without the .wav
	 * @param packName The name of the sound pack to add to
	 * @return Any messages generated while making the audio (exceptions, warnings, etc.)
	 */
	public static String createAudio(String[] fileNames, String name, String packName){
		
		String exception = "";
		long fileLength = 0;
		try {
			URL packURL = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName).toURI().toURL();
		} catch (MalformedURLException e1) {
			URL packURL = IPA.class.getResource("/" + packName);
			exception += "packURL is malformed! Getting resources from jar instead...";
			exception += e1.toString();
			e1.printStackTrace();
		}
		AudioInputStream allStreams[] = new AudioInputStream[fileNames.length];
		try {
			for (int i = 0; i < fileNames.length; i++) {
				URL url;
				File clipFile = new File(System.getProperty("user.home") + "/AlgonquinTTS/packs/" + packName + "/" + fileNames[i] + ".wav");
				try {
					url = clipFile.toURI().toURL();
				} catch (MalformedURLException e1) {
					url = IPA.class.getResource("/" + packName + "/" + fileNames[i] + ".wav");
					exception += "url is malformed! Getting resources from jar instead...";
					exception += e1.toString();
					e1.printStackTrace();
				}
				if (clipFile.exists() == false) {
					if (fileNames[i] != null) {
						boolean foundValid = false;
						for (int j = 0; j < fileNames[i].length(); j++) {
							for (int k = 0; k < ipaList.length; k++) {
								if (fileNames[i].charAt(j) == ipaList[k]) {
									foundValid = true;
									exception += "Invalid sound " + fileNames[i] + " detected! This usually means the sound hasn't been added yet. Reverting to " + fileNames[i].charAt(j) + "\n";
									fileNames[i] = Character.toString(fileNames[i].charAt(j));
								}
							}
						}
						if (foundValid == false) {
							exception += "Invalid sound " + fileNames[i] + " detected! No valid replacement found, skipping...\n";
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
			exception += "Created file " + System.getProperty("user.home") + 
					"/AlgonquinTTS/" + name + ".wav\n";
			Clip clip = AudioSystem.getClip();
		    AudioInputStream ais = AudioSystem.getAudioInputStream(
		    		new File(System.getProperty("user.home") + 
							"/AlgonquinTTS/" + name + ".wav").getAbsoluteFile()
		    		);
		    clip.open(ais);
		    clip.start();
		    fileLength += clip.getMicrosecondLength();
		    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
		    {
		    }
		    ais.close();
		    exception += "Successfully played " + Arrays.toString(fileNames) + "\n";
			
		} catch (Exception e) {
			exception = e.toString();
			e.printStackTrace();
		}
		
		
		return exception;
	}
	
	
	
	public static void recordAudio(Clip clip, File file) {
		
	}
   
}

