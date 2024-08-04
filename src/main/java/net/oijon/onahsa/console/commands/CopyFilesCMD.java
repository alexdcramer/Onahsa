package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Functions;

public class CopyFilesCMD extends Command {

	public CopyFilesCMD(Log log) {
		super(log);
		name = "copyfiles";
		description = "Copies needed Onahsa files to file system.";
	}

	@Override
	public void run(String[] args) {
		try {
			long time1 = System.nanoTime();
			log.info("Copying files...");
			Functions.copyFiles(); 
			long time2 = System.nanoTime();
			double ms = (time2 - time1) / 1000000D;
			log.info("Files successfully copied! (elapsed time: " + String.format("%.3f", ms) + " milliseconds)");
			
		} catch (Exception e) {
			log.err("Files could not be copied, " + e.toString());
			e.printStackTrace();
		}
	}
}
