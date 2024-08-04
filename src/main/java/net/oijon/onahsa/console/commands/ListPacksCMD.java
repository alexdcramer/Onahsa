package net.oijon.onahsa.console.commands;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Functions;

public class ListPacksCMD extends Command {

	public ListPacksCMD(Log log) {
		super(log);
		name = "listpacks";
		description = "Lists all currently installed sound packs.";
	}

	@Override
	public void run(String[] args) {
		String[] packs = Functions.getPacks();
		log.info("Currently installed packs:");
		for (int i = 0; i < packs.length; i++) {
			log.info(packs[i]);
		}
		if (packs.length == 0) {
			log.warn("No packs found. Have files been properly copied over?");
		}
	}

}
