package br.com.capela.app1.model;

import java.util.List;
import java.util.Optional;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class SaveCoinGameInfo {
	@Key("data")
	public List<PriceData> priceData;
	
	public PriceData priceData(){
		PriceData nullPriceData = new PriceData();
		nullPriceData.priceInfo = new PriceInfo();
		nullPriceData.priceInfo.rawCurrentPrice = Float.NaN;
		return Optional.ofNullable(!priceData.isEmpty() && priceData.size() == 1 ? priceData.get(0) : null).orElse(nullPriceData);
	}
	
	public static class PriceData{
		@Key
		public String title;
		@Key
		public String url;
		@Key
		public PriceInfo priceInfo;
	}
	
	public static class PriceInfo{
		@Key
		public Float rawCurrentPrice;
		@Key
		public boolean hasDiscount;
	}
	
	public static class SaveCoinUrl extends GenericUrl {
		public SaveCoinUrl(String encodedUrl) {
			super(encodedUrl);
		}
	}
	
}
