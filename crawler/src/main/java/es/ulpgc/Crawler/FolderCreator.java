package es.ulpgc.Crawler;

import java.io.File;

public class FolderCreator {

    private static boolean foldersCreated = false;

    private static void createFolders() {
        if (!foldersCreated) {
            File dataLakeFolder = new File("DataLake");
            if (!dataLakeFolder.exists()) {
                dataLakeFolder.mkdirs();
            }

            File booksFolder = new File("DataLake/books");
            if (!booksFolder.exists()) {
                booksFolder.mkdirs();
            }

            File metadataFolder = new File("DataLake/metadata");
            if (!metadataFolder.exists()) {
                metadataFolder.mkdirs();
            }

            File contentFolder = new File("DataLake/content");
            if (!contentFolder.exists()) {
                contentFolder.mkdirs();
            }

            foldersCreated = true;
        }
    }

    public static String getFilename(String id, String fileType) {
        createFolders();

        String destination = "DataLake/";
        switch (fileType) {
            case "book" -> destination += "books/";
            case "metadata" -> destination += "metadata/";
            case "content" -> destination += "content/";
        }

        for (int i = 0; i < 3 && i < id.length(); i++) {
            String digits = id.substring(0, i + 1);
            destination += digits + "/";
            File folder = new File(destination);
            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        if (fileType.equals("metadata")){
            destination += id + ".json";
        } else {
            destination += id + ".txt";
        }

        return destination;
    }
}
