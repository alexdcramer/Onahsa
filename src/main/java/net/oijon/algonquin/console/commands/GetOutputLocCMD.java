package net.oijon.algonquin.console.commands;

import net.oijon.algonquin.console.Console;
import net.oijon.utils.logger.Log;

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
