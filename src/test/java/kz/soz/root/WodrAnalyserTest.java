package kz.soz.root;

import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 05.08.2016 14:02
 * Copyright © LLP JazzSoft
 */
public class WodrAnalyserTest {
    WordAnalyser wordAnalyser = new WordAnalyser();
    TestReader reader = new TestReader();

    public void runTest(String filename){
        Map<String, String> data = reader.read(filename);
        for (String key : data.keySet()) {
            try {
                assertEquals(key, wordAnalyser.getRoot(data.get(key)));
            } catch (Exception ex) {
                System.out.println("Error: " + key + ":" + data.get(key));
            }
        }
    }

    @Test
    public void testPluralEndings(){
        runTest("plural_ending.txt");
    }

    @Test
    public void testPersonalEndings(){
        runTest("personal_ending.txt");
    }

    @Test
    public void testPossessiveEndings(){
        runTest("possessive_ending.txt");
    }

    @Test
    public void testCaseEndings(){
        runTest("case_ending.txt");
    }
}
