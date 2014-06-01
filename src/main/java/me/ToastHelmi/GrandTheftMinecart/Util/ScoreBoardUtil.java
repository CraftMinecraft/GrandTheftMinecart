package me.ToastHelmi.GrandTheftMinecart.Util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardUtil {

	private static ScoreBoardUtil instance;
	private final ScoreboardManager manager;
	private HashMap<String, Scoreboard> boards = new HashMap<String, Scoreboard>();
	
	private ScoreBoardUtil(){
		instance = this;
		manager = Bukkit.getScoreboardManager();
	}
	
	public static  ScoreBoardUtil getInstance(){
		if(instance == null)
			instance = new ScoreBoardUtil();
		
		return instance;
	}
	
	
	public void setScoreBoard(Player p, DisplaySlot s,String name, int score){
		Scoreboard board;
		Objective o;
		board = manager.getNewScoreboard();
		
		o = board.registerNewObjective(name, "dummy");
		o.setDisplaySlot(s);
		o.setDisplayName(name);
		o.getScore(p).setScore(score);
		p.setScoreboard(board);
		
		
	}
	public void unregisterScoreBoard(Player p,String name){
		p.getScoreboard().getObjective(name).unregister();
	}
}
