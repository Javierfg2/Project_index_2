package es.ulpgc.Query;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@RestController
public class BookAPI{

        @GetMapping("/books/{word}")
        public String getBooks(@PathVariable String word) {
            String path = getFilePath(word);
            File file = new File(path);

            if (!file.exists()) {
                return "The word \"" + word + "\" does not appear in any book";
            } else {
                String json = readJsonFromFile(path);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();

                String jword = jsonObject.get("word").getAsString();
                String references = jsonObject.get("references").toString();

                return "The word \"" + jword + "\" appears in the books: " + references;
            }
        }

        public static String getFilePath(String word) {
            String firstLetter = word.substring(0, 1);
            String second = word.substring(0, 2);
            String third = word.substring(0, 3);
            return "Datamart/" + firstLetter + "/" + second + "/" + third + "/" + word + ".txt";
        }

        public static String readJsonFromFile(String filePath) {
            StringBuilder jsonText = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    jsonText.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonText.toString();
        }

        public static void main(String[] args) {
            SpringApplication.run(BookAPI.class, args);
        }
}


