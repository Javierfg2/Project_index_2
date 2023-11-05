package es.ulpgc.Cleaner;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Cleaner {

    public static void cleanBooks(File dataLakeFolder) throws IOException {
        if (dataLakeFolder.isDirectory()) {
            File[] files = dataLakeFolder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        cleanBooks(file);
                    } else if (file.isFile() && file.getName().endsWith(".txt")) {
                        String filePath = file.getAbsolutePath();
                        System.out.println("Cleaning file: " + filePath);

                        ContentExtractor cont = new ContentExtractor();
                        MetadataExtractor meta = new MetadataExtractor();

                        String content = cont.extractData(filePath);
                        cont.storeData(content, filePath);
                        
                        String metadata = meta.extractData(filePath);
                        meta.storeData(metadata, filePath);
                    }
                }
            }
        }
    }
}