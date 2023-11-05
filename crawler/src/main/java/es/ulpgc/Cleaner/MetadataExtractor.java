package es.ulpgc.Cleaner;

import com.google.gson.Gson;
import es.ulpgc.Crawler.FolderCreator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetadataExtractor implements Extractor{

    @Override
    public String extractData(String filePath) {
        String title = null;
        String author = null;
        String language = null;
        String publicationYear = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int metadataFound = 0;

            while ((line = reader.readLine()) != null) {
                if (metadataFound == 4) {
                    break;
                }

                if (line.matches("^(Title|Author|Language):(.*)$")) {

                    Matcher matcher = Pattern.compile("^(Title|Author|Language):(.*)$").matcher(line);
                    if (matcher.find()) {
                        String key = matcher.group(1).trim();
                        String value = matcher.group(2).trim();
                        metadataFound++;

                        if ("Title".equals(key)) {
                            title = value;
                        } else if ("Author".equals(key)) {
                            author = value;
                        } else if ("Language".equals(key)) {
                            language = value;
                        }
                    }
                } else if (line.matches("^(Original publication|Release Date):(.*)$")) {
                    Matcher matcher = Pattern.compile("^(Original publication|Release Date):(.*)$").matcher(line);

                    if (matcher.find()) {
                        String[] parts = matcher.group(2).trim().split("\\s+");

                        if (parts.length > 0) {
                            publicationYear = parts[parts.length - 1].replaceAll("[^0-9]", "");
                            metadataFound++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Book book = new Book(title, publicationYear, author, language);

        Gson gson = new Gson();
        return gson.toJson(book);
    }

    @Override
    public void storeData(String book, String bookFilePath) {
        String id = bookFilePath.substring(bookFilePath.lastIndexOf("\\") + 1, bookFilePath.lastIndexOf("."));
        String filename = FolderCreator.getFilename(id, "metadata");

        try {
            BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filename, true));
            writer.write(book);
            writer.close();
            System.out.println("Metadata stored in the file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


