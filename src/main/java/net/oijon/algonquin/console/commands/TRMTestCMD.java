package net.oijon.algonquin.console.commands;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.oijon.algonquin.tts.trm.TRM;
import net.oijon.olog.Log;

public class TRMTestCMD extends Command {

	public TRMTestCMD(Log log) {
		super(log);
		name = "trmtest";
		description = "Tests TRM. Currently highly experimental and doesn't work very well...";
	}

	@Override
	public void run(String[] args) {
		//log.info("Generated from a value of 1: " + Float.toString(tube.generate(1)));
		//log.info("Generated from a value of 2: " + Float.toString(tube.generate(2)));
		//log.info("Generated from a value of 1.5: " + Float.toString(tube.generate((float) 1.5)));
		//log.info("Generated from a value of 10: " + Float.toString(tube.generate(10)));
		//log.info("Generated from a value of 30: " + Float.toString(tube.generate(30)));
		log.info("This is currently under development and is highly experimental!");
		log.info("However, below you will find debug information to help more easially develop this!");
		log.info("**NOTICE: In future versions, this will be replaced by Bagpipe**");
		log.info("-----BEGIN TESTTUBE-----");
		TRM.createTestTube();
		log.info("-----END TESTTUBE-----");
		log.info("-----BEGIN RAW OUTPUT-----");
		log.info("Now, a .wav file will be created of a pure vocal output. This should be just a sine wave.");
		try {
			TRM.createTestWave();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.err(e.toString());
		}
		log.info("Now it will play!");
		try {
		    Clip clip = AudioSystem.getClip();
		    AudioInputStream ais = AudioSystem.getAudioInputStream(
		    		new File(System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav").getAbsoluteFile()
		    		);
		    clip.open(ais);
		    clip.start();
		    clip.getMicrosecondLength();
		    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
		    {
		    }
		    clip.drain();
		    clip.stop();
		    clip.flush();
		  }
		  catch (UnsupportedAudioFileException e) {
			log.err("Unsupported audio format: '" + System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav" + "' - " + e.toString());
		  }
		  catch (LineUnavailableException e) {
			log.err("Could not play '" + System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav" + "' - " + e.toString());
		  }
		  catch (IOException e) {
			log.err("Could not play '" + System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav" + "' - " + e.toString());
			e.printStackTrace();
		  }
		log.info("If all went to plan, and you did not see any exceptions, you just heard the beep that will form the voicebox's raw output!");
		log.info("Of course, this won't be what the end product sounds like, the end product will have to go through several tubes.");
		log.info("These tubes represent parts of the mouth and nasal cavity, which should be able to approximate the sound of speaking!");
		log.info("-----END RAW OUTPUT-----");
		log.info("-----BEGIN 17cm TUBE OUTPUT-----");
		log.info("A 17cm tube should allow a sound similar to a schwa!");
		try {
			TRM.createTest17cmWave();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.err(e.toString());
		}
		try {
		    Clip clip = AudioSystem.getClip();
		    AudioInputStream ais = AudioSystem.getAudioInputStream(
		    		new File(System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav").getAbsoluteFile()
		    		);
		    clip.open(ais);
		    clip.start();
		    clip.getMicrosecondLength();
		    while(clip.getMicrosecondLength() != clip.getMicrosecondPosition())
		    {
		    }
		    clip.drain();
		    clip.stop();
		    clip.flush();
		  }
		  catch (UnsupportedAudioFileException e) {
			  log.err("Unsupported audio format: '" + System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav" + "' - " + e.toString());
		  }
		  catch (LineUnavailableException e) {
			  log.err("Could not play '" + System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav" + "' - " + e.toString());
		  }
		  catch (IOException e) {
			  log.err("Could not play '" + System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav" + "' - " + e.toString());
			  e.printStackTrace();
		  }
		log.info("Did the sound that just played sound like a schwa? If not, something isn't working...");
	}

}
