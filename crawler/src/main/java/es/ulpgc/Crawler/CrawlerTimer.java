package es.ulpgc.Crawler;

import java.util.TimerTask;
import java.util.Timer;

public class CrawlerTimer {
    private static int currentBookId = 1;
    public static int maxBookId = 1000;
    static Timer timer = new Timer();

    public static void scheduleDownloadTask() {
        timer.scheduleAtFixedRate(new DownloadTask(), 0, 60 * 1000);
    }

    public static class DownloadTask extends TimerTask {
        public void run() {
            if (currentBookId <= maxBookId) {
                String txtFileUrl = "https://www.gutenberg.org/cache/epub/" + currentBookId + "/pg" + currentBookId + ".txt";
                DownloadBook.downloadTextFile(txtFileUrl, Integer.toString(currentBookId));
                currentBookId++;
            } else {
                timer.cancel();
            }
        }
    }
}






