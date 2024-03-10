package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class JokesStorage {
    HttpClient httpClient;
    HttpRequest httpRequest;

    public JokesStorage() {
        httpClient = HttpClient.newHttpClient();

        httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://xn--80abh7bk0c.xn--p1ai/random"))
                .header("User-Agent", "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                .build();
    }

    public String getRandomJoke() {
        try {
            HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String bodyAsString = (String) httpResponse.body();

            Document document = Jsoup.parse(bodyAsString);
            Element divQuoteBody = document.select("div.quote__body").first();
            String plainHtml = divQuoteBody.html();

            plainHtml = plainHtml
                    .replace("<br>", "\n")
                    .replace("&quot;", "\"")
                    .replace("&lt;", "<")
                    .replace("&gt;", ">");

            return plainHtml;
        }catch (Exception e){
            return "Ошибка. Не удалось загрузить шутку";
        }
    }
}
