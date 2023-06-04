package net.oijon.algonquin.console.commands;

import java.util.ArrayList;

import net.oijon.algonquin.console.Console;
import net.oijon.utils.logger.Log;

public class HelpCMD extends Command {

	public HelpCMD(Log log) {
		super(log);
		this.name = "help";
		this.description = "Lists all commands.";
	}

	@Override
	public void run(String[] args) {
		ArrayList<Command> commands = Console.commands();
		String line = "";
		for (int i = 0; i < 15; i++) {
			line += "#";
		}
		int pages = ((commands.size() - 1) / 10) + 1;
		int currentPage = 1;
		
		if (args.length >= 2) {
			try {
				currentPage = Integer.parseInt(args[1]);
				if (currentPage > pages) {
					log.warn("Specified page is greater than amount of pages. Defaulting to " + pages + ".");
					currentPage = pages;
				}
			} catch (NumberFormatException e) {
				log.err("'" + args[1] + "' is not a valid page number. Defaulting to page 1.");
			}
			
		}
		
		int perPage = 10;
		if (perPage > commands.size()) {
			perPage = commands.size();
		} else if ((currentPage * 10) > commands.size()) {
			perPage = commands.size() % 10;
		}
		
		log.info(line + "[Help]" + line);
		for (int i = 0; i < perPage; i++) {
			Command cmd = commands.get(i + (currentPage * 10) - 10);
			log.info(cmd.getName() + " - " + cmd.getDescription());
		}
		log.info(line + "[" + currentPage + "/" + pages + "]" + line);
		log.info("Type \"help {pagenumber}\" to see a specific page.");
	}

}
