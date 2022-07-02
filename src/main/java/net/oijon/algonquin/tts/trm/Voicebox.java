package net.oijon.algonquin.tts.trm;

public class Voicebox {

	private float intensity;
	private int frameCount = 0;
	
	public Voicebox(float intensity) {
		this.intensity = intensity;
	}
	public float frame() {
		float amount = (float) (((Math.sin(frameCount*(float)(1F/8F)))*(Math.sin(frameCount*(float)(1F/16F)))*(Math.sin(frameCount*(float)(1F/32F)))*(Math.sin(frameCount*(float)(1F/64F))))*intensity);
		frameCount++;
		return amount;
	}
	public int getFrameCount() {
		return frameCount;
	}
	public float getIntensity() {
		return intensity;
	}
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
}
