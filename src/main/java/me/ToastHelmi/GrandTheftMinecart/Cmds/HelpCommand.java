package me.ToastHelmi.GrandTheftMinecart.Cmds;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand extends BaseCommand{
	
	
	public HelpCommand(){
		this.name = "Help";
		this.discription = "Shows this page";
		this.usage = "/GTM or /GTM Help";
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String label,String[] args) {

		sender.sendMessage(ChatColor.GOLD + "-------HELP-------");
		for(BaseCommand c : CommandManager.registry.values()){
			sender.sendMessage(ChatColor.GOLD +"/GTM "+ChatColor.DARK_GREEN +c.name + " - "+ ChatColor.DARK_GRAY + c.discription);
		}
		
	}

}
