package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;

public class GetOutputLocCMD extends Command {

	public GetOutputLocCMD(Log log) {
		super(log);
		name = "getpath";
		description = "Gets the path of the current output file.";
	}

	@Override
	public void run(String[] args) {
		log.info(Console.getOutputDir() + "/" + Console.getOutputName() + ".wav");
	}

}
