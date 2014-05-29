package me.ToastHelmi.GrandTheftMinecart.Cmds.commands;

import me.ToastHelmi.GrandTheftMinecart.Cmds.BaseCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

public class KillAllCommand extends BaseCommand {

	public KillAllCommand() {
		this.name = "KillAll";
		this.discription = "Kill every police";
	}
	@Override
	protected boolean checkPermissions(CommandSender sender) {
		return (sender.hasPermission("gtm.killall"));
	};

	@Override
	public void onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!checkPermissions(sender)) {
			sender.sendMessage(ChatColor.RED+ "You are not alowed to use this command!");
			return;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED+ "This command is only for Players!");
			return;
		}

		final Player p = (Player) sender;
		int i = 0;
		for (Entity e : p.getWorld().getEntities()) {
				if(e instanceof Zombie || e instanceof Skeleton){
					((Monster)e).getEquipment().clear();
					((Monster)e).damage(Double.MAX_VALUE);
					i++;
				}
				
		}
		sender.sendMessage("[GTM] "+ i + "Policemembers were killed!");
	}

}
