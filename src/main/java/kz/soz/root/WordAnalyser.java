package kz.soz.root;

import org.junit.Test;

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
        System.out.println(databaseAdapter.getTranslation(root));
        return databaseAdapter.getTranslation(root) != null;
    }

    public String removeEnding(String word){
        return "";
    }

    @Test
    public void test(){
        WordAnalyser wordAnalyser = new WordAnalyser();
        wordAnalyser.isRoot("көсем");
    }
}
