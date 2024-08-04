package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;

public class ExitCMD extends Command {

	public ExitCMD(Log log) {
		super(log);
		name = "exit";
		description = "Exits the current session of Onahsa.";
	}

	@Override
	public void run(String[] args) {
		Console.setLoop(false);
	}

}
