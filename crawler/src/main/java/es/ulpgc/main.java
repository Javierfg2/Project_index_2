package es.ulpgc;
import es.ulpgc.Cleaner.Cleaner;
import es.ulpgc.Crawler.CrawlerTimer;
import es.ulpgc.Indexer.Index;

import java.io.File;
import java.io.IOException;

import java.util.Timer;
import java.util.TimerTask;

public class main {
    public static void main(String[] args) throws IOException {
        String datalake = "Datalake";
        String datamart = "Datamart";
        File filePath = new File(datalake);
        Index index = new Index(datalake, datamart);

        CrawlerTimer.scheduleDownloadTask();

        Timer timer = new Timer();
        TimerTask timertask = new TimerTask() {
            @Override
            public void run() {
                try {
                    Cleaner.cleanBooks(filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                index.indexGenerator();
            }
        };

        timer.schedule(timertask, 0, 60 * 1000);
    }


}
