package kz.soz.root;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 04.08.2016 14:54
 * Copyright © LLP JazzSoft
 */
public class WordAnalyser {
    private DatabaseAdapter databaseAdapter;

    public WordAnalyser() {
        databaseAdapter = new DatabaseAdapter();
    }

    public String getRoot(String word){
        String root = word;
        while (!isRoot(root)){
            root = removeEnding(root);
        }
        return root;
    }

    public boolean isRoot(String root) {
        return databaseAdapter.getTranslation(root) != null;
    }

    public String removeEnding(String word){
        String result;
        result = removePluralEnding(word);
        if(result == null){
            result = removePersonalEnding(word);
        }
        if(result == null){
            result = removePossessiveEnding(word);
        }
        if(result == null){
            result = removeCaseEnding(word);
        }
        return result;
    }

    private String removePossessiveEnding(String word) {
        String result;
        result = removeEndingByPattern(word, ".*((ыңыз)|(іңіз)|(ымыз)|(іміз))$", 4);
        if(result == null){
            result = removeEndingByPattern(word, ".*((ңыз)|(ңіз)|(мыз)|(міз))$", 3);
        }
        if(result == null){
            result = removeEndingByPattern(word, ".*((ым)|(ім)|(ың)|(ің)|(сы)|(сі))$", 2);
        }
        if(result == null){
            result = removeEndingByPattern(word, ".*((м)|(ң)|(ы)|(і))$", 1);
        }
        return result;
    }

    private String removeCaseEnding(String word) {
        String result;
        result = removeEndingByPattern(word, ".*((ның)|(нің)|(дың)|(дің)|(тың)|(тің)" +
                "|(нда)|(нде)|(дан)|(ден)|(тан)|(тен)|(нан)|(нен)|(мен)|(бен)|(пен))$", 3);
        if(result == null){
            result = removeEndingByPattern(word, ".*((ға)|(ге)|(қа)|(ке)|(на)|(не)|(ды)|(ді)|(ты)|(ті)|(ны)|(ні)" +
                    "|(да)|(де)|(та)|(те))$", 2);
        }
        if(result == null){
            result = removeEndingByPattern(word, ".*((а)|(е)|(н))$", 1);
        }
        return result;
    }

    private String removePersonalEnding(String word) {
        return removeEndingByPattern(word, ".*((мын)|(мін)|(бын)|(бін)|(пын)|(пін)|(сың)|(сыз)|(сің)|(сіз)" +
                "|(мыз)|(міз)|(быз)|(біз)|(пыз)|(піз))$", 3);
    }

    public String removePluralEnding(String word) {
        return removeEndingByPattern(word, ".*((лар)|(лер)|(тар)|(тер)|(дар)|(дер))$", 3);
    }

    public String removeEndingByPattern(String word, String pattern, int length) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(word);
        if(m.matches()){
            return word.substring(0, word.length() - length);
        }
        return null;
    }
}
