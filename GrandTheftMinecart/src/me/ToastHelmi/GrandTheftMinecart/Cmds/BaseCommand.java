package me.ToastHelmi.GrandTheftMinecart.Cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand {
	
	public String name ="";
	public String discription = "";
	public String usage = "";

	public abstract void onCommand(CommandSender sender, Command cmd, String label, String[] args);
	
	protected boolean checkPermissions(CommandSender sender){
		return true;
	}
	
	
}
