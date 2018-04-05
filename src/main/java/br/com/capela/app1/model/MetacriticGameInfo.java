package br.com.capela.app1.model;

public class MetacriticGameInfo {

	private String name;
	private Integer metacrictPosition;
	private Integer metacrictScore;
	
	public MetacriticGameInfo(String name, String _metacrictPosition, String _metacrictScore) {
		this.name = name;
		try {
			this.metacrictPosition = Integer.valueOf(_metacrictPosition);
		}catch(Exception e) {
			this.metacrictPosition = 0;
		}
		try {
			this.metacrictScore = Integer.valueOf(_metacrictScore);
		}catch(Exception e) {
			this.metacrictScore =  0;
		}
		
	}

	public Integer getMetacrictPosition() {
		return metacrictPosition;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getMetacrictScore() {
		return metacrictScore;
	}
}
