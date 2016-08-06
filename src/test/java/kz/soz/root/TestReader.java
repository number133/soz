package kz.soz.root;

import kz.soz.dic.DatabaseWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 05.08.2016 13:21
 * Copyright © LLP JazzSoft
 */
public class TestReader {

    public Map<String, String> read(String filename) {
        Map<String, String> result = new HashMap<>();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());
            FileInputStream fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            while ((strLine = br.readLine()) != null) {
                strLine = strLine.trim();
                if(!strLine.isEmpty()){
                    String[] parts = strLine.split(":");
                    result.put(parts[0], parts[1]);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
