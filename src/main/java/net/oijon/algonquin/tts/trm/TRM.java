package net.oijon.algonquin.tts.trm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import net.oijon.algonquin.console.Console;
import net.oijon.utils.logger.Log;

/**
 * @author N3ther
 * Highly experimental.
 */
public class TRM {

	static Log log = Console.getLog();
	
	/**
	 * Creates and plays a test sound. Will be removed in the future.
	 * @throws IOException Thrown when it can't create the output file for whatever reason.
	 */
	public static void makeTestSound() throws IOException {
		final double sampleRate = 44100.0;
        final double frequency = 440;
        final double frequency2 = 90;
        final double amplitude = 1.0;
        final double seconds = 2.0;
        final double twoPiF = 2 * Math.PI * frequency;
        final double piF = Math.PI * frequency2;

        float[] buffer = new float[(int)(seconds * sampleRate)];

        for (int sample = 0; sample < buffer.length; sample++) {
            double time = sample / sampleRate;
            buffer[sample] = (float)(amplitude * Math.cos(piF * time) * Math.sin(twoPiF * time));
        }

        final byte[] byteBuffer = new byte[buffer.length * 2];

        int bufferIndex = 0;
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int)(buffer[bufferIndex++] * 32767.0);

            byteBuffer[i++] = (byte)x;
            byteBuffer[i] = (byte)(x >>> 8);
        }

        
        if (new File(System.getProperty("user.home") + "/AlgonquinTTS").exists() == false) {
        	new File(System.getProperty("user.home") + "/AlgonquinTTS").mkdir();
        }
        
        File out = new File(System.getProperty("user.home") + "/AlgonquinTTS/out10.wav");

        final boolean bigEndian = false;
        final boolean signed = true;

        final int bits = 16;
        final int channels = 1;

        AudioFormat format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
	}
	
	/**
	 * Creates a tube (See net.oijon.algonquin.tts.trm.Tube) that is 17cm long. Will be removed in the future.
	 * @return Any debug information generated.
	 */
	public static void createTestTube() {
		Tube testTube = new Tube(17);
		Voicebox voiceBox = new Voicebox(5);
		
		log.info("Voicebox wave: ");
		
		String frames = "";
		for (int i = 0; i < testTube.getSampleRate()*2; i++) {
			frames += voiceBox.frame() + ", ";
		}
		log.info(frames);
		
		log.info("outwards elements: " + Arrays.toString(testTube.out()));
		log.info("Full array:");
		String fullArray = "";
		for (int i = 0; i < testTube.getSampleRate(); i++) {
			fullArray += testTube.getDelayLine()[0][i] + ", ";
		}
		log.info(fullArray);
		
		String delayLine = "";
		for (int i = 0; i < testTube.getSampleRate(); i++) {
			delayLine += testTube.getDelayLine()[1][i] + ", ";
		}
		log.info(delayLine);
		
	}
	
	/**
	 * Creates a test wave from a raw Voicebox instance (see net.oijon.algonqion.tts.trm.Voicebox). Will be renamed in the future.
	 * @return Any debug information generated.
	 * @throws IOException Thrown when it can't create the output file for whatever reason.
	 */
	public static void createTestWave() throws IOException {
		
		Voicebox voiceBox = new Voicebox(1);
		
		final double sampleRate = 44100.0;
        final double seconds = 2.0;

        log.info("Sample Rate: " + sampleRate);
        log.info("Amplitude: 1");
        log.info("Seconds: " + seconds);
        
        
        float[] buffer = new float[(int)(seconds * sampleRate)];

        for (int sample = 0; sample < buffer.length; sample++) {
            buffer[sample] = voiceBox.frame();
        }

        final byte[] byteBuffer = new byte[buffer.length * 2];

        int bufferIndex = 0;
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int)(buffer[bufferIndex++] * 32767.0);

            byteBuffer[i++] = (byte)x;
            byteBuffer[i] = (byte)(x >>> 8);
        }

        
        if (new File(System.getProperty("user.home") + "/AlgonquinTTS").exists() == false) {
        	new File(System.getProperty("user.home") + "/AlgonquinTTS").mkdir();
        	log.info("Created new directory in " + System.getProperty("user.home") + "/AlgonquinTTS");
        }
        
        File out = new File(System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav");
        log.info("Created new file in " + System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav");

        final boolean bigEndian = false;
        final boolean signed = true;

        log.info("Big Endian: " + bigEndian);
        log.info("Signed: " + signed);
        
        final int bits = 16;
        final int channels = 1;

        log.info("Bits: " + bits);
        log.info("Channels: " + channels);
        
        AudioFormat format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
		
	}
	/**
	 * Puts a Voicebox through a 17cm tube. Currently not working as expected.
	 * Will be removed in the future.
	 * @return Any debug information generated.
	 * @throws IOException Thrown when it can't create the output file for whatever reason.
	 */
	public static void createTest17cmWave() throws IOException {
		
		Voicebox voiceBox = new Voicebox(1);
		Tube tube = new Tube(17);
		
		final double sampleRate = 44100.0;
        final double seconds = 2.0;

        log.info("Sample Rate: " + sampleRate);
        log.info("Amplitude: 1");
        log.info("Seconds: " + seconds);
        
        
        float[] buffer = new float[(int)(seconds * sampleRate)];

        for (int sample = 0; sample < buffer.length; sample++) {
        	tube.push(voiceBox.frame());
            buffer[sample] = tube.out()[0];
        }

        final byte[] byteBuffer = new byte[buffer.length * 2];

        int bufferIndex = 0;
        for (int i = 0; i < byteBuffer.length; i++) {
            final int x = (int)(buffer[bufferIndex++] * 32767.0);

            byteBuffer[i++] = (byte)x;
            byteBuffer[i] = (byte)(x >>> 8);
        }

        
        if (new File(System.getProperty("user.home") + "/AlgonquinTTS").exists() == false) {
        	new File(System.getProperty("user.home") + "/AlgonquinTTS").mkdir();
        	log.info("Created new directory in " + System.getProperty("user.home") + "/AlgonquinTTS");
        }
        
        File out = new File(System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav");
        log.info("Created new file in " + System.getProperty("user.home") + "/AlgonquinTTS/test17cm.wav");

        final boolean bigEndian = false;
        final boolean signed = true;

        log.info("Big Endian: " + bigEndian);
        log.info("Signed: " + signed);
        
        final int bits = 16;
        final int channels = 1;

        log.info("Bits: " + bits);
        log.info("Channels: " + channels);
        
        log.info("Channels: " + channels);
        
        log.info("Array:");
        log.info(Arrays.toString(tube.getDelayLine()[0]));
        log.info(Arrays.toString(tube.getDelayLine()[1]));
        
        AudioFormat format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
		
	}
	/**
	 * Do not use.
	 * 
	 * Unfinished, worse version of create17cmWave.
	 * @return Currently blank.
	 */
	public static void createSchwaTubeTest() {
		
		int sampleRate = 2018; // L = c/F, where L is the length (17cm), c is the speed of sound (cm/s), and F is the sampling frequency
		float delayLine[][] = new float[sampleRate][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < sampleRate; j++) {
				delayLine[j][i] = (delayLine[j-1][i]);
			}
		}
		
	}
	
}
