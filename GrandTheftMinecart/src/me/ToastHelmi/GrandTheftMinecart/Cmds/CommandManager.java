package me.ToastHelmi.GrandTheftMinecart.Cmds;

import java.util.Arrays;
import java.util.HashMap;

import me.ToastHelmi.GrandTheftMinecart.Cmds.commands.KillAllCommand;
import me.ToastHelmi.GrandTheftMinecart.Cmds.commands.SetWantedlevelCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
	static HashMap<String,BaseCommand> registry = new HashMap<String,BaseCommand>();

	static{
		registry.put("help", new HelpCommand());
		registry.put("killall", new KillAllCommand());
		registry.put("swl", new SetWantedlevelCommand());
	}
	
	public void registrerCommand(String name, BaseCommand c){
		registry.put(name, c);
	}
	
	public boolean exists(String name) {
		return registry.containsKey(name);
	}

	public BaseCommand getExecutor(String name) {
		return registry.get(name);
	}

	
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		// help command
		if (args.length == 0 || args[0].equals("help")) {
			getExecutor("help").onCommand(sender, cmd, label, args);
			return true;
		}
		// other commands
		if (exists(args[0])) {
			getExecutor(args[0]).onCommand(sender, cmd, label, Arrays.copyOfRange(args, 1, args.length));
			return true;
		} else {
			sender.sendMessage(ChatColor.RED
					+ "This command does not exist! Try /GTM help to see a list of commands!");
		}
		return false;
	}

}
