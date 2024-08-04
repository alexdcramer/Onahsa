package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;

public class SetPathCMD extends Command {

	public SetPathCMD(Log log) {
		super(log);
		name = "setpath";
		description = "Sets the path for the output file.";
	}

	@Override
	public void run(String[] args) {
		if (args.length < 2) {
			log.err("Invalid amount of parameters for 'setname'. Expected 2, given " + args.length);
		} else {
			Console.setOutputDir(args[1]);
			log.info("Set output file to " + args[1] + "/" + Console.getOutputName() + ".wav");
		}

	}

}
