package net.oijon.algonquin.tts.trm;

public class Tube {

	private int length;
	private int sampleRate;
	private final int speedOfSound = 34300; //in cm/s
	private double loss = 0.97; //3% loss
	private float[][] delayLine;
	
	public Tube (int length) {
		this.length = length;
		// L = c/F, where L is the length (17cm), c is the speed of sound (cm/s), and F is the sampling frequency
		sampleRate = speedOfSound/this.length;
		//Creates the delay array based off 
		delayLine = new float[sampleRate][2];
	}
	
	//create and manage a delay line
	//returns false if there is still room before sound makes its way out
	//returns true if its pushing the sound out to the next tube
	//this is done by checking if the last part of the array for the sample rate is filled.
	public boolean push(float pushAmount) {
		for (int i = 0; i < sampleRate-1; i++) {
			delayLine[i+1][0] = delayLine[i][0];
		}
		delayLine[0][0] = pushAmount;
		delayLine[sampleRate-1][1] = -1*delayLine[sampleRate-1][0]; 
		for (int i = sampleRate-1; i > 0; i--) {
			delayLine[i-1][0] = delayLine[i][0];
		}
		if (Float.isNaN(delayLine[sampleRate-1][0])) {
			return false;
		} else {
			return true;
		}
	}
	
	//convert to 44.1 kHz by adding new data to follow the slope.
	//i could very well be misunderstanding something about this in the paper by Manzara
	//if so, this method will probably be replaced.
	public float[][] standardize() {
		float[][] standard = new float[44100][2];
		int multiple = (int)(44100/sampleRate);
		for (int h = 0; h < 2; h++) {
			for (int i = 0; i < sampleRate; i++) {
				float slope = 1;
				if (i+1 < sampleRate) { 
					slope = (delayLine[i+1][h]-delayLine[i][h])/((i+1)-i);
				} else {
					slope = (delayLine[sampleRate-1][h]-delayLine[i][h])/((sampleRate-1)-i);
				}
				for (int j = 0; j < multiple; j++) {
					//could be optimized more
					if (j == 0) {
						standard[i*multiple][h] = delayLine[i][h];
					} else {
						standard[j][h] = standard[j-1][h]*slope;
					}
				}
			}
		}
		
		return standard;
	}
	
	public float[] out() {
		float[] out = new float[2];
		out[0] = delayLine[sampleRate-1][0];
		out[1] = delayLine[sampleRate-1][1];
		return out;
	}
	
	//debug only, should not be used in production
	public float[][] getDelayLine() {
		return delayLine;
	}
	public int getLength() {
		return length;
	}
	public int getSampleRate() {
		return sampleRate;
	}
	public double getLoss() {
		return loss;
	}
	public void setLoss(double loss) {
		this.loss = loss;
	}
}
