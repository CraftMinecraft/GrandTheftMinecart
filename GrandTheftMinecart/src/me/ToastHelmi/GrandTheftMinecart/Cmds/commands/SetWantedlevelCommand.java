package me.ToastHelmi.GrandTheftMinecart.Cmds.commands;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.Cmds.BaseCommand;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWantedlevelCommand  extends BaseCommand{
	
	public SetWantedlevelCommand(){
		this.name = "SWL";
		this.discription = "Set the Wantedlevel for Player";
	}
	
	@Override
	protected boolean checkPermissions(CommandSender sender) {
		return sender.hasPermission("gtm.swl");
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		if(!checkPermissions(sender)){
			sender.sendMessage(ChatColor.RED +"You are not alowed to use this command!");
			return;
		}
		if(args.length  != 2){
			sender.sendMessage(ChatColor.RED+"/gtm swl <player> <level 0-5>");
			
			return;
		}
		final Player p  = Bukkit.getServer().getPlayer(GrandTheftMinecart.getInstance().getUUIDFromName(args[0]));
		if(p.isOnline()){
			try{
				int i = Integer.valueOf(args[1]);
				if (i < 0 || i > 5){
					sender.sendMessage(ChatColor.RED+ "Choose a Number from 0 to 5");
					return;
				}
				
				CrimeManager.getInstance().setWantedLevel(p,i);
				sender.sendMessage(ChatColor.GREEN+args[0]+ "Wantetlevel is set to "+ args[1]);
			}
			catch(NumberFormatException e){
				sender.sendMessage(args[1] + ChatColor.RED +"is not a number!");
				sender.sendMessage(ChatColor.RED+ "Choose a Number from 0 to 5");
				return;
			}
		}
	}

}
