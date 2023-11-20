package es.ulpgc.word_searcher.Indexer;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class FileIndexer {

    Gson gson = new Gson();

    public FileIndexer(File file, String datamart) {

        String[] words = Utils.fileCleaner(file);

        for (String word : words) {
            word = word.replace(" ", "");

            if ((word.length() > 3 && !Pattern.matches("^(nul|con|aux|prn).*", word))) {

                String route =  datamart + "/" + word.substring(0,1) + "/" + word.substring(0,2) + "/"
                        + word.substring(0,3) + "/" + word + ".txt"; 

                routeManager(route, file.getName(), word);
            }
        }
    }

    public void fileCreator(String fileName, String route, String word){
        try {
            File wordFile = new File(route);
            wordFile.createNewFile();
            FileWriter fr = new FileWriter(route);

            Word nWord = new Word(word);
            nWord.setReferences(fileName, 1);
            String fWord = gson.toJson(nWord);

            fr.write(fWord);
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void routeManager(String route, String fileName, String word){

        File wordFile = new File(route);

        if (wordFile.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(route));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
                br.close();

                Word cWord = gson.fromJson(sb.toString(), Word.class);

                if(cWord.getReferences().containsKey(fileName)){
                    cWord.setReferences(fileName, cWord.getReferences().get(fileName)+1);
                }
                else{
                    cWord.setReferences(fileName, 1);
                }

                String nWord = gson.toJson(cWord);

                BufferedWriter bw = new BufferedWriter(new FileWriter(route));
                bw.write(nWord);
                bw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File directory = wordFile.getParentFile();

            if (directory.exists()) {
                fileCreator(fileName, route, word);
            } else{
                directory.mkdirs();
                fileCreator(fileName, route, word);
            }
        }
    }
}
