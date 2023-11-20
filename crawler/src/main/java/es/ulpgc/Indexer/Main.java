package es.ulpgc.word_searcher.Indexer;

public class Main {
    public static void main(String[] args) {
        TreeIndexer index = new TreeIndexer("Datalake", "Datamart");
        index.indexGenerator();
    }
}