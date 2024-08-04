package net.oijon.onahsa.tts.trm;

/**
 * @author N3ther
 *
 */
public class Voicebox {

	private float intensity;
	private int frameCount = 0;
	
	/**
	 * Creates a Voicebox with a base intensity.
	 * @param intensity The amount the wave is multiplied by.
	 */
	public Voicebox(float intensity) {
		this.intensity = intensity;
	}
	/**
	 * Gets the next sample of the wave.
	 * @return The y-axis point of the wave.
	 */
	public float frame() {
		float amount = (float) (((Math.sin(frameCount*(float)(1F/8F)))*(Math.sin(frameCount*(float)(1F/16F)))*(Math.sin(frameCount*(float)(1F/32F)))*(Math.sin(frameCount*(float)(1F/64F))))*intensity);
		frameCount++;
		return amount;
	}
	/**
	 * Gets the amount of frames created.
	 * @return Amount of frames created.
	 */
	public int getFrameCount() {
		return frameCount;
	}
	/**
	 * Gets the amount the wave is multiplied by.
	 * @return The wave's intensity.
	 */
	public float getIntensity() {
		return intensity;
	}
	/**
	 * Sets the wave intensity. This will be a controllable thing in the GUI in a future update.
	 * @param intensity The new intensity for the wave.
	 */
	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}
	
}
