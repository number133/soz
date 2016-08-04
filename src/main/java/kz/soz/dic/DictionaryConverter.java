package kz.soz.dic;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 04.08.2016 16:27
 * Copyright © LLP JazzSoft
 */
public class DictionaryConverter {
    private String[] buffer;
    private PrintWriter output;
    private DatabaseWriter databaseWriter;

    public void convert() {
        try {
            // Open the file
            FileInputStream fstream = new FileInputStream("D:\\dicx.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            output = new PrintWriter(new FileWriter("output.txt"));
            databaseWriter = new DatabaseWriter();
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                processLine(strLine);
            }

            //Close the input stream

            br.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processLine(String line) {
        String trimmed = line.trim();
        if(trimmed.length() > 1){
            String[] parts = trimmed.split("\\t+");
//            String[] parts = removeEmpty(rawParts);
            if(parts.length > 1){
                processBuffer();
                buffer = new String[2];
                buffer[0] = parts[0];
                buffer[1] = concatArray(Arrays.copyOfRange(parts, 1, parts.length));
            } else {
                buffer[1] = buffer[1] + " " + concatArray(parts);
            }
        }
    }

    private void processBuffer() {
        if(buffer != null) {
            //output.println("{" + buffer[0] + "|" + buffer[1] + "}");
            databaseWriter.write(buffer[0], buffer[1]);
        }
    }

    private String[] removeEmpty(String[] rawParts) {
        List<String> result = new ArrayList<>();
        for(String part : rawParts){
            if(part.trim().length() > 0) {
                result.add(part);
            }
        }
        String[] resultArr = new String[result.size()];
        resultArr = result.toArray(resultArr);
        return resultArr;
    }

    private String concatArray(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for(String part : strings){
            sb.append(part + " ");
        }
        return sb.toString();
    }

    @Test
    public void run(){
        DictionaryConverter cv = new DictionaryConverter();
        cv.convert();
    }
}
