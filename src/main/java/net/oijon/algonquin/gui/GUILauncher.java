package net.oijon.algonquin.gui;

import java.io.IOException;

import net.oijon.algonquin.tts.trm.TRM;

public class GUILauncher {

	public static void main(String[] args) {
		try {
			TRM.makeTestSound();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        GUI.main(args);
    }
	
}
