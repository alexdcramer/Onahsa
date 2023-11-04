package net.oijon.algonquin.console.commands;

import net.oijon.algonquin.console.Console;
import net.oijon.olog.Log;

public class ExitCMD extends Command {

	public ExitCMD(Log log) {
		super(log);
		name = "exit";
		description = "Exits the current session of AlgonquinTTS.";
	}

	@Override
	public void run(String[] args) {
		Console.setLoop(false);
	}

}
