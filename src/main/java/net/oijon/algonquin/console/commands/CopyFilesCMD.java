package net.oijon.algonquin.console.commands;

import net.oijon.algonquin.console.Functions;
import net.oijon.olog.Log;

public class CopyFilesCMD extends Command {

	public CopyFilesCMD(Log log) {
		super(log);
		name = "copyfiles";
		description = "Copies needed Algonquin files to file system.";
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
