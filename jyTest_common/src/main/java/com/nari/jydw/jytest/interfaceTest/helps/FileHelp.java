package com.nari.jydw.jytest.interfaceTest.helps;

import java.io.*;

public class FileHelp {
    public static void writeFile (String filePath,String content) throws IOException {
        File file = new File(filePath);
        if(file.isFile())
            file.delete();


        file.createNewFile();
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(content);
        output.flush();
        output.close();

    }

    public static String readFileAsString (String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }
}
