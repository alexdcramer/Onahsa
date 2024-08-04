package net.oijon.onahsa.console.commands;

import java.util.Properties;

import net.oijon.oling.info.Info;
import net.oijon.olog.Log;

public class DebugInfoCMD extends Command {

	public DebugInfoCMD(Log log) {
		super(log);
		name = "debuginfo";
		description = "Prints out debug info for troubleshooting.";
	}

	@Override
	public void run(String[] args) {
		log.info(Info.getVersion());
		log.info(net.oijon.onahsa.info.Info.getVersion());
		Properties properties = System.getProperties();
		String rawProperties = properties.toString();
		String[] propArray = rawProperties.substring(0, rawProperties.length() - 1).split(", ");
		for (int i = 0; i < propArray.length; i++) {
			log.info(propArray[i]);
		}
	}

}
