package me.ToastHelmi.GrandTheftMinecart.Util;

import java.io.IOException;

import me.ToastHelmi.GrandTheftMinecart.GrandTheftMinecart;
import me.ToastHelmi.GrandTheftMinecart.Police.CrimeManager;

import org.mcstats.Metrics.Graph;
import org.mcstats.Metrics.Plotter;


public class Metrics {
	
	public static void initMetrics(){
		try {
			org.mcstats.Metrics m = new org.mcstats.Metrics(GrandTheftMinecart.getInstance());
			
			Graph AverageWantetLevel = m.createGraph("Average WantetLevel");
			AverageWantetLevel.addPlotter(new Plotter("Average WantetLevel") {
				
				@Override
				public int getValue() {
					// TODO Auto-generated method stub
					return (int) CrimeManager.getInstance().getAverageWantetLevel();
				}
			});
			
			m.start();
		} catch (IOException e) {
			System.err.print("Unable to start Metrics");
		}
	}

}
