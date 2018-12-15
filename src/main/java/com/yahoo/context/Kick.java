package com.yahoo.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yahoo.core.MainProcessHardOff;

public class Kick {
	
	//private static ScheduledExecutorService scheduler;
	private static ExecutorService schedulerHardOff;
	
	public static void main(String[] args) {
		System.out.println("Start Main process");
		
		MainProcessHardOff tx = new MainProcessHardOff();
		
    	//scheduler = Executors.newSingleThreadScheduledExecutor();
//    	schedulerHardOff = Executors.newFixedThreadPool(14);
//    	
//		//scheduler.scheduleAtFixedRate(new MainProcess(), 0, 1, TimeUnit.MINUTES);
//		schedulerHardOff.execute(new MainProcessHardOff());
	}
}
