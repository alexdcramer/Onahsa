package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;

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
