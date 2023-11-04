package net.oijon.algonquin.console.commands;

import net.oijon.algonquin.console.Console;
import net.oijon.olog.Log;

public class CurrentPackCMD extends Command {

	public CurrentPackCMD(Log log) {
		super(log);
		name = "currentpack";
		description = "Gets the sound pack currently being used.";
	}

	@Override
	public void run(String[] args) {
		log.info(Console.getSelectedPack());
	}

}
