package net.oijon.algonquin.tts;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class IPA 
{
	public static String[] getFileNames(String input) {
		//i dont like how this is hard-coded in, extIPA exists....
		//TODO: find a way to get this read in from a file
		
		//g and ɡ are the same sound, however two different points in unicode. as such, they need to both be in there to prevent disappearing chars
		char[] ipaList = {'p', 'b', 't', 'd', 'ʈ', 'ɖ', 'c', 'ɟ', 'k', 'g', 'ɡ', 'q', 'ɢ', 'ʔ', 'm', 'ɱ', 'n', 'ɳ', 'ɲ', 'ŋ', 'ɴ', 'ʙ', 'r', 'ʀ', 'ⱱ', 'ɾ', 'ɽ', 'ɸ', 'β', 'f', 'v', 'θ', 'ð', 's', 'z', 'ʃ', 'ʒ', 'ʂ', 'ʐ', 'ç', 'ʝ', 'x', 'ɣ', 'χ', 'ʁ', 'ħ', 'ʕ', 'h', 'ɦ', 'ɬ', 'ɮ', 'ʋ', 'ɹ', 'ɻ', 'j', 'ɰ', 'l', 'ɭ', 'ʎ', 'ʟ', 'ʍ', 'w', 'ɥ', 'ʜ', 'ʢ', 'ʡ', 'ɕ', 'ʑ', 'ɺ', 'ɧ', 'i', 'y', 'ɨ', 'ʉ', 'ɯ', 'u', 'ɪ', 'ʏ', 'ʊ', 'e', 'ø', 'ɘ', 'ɵ', 'ɤ', 'o', 'ə', 'ɛ', 'œ', 'ɜ', 'ɞ', 'ʌ', 'ɔ', 'æ', 'ɐ', 'a', 'ɶ', 'ɑ', 'ɒ'};
    	//char[] preDiacriticList = {'ᵐ', 'ⁿ', 'ᶯ', 'ᶮ', 'ᵑ'};
		char[] postDiacriticList = {'̥', '̊', '̬', 'ʰ', '̹', '̜', '̟', '̠', '̈', '̽', '̩', '̯', '˞', '̤', '̰', '̼', 'ʷ', 'ʲ', 'ˠ', 'ˤ', '̴', '̝', '̞', '̘', '̙', '̪', '̺', '̻','̃', 'ˡ', '̚', '-'};
    	String[] fileNames = new String[input.length()];
        
        int inputLength = input.length();
        int currentFileName = 0;
        
        for (int i = 0; i < inputLength; i++) {
        	char c = input.charAt(i);
        	//boolean isPreDiacritic = false;
        	boolean isPostDiacritic = false;
        	
        	//handles spaces.
        	if (c == ' ') {
        		//if space, set to space.wav
        		fileNames[currentFileName] = "space";
        		currentFileName++;
        	}
        	/*
        	for (int l = 0; l < preDiacriticList.length; l++) {
        		if (c == preDiacriticList[l]) {
        			isPreDiacritic = true;
        			if (currentFileName != fileNames.length) {
        				currentFileName++;
        				fileNames[currentFileName] += "" + c;
        				currentFileName--;
        			}
        		} else {
    				System.err.println("Prediacritic attempted to be added to non-existant character! Skipping...");
    			}
        	}
        	 */
        	for (int j = 0; j < postDiacriticList.length; j++) {
        		if (c == postDiacriticList[j]) {
        			isPostDiacritic = true;
        			//shouldnt actually be a problem, but just in case...]
        			if (currentFileName != 0) {
            			//if diacritic, add to file name of previous char.
        				currentFileName--;
        				fileNames[currentFileName] += "" + c;
        				currentFileName++;
        			} else {
        				System.err.println("Postdiacritic attempted to be added to non-existant character! Skipping...");
        			}
        			
        		}
        	}
        	//skips if the character was a diacritic, should speed things up...
        	if (isPostDiacritic != true) {
	        	for (int k = 0; k < ipaList.length; k++) {
	        		if (c == ipaList[k]) {
	        			//sets file name to character and goes to the next file name
	        			fileNames[currentFileName] = Character.toString(c);
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
	public static String createAudio(String[] fileNames) {
		String exception = "Successfully played " + Arrays.toString(fileNames);
		long fileLength = 0;
		for (int i = 0; i < fileNames.length; i++) {
			try {
			    Clip clip = AudioSystem.getClip();
			    InputStream is = IPA.class.getResourceAsStream("/classic/" + fileNames[i] + ".wav");
			    InputStream bis = new BufferedInputStream(is);
			    AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			    clip.open(ais);
			    clip.start();
			    fileLength += clip.getMicrosecondLength();
			    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
			    {
			    }
			  }
			  catch (UnsupportedAudioFileException e) {
				exception = "unsupported audio format: '" + fileNames[i] + "' - " + e.toString() + "\n";
				exception += "File names: " + Arrays.toString(fileNames) + "\n";
				return exception;
			  }
			  catch (LineUnavailableException e) {
				exception = "could not play '" + fileNames[i] + "' - " + e.toString() + "\n";
				exception += "File names: " + Arrays.toString(fileNames) + "\n";
				return exception;
			  }
			  catch (IOException e) {
				exception = "could not play '" + fileNames[i] + "' - " + e.toString() + "\n";
				exception += "File names: " + Arrays.toString(fileNames) + "\n";
				return exception;
			  }
			
		}
		double fileLengthInSeconds = (fileLength/(10000L))/100D;
		exception += "\nFile length: " + fileLengthInSeconds + " seconds, " + fileLength + " microseconds";
		return exception;
	}
	
	public static void recordAudio(Clip clip, File file) {
		
	}
   
}

