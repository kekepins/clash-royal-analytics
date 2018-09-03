package cr.extractor;

import java.util.concurrent.Callable;

import cr.model.Battle;
import cr.service.CrServiceException;
import cr.service.QueryBuilder;

public class BattleResultTask implements Callable<Battle[]>{
	
	private final String playerStr; 
	
	public BattleResultTask(String playerStr) {
		this.playerStr = playerStr;
	}

	@Override
	public Battle[] call() throws Exception {
		try {
			Battle[] battles = QueryBuilder.selectPlayerBattles(playerStr ).execute();
			return battles;
		} 
		catch (CrServiceException e) {
			
			e.printStackTrace();
			
			// Error occurs in query
			try {
				System.out.println("#" + Thread.currentThread().getId() +  ": Error sleep a little ...");
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return new Battle[] {};
		}	
	}

}
