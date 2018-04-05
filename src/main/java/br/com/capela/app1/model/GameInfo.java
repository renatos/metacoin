package br.com.capela.app1.model;

import java.util.Comparator;

public class GameInfo implements Comparable<GameInfo>{
	
	private MetacriticGameInfo metacriticGame;
	private SaveCoinGameInfo saveCoinGame;
	
	public GameInfo(MetacriticGameInfo metacriticGameInfo, SaveCoinGameInfo saveCoinGameInfo){
		this.metacriticGame = metacriticGameInfo;
		this.saveCoinGame = saveCoinGameInfo;
	}
	
	public SaveCoinGameInfo getSaveCoinGame() {
		return saveCoinGame;
	}
	
	public MetacriticGameInfo getMetacriticGame() {
		return metacriticGame;
	}
	
	public Float price(){
		return saveCoinGame.priceData().priceInfo.rawCurrentPrice;
	}
	
	public Integer score(){
		return metacriticGame.getMetacrictScore();
	}
	
	public Integer position(){
		return metacriticGame.getMetacrictPosition();
	}
	
	@Override
	public String toString() {
		return metacriticGame.getMetacrictPosition()+
				" \t"+score()+	
				" \t"+saveCoinGame.priceData().priceInfo.rawCurrentPrice+
				" \tdiscount:"+saveCoinGame.priceData().priceInfo.hasDiscount+
				" \t"+ saveCoinGame.priceData().title;
	}
	
	@Override
	public int compareTo(GameInfo o){
	    return Comparator.comparing(GameInfo::price)
	    		.reversed()
	            .thenComparing(GameInfo::score)
	            .thenComparing(GameInfo::position)
	            .compare(this, o);
	}
}
