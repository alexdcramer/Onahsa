package net.oijon.algonquin.tts.trm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

// Highly experimental.
public class TRM {

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
	
	public static String createTestTube() {
		String exception = "";
		Tube testTube = new Tube(17);
		Voicebox voiceBox = new Voicebox(5);
		
		exception += "\nVoicebox wave: ";
		
		for (int i = 0; i < testTube.getSampleRate()*2; i++) {
			exception += voiceBox.frame() + ", ";
		}
		
		exception += ("outwards elements: " + Arrays.toString(testTube.out())  + "\n");
		exception += "Full array: "  + "\n";
		for (int i = 0; i < testTube.getSampleRate(); i++) {
			exception += testTube.getDelayLine()[i][0] + ", ";
		}
		exception += "\n";
		
		for (int i = 0; i < testTube.getSampleRate(); i++) {
			exception += testTube.getDelayLine()[i][1] + ", ";
		}
		
		return exception;
	}
	
	public static String createTestWave() throws IOException {
		String exception = "";
		
		Voicebox voiceBox = new Voicebox(1);
		
		final double sampleRate = 44100.0;
        final double seconds = 2.0;

        exception += "Sample Rate: " + sampleRate + "\n";
        exception += "Amplitude: 1\n";
        exception += "Seconds: " + seconds + "\n";
        
        
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
        	exception += "Created new directory in " + System.getProperty("user.home") + "/AlgonquinTTS" + "\n";
        }
        
        File out = new File(System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav");
        exception += "Created new file in " + System.getProperty("user.home") + "/AlgonquinTTS/testwave.wav" + "\n";

        final boolean bigEndian = false;
        final boolean signed = true;

        exception += "Big Endian: " + bigEndian + "\n";
        exception += "Signed: " + signed + "\n";
        
        final int bits = 16;
        final int channels = 1;

        exception += "Bits: " + bits + "\n";
        exception += "Channels: " + channels + "\n";
        
        AudioFormat format = new AudioFormat((float)sampleRate, bits, channels, signed, bigEndian);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);
        AudioInputStream audioInputStream = new AudioInputStream(bais, format, buffer.length);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
        audioInputStream.close();
		
		return exception;
	}
	
	public static String createSchwaTubeTest() {
		String exception = "";
		
		int sampleRate = 2018; // L = c/F, where L is the length (17cm), c is the speed of sound (cm/s), and F is the sampling frequency
		float delayLine[][] = new float[sampleRate][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < sampleRate; j++) {
				delayLine[j][i] = (delayLine[j-1][i]);
			}
		}
		
		return exception;
	}
	
}
