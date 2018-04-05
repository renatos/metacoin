package br.com.capela.app1.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

import br.com.capela.app1.model.GameInfo;
import br.com.capela.app1.model.MetacriticGameInfo;
import br.com.capela.app1.model.SaveCoinGameInfo;
import br.com.capela.app1.model.SaveCoinGameInfo.SaveCoinUrl;

@Service
public class GameInfoService {
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	private static final String URL_METACRITIC = "http://www.metacritic.com/browse/games/score/metascore/all/switch/filtered?sort=desc";
	private static final String URL_SAVECOIN = "https://api.savecoins.me/v1/games?currency=BRL&locale=pt&filter[title]=";

	public void consulta(int limit) throws IOException {
		Document doc = Jsoup.connect(URL_METACRITIC).get();
		Elements allElementGames = doc.getElementsByClass("product_row");

		List<MetacriticGameInfo> allMetacriticGames = new ArrayList<>();
		List<GameInfo> allGames = new ArrayList<>();

		allElementGames.forEach(game -> {
			allMetacriticGames.add(new MetacriticGameInfo(game.getElementsByClass("product_title").get(0).text(),
					game.getElementsByClass("row_num").get(0).text().replace(".", ""),
					game.getElementsByClass("product_score").get(0).text()));
		});

		allMetacriticGames.stream().
			filter(game -> game.getMetacrictPosition() < limit).
			forEach(throwingConsumerWrapper(game -> {
				HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
					@Override
					public void initialize(HttpRequest request) {
						request.setParser(new JsonObjectParser(JSON_FACTORY));
					}
				});
				SaveCoinUrl url = new SaveCoinUrl(URL_SAVECOIN + game.getName());
				HttpRequest request = requestFactory.buildGetRequest(url);
				SaveCoinGameInfo saveCoinGame = request.execute().parseAs(SaveCoinGameInfo.class);
				GameInfo gameInfo = new GameInfo(game, saveCoinGame);
				allGames.add(gameInfo);
			}));
		
		List<GameInfo> allGamesFilter = allGames.stream().filter(game -> !game.price().equals(Float.NaN)).collect(Collectors.toList());;
		
		allGamesFilter.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
	}
	
	static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
		return i -> {
			try {
				throwingConsumer.accept(i);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		};
	}
}
