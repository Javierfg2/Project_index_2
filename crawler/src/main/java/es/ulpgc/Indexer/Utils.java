package es.ulpgc.Indexer;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

public class Utils {

    public static String eliminateMetadata(File book){
        try {
            boolean foundFirstOccurrence = false;
            List<String> contentWithoutMetadata = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new FileReader(book))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("*** START OF THE PROJECT") && !foundFirstOccurrence) {
                        foundFirstOccurrence = true;
                    } else if (foundFirstOccurrence) {
                        if (line.contains("*** END OF THE PROJECT")) {
                            break;
                        }
                        contentWithoutMetadata.add(line);
                    }
                }
            }

            StringBuilder result = new StringBuilder();
            for (String contentLine : contentWithoutMetadata) {
                result.append(contentLine).append("\n");
            }

            return result.toString();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static String normalize(String book){
        book = book.toLowerCase();
        book = book.replaceAll("[^a-zA-Z]+", " ");
        return book;
    }

    public static String[] fileCleaner(File file){
        String content = eliminateMetadata(file);
        String clean = normalize(content);
        return clean.split(" ");
    }
}
