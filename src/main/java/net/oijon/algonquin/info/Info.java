package net.oijon.algonquin.info;

/**
 * A class to get the version information of the current build
 * 
 * essentially the same as net.oijon.utils.info.Info
 * @author alex
 *
 */
public class Info {

	private static String versionNum = "0.4.2";
	private static String fullVersion = "AlgonquinTTS - v" + versionNum;
	
	/**
	 * Gets the current version of AlgonquinTTS, for example "AlgonquinTTS - v0.3.3"
	 * @return The current version of AlgonquinTTS
	 */
	public static String getVersion() {
		return fullVersion;
	}
	
	/**
	 * Gets the version number of AlgonquinTTS, for example "0.3.3"
	 * @return The current version number of AlgonquinTTS
	 */
	public static String getVersionNum() {
		return versionNum;
	}
	
}
