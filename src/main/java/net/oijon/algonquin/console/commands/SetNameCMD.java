package net.oijon.algonquin.console.commands;

import net.oijon.algonquin.console.Console;
import net.oijon.olog.Log;

public class SetNameCMD extends Command {

	public SetNameCMD(Log log) {
		super(log);
		name = "setname";
		description = "Sets the name of the output file.";
	}

	@Override
	public void run(String[] args) {
		if (args.length < 2) {
			log.err("Invalid amount of parameters for 'setname'. Expected 2, given " + args.length);
		} else {
			Console.setOutputName(args[1]);
			log.info("Set name to " + args[1] + ".wav");
		}
	}

}
