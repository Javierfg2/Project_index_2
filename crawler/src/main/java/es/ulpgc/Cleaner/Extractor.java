package es.ulpgc.Cleaner;

import java.io.IOException;

public interface Extractor {
    String extractData(String filePath);
    void storeData(String data, String dataFilePath) throws IOException;
}
