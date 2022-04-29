package net.oijon.algonquin.tts;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
		
		//g and É¡ are the same sound, however two different points in unicode. as such, they need to both be in there to prevent disappearing chars
		char[] ipaList = {'p', 'b', 't', 'd', 'ʈ', 'ɖ', 'c', 'ɟ', 'k', 'g', 'ɡ', 'q', 'ɢ', 'ʔ', 'm', 'ɱ', 'n', 'ɳ', 'ɲ', 'ŋ', 'ɴ', 'ʙ', 'r', 'ʀ', 'ⱱ', 'ɾ', 'ɽ', 'ɸ', 'β', 'f', 'v', 'θ', 'ð', 's', 'z', 'ʃ', 'ʒ', 'ʂ', 'ʐ', 'ç', 'ʝ', 'x', 'ɣ', 'χ', 'ʁ', 'ħ', 'ʕ', 'h', 'ɦ', 'ɬ', 'ɮ', 'ʋ', 'ɹ', 'ɻ', 'j', 'ɰ', 'l', 'ɭ', 'ʎ', 'ʟ', 'ʍ', 'w', 'ɥ', 'ʜ', 'ʢ', 'ʡ', 'ɕ', 'ʑ', 'ɺ', 'ɧ', 'i', 'y', 'ɨ', 'ʉ', 'ɯ', 'u', 'ɪ', 'ʏ', 'ʊ', 'e', 'ø', 'ɘ', 'ɵ', 'ɤ', 'o', 'ə', 'ɛ', 'œ', 'ɜ', 'ɞ', 'ʌ', 'ɔ', 'æ', 'ɐ', 'a', 'ɶ', 'ɑ', 'ɒ'};
    	char[] diacriticList = {'̥', '̬', '̹', '̜', '̟', '̠', '̈', '̽', '̩', '̯', '˞', '̤', '̰', '̼', 'ʷ', 'ʲ', 'ˠ', 'ˤ', '̴', '̝', '̞', '̘', '̙', '̪', '̺', '̻', '̃', 'ⁿ', 'ˡ', '̚'};
        String[] fileNames = new String[input.length()];
        
        int inputLength = input.length();
        int currentFileName = 0;
        
        for (int i = 0; i < inputLength; i++) {
        	char c = input.charAt(i);
        	boolean isDiacritic = false;
        	
        	//handles spaces.
        	if (c == ' ') {
        		//if space, set to space.wav
        		fileNames[currentFileName] = "space";
        		currentFileName++;
        	}
        	
        	for (int j = 0; j < diacriticList.length; j++) {
        		if (c == diacriticList[j]) {
        			isDiacritic = true;
        			//shouldnt actually be a problem, but just in case...
        			if (currentFileName != 0) {
            			//if diacritic, add to file name of previous char.
        				currentFileName--;
        				fileNames[currentFileName] += "" + c;
        				currentFileName++;
        			} else {
        				System.err.println("Diacritic attempted to be added to non-existant character! Skipping...");
        				//TODO: check for diacritics that are added to the beginning of a consonant
        			}
        			
        		}
        	}
        	//skips if the character was a diacritic, should speed things up...
        	if (isDiacritic != true) {
	        	for (int k = 0; k < ipaList.length; k++) {
	        		if (c == ipaList[k]) {
	        			//sets file name to character and goes to the next file name
	        			fileNames[currentFileName] = "" + c; //this is stupid but otherwise i cant cast from char to string
	        			currentFileName++;
	        		}
	        	}
        	}
        	
        	//TODO: handle supersegmentals
        	//i am very much hoping that there is some way to simply edit the .wav files in batch
        	// and that i will not have to say É¢Ì°Ìžâ�¿Ê°Ì©Ê·Ê²Ë Ë¤Ë¡
        
        }
		return fileNames;
	}
	public static void createAudio(String[] fileNames) {
		for (int i = 0; i < fileNames.length; i++) {
			try {
			    Clip clip = AudioSystem.getClip();
			    InputStream is = IPA.class.getResourceAsStream("/default/" + fileNames[i] + ".wav");
			    InputStream bis = new BufferedInputStream(is);
			    AudioInputStream ais = AudioSystem.getAudioInputStream(bis);
			    clip.open(ais);
			    clip.start();
			    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
			    {
			    }
			  }
			  catch (UnsupportedAudioFileException e) {
			    throw new IllegalArgumentException("unsupported audio format: '" + fileNames[i] + "'", e);
			  }
			  catch (LineUnavailableException e) {
			    throw new IllegalArgumentException("could not play '" + fileNames[i] + "'", e);
			  }
			  catch (IOException e) {
			    throw new IllegalArgumentException("could not play '" + fileNames[i] + "'", e);
			  }
		}
	}
    public static void main( String[] args )
    {
    	//FIXME: Resource leak!
    	@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
    	String input = null;
    	while (true) {
    		System.out.println("Type some IPA to pronounce!\n");
    		try {
    			input = scanner.nextLine();
    		} catch (NoSuchElementException e) {
    			System.err.println("Hmm... nothing was inputted... Skipping");
    			input = " ";
    		}
    		createAudio(getFileNames(input));
    	}
    }
}
