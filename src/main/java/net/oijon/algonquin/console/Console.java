package net.oijon.algonquin.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import net.oijon.algonquin.console.commands.Command;
import net.oijon.algonquin.console.commands.CopyFilesCMD;
import net.oijon.algonquin.console.commands.CurrentPackCMD;
import net.oijon.algonquin.console.commands.DebugInfoCMD;
import net.oijon.algonquin.console.commands.ExitCMD;
import net.oijon.algonquin.console.commands.GetOutputLocCMD;
import net.oijon.algonquin.console.commands.HelpCMD;
import net.oijon.algonquin.console.commands.ListPacksCMD;
import net.oijon.algonquin.console.commands.PronounceCMD;
import net.oijon.algonquin.console.commands.SetNameCMD;
import net.oijon.algonquin.console.commands.SetPackCMD;
import net.oijon.algonquin.console.commands.SetPathCMD;
import net.oijon.algonquin.console.commands.TRMTestCMD;
import net.oijon.olog.Log;

public class Console {
	
	private static ArrayList<Command> commands = new ArrayList<Command>();
	private static boolean loop = true;
	private static String selectedPack = "newclassic";
	private static String outputDir = System.getProperty("user.home") + "/AlgonquinTTS";
	private static String outputName = "output";
	private static Log log = new Log(System.getProperty("user.home") + "/AlgonquinTTS");;
	
	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = "[" + dateFormat.format(date) + "] ";
		return time;		
	}
	
	public static void init() {		
		/*
		 * 000000000000000000000000000000
		 * 0    Command Instantiation   0
		 * 000000000000000000000000000000
		 * 0   Ordered Alphabetically   0
		 * 000000000000000000000000000000
		 */
		
		commands.add(new CopyFilesCMD(log));
		commands.add(new CurrentPackCMD(log));
		commands.add(new DebugInfoCMD(log));
		commands.add(new ExitCMD(log));
		commands.add(new GetOutputLocCMD(log));
		commands.add(new HelpCMD(log));
		commands.add(new ListPacksCMD(log));
		commands.add(new PronounceCMD(log));
		commands.add(new SetNameCMD(log));
		commands.add(new SetPackCMD(log));
		//commands.add(new SetPathCMD(log));
		commands.add(new TRMTestCMD(log));
	}
	
	public static void run() {
		init();
		String walls = "";
		for (int i = 0; i < 40; i++) {
			walls += "#";
		}
		log.info(walls);
		
		Scanner userInput = new Scanner(System.in);
		log.info("Welcome to the AlgonquinTTS command line!");
		log.info(net.oijon.algonquin.info.Info.getVersion());
		log.info("Type \"help\" for a list of all commands!");
		log.info(walls);
		
		while(loop) {
			System.out.print(">");
			String command[] = userInput.nextLine().split(" ");
			parse(command);
		}
		userInput.close();
	}
	
	public static void parse(String command) {
		String[] newCommand = command.split(" ");
		parse(newCommand);
	}
	
	public static void parse(String[] command) {
		boolean valid = false;
		for (int i = 0; i < commands.size(); i++) {
			if (command[0].equals(commands.get(i).getName())) {
				valid = true;
				commands.get(i).execute(command);
			}
		}
		
		if (!valid) {
			log.err("'" + command[0] + "' is an invalid command. Type \"help\" for a list of all commands.");
		}
	}
	
	public static ArrayList<Command> commands() {
		return new ArrayList<Command>(commands);
	}
	
	public static void setLoop(boolean loop) {
		Console.loop = loop;
	}
	
	public static String getSelectedPack() {
		return new String(selectedPack);
	}
	
	public static void setSelectedPack(String selectedPack) {
		Console.selectedPack = new String(selectedPack);
	}
	
	public static String getOutputName() {
		return new String(outputName);
	}
	
	public static void setOutputName(String outputName) {
		Console.outputName = new String(outputName);
	}
	
	public static String getOutputDir() {
		return new String(outputDir);
	}
	
	public static void setOutputDir(String outputDir) {
		Console.outputDir = new String(outputDir);
	}
	
	public static Log getLog() {
		return log;
	}
	
	public static void main(String args[]) {
		init();
		run();
	}
	
}
