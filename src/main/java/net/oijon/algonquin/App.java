package net.oijon.algonquin;


public class App 
{
	public void parseSound(String input) {
		char[] ipaList = new char[107];
    	char[] diacriticList = new char[48];
        String[] fileNames = new String[input.length()];
        
        int inputLength = input.length();
        int currentFileName = 0;
        
        for (int i = 0; i < inputLength; i++) {
        	char c = input.charAt(i);
        	boolean isDiacritic = false;
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
        }
        
        //TODO: get input, then for each IPA character, load a .wav file for that
        //char, then splice it together into an output
        
        //it will have to separate diacritics though...
        //perhaps have diacritics in it's own array, then scan for them before going
        //to the next sound?
	}
    public static void main( String[] args )
    {
    	        
    }
}
