package kz.soz.tatoeba;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 15.06.2016 16:58
 * Copyright © LLP JazzSoft
 */
public class SentenceTranslation {

    private final Map<String, String> langAndSentences = new HashMap<>();

    public void putSentence(String lang, String sentence){
        langAndSentences.put(lang, sentence);
    }

    public String getSentence(String lang){
        return langAndSentences.get(lang);
    }

    @Override
    public String toString() {
        return "SentenceTranslation{" + langAndSentences + '}';
    }
}
