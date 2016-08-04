package kz.soz.tatoeba;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 17.06.2016 16:22
 * Copyright © LLP JazzSoft
 */
public class DataAdapter {

    public List<SentenceTranslation> fetchFirstTen(String from, String to, String word) {
        List<SentenceTranslation> result = new ArrayList<>();
        String url = "https://tatoeba.org/eng/sentences/search?query=%s&from=%s&to=%s";
        try {
            URL oracle = new URL(String.format(url, word, from, to));
            return request(from, to, url, oracle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SentenceTranslation> fetchRandomTen(String from, String to) {
        int page = new Random().nextInt(100) + 1;
        String url = "https://tatoeba.org/eng/sentences/search/page:%d?query=&from=%s&to=%s";
        try {
            URL oracle = new URL(String.format(url, page, from, to));
            return request(from, to, url, oracle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<SentenceTranslation> request(String from, String to, String urlString, URL url) throws IOException {
        List<SentenceTranslation> result = new ArrayList<>();
        Document doc = Jsoup.parse(url.openStream(), "UTF-8", urlString);

        Elements sentences = doc.select("div.sentences_set");
        sentences.forEach(sentence -> {
            Elements translations = sentence.select("div[lang]");
            SentenceTranslation sentenceTranslation = new SentenceTranslation();
            sentenceTranslation.putSentence(from, translations.get(0).text());
            sentenceTranslation.putSentence(to, translations.get(1).text());
            result.add(sentenceTranslation);
        });
        return result;
    }
}
