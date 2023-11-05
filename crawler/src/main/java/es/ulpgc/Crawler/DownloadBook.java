package es.ulpgc.Crawler;

import es.ulpgc.Crawler.FolderCreator;

import java.io.*;
import java.net.URL;

public class DownloadBook {
    public static void downloadTextFile(String txtFileUrl, String id) {
        try {
            URL url = new URL(txtFileUrl);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;

                String filename = FolderCreator.getFilename(id, "book");

                try (BufferedWriter out = new BufferedWriter(new FileWriter(filename, true))) {
                    while ((line = in.readLine()) != null) {
                        out.write(line);
                        out.newLine();
                    }
                }
            }
            System.out.println("Book with ID " + id + " is downloaded.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}