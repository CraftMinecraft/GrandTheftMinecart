package me.ToastHelmi.GrandTheftMinecart.Cmds.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.ToastHelmi.GrandTheftMinecart.Cmds.BaseCommand;
import me.ToastHelmi.GrandTheftMinecart.Util.Updater.UpdateListener;

public class DisableUpdateInfoCommand  extends BaseCommand{

	public UpdateListener listener;
	
	public DisableUpdateInfoCommand(UpdateListener s){
		listener = s;
		this.name = "ToggleUpdateInfo";
		this.discription = "Toggles the UpdateInfo for all players with Permmissions";
	}
	@Override
	protected boolean checkPermissions(CommandSender sender) {
		
		return sender.hasPermission("gtm.UpdateInfo"); 
	}
	
	@Override
	public void onCommand(CommandSender sender, Command cmd, String label,String[] args) {
		if(checkPermissions(sender)){
			listener.toggleInfo();
			sender.sendMessage("UpdateInfo is "+(listener.isInfoEnabeld()? "enabeld":"disabeld")+".");
		}
	}

}
