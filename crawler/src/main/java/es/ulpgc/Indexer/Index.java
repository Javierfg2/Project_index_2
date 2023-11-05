package es.ulpgc.Indexer;
import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class Index implements Indexer {
    private String datamart;
    private String datalake;

    private Set<String> indexedFiles;

    public Index(String datalake, String datamart){
        this.datalake = datalake;
        this.datamart = datamart;
        this.indexedFiles = new HashSet<>();
    }

    @Override
    public void indexGenerator(){

        File datalake = new File(this.datalake);

        if (datalake.exists() && datalake.isDirectory()){
            exploreDirectory(datalake);
        }
        else{
            System.out.println("Empty directory");
        }
    }

    @Override
    public String wordBrowser(String word) {
        String route = this.datamart+"/"+word.charAt(0)+"/"+word.charAt(0)+word.charAt(1)+"/"+word.charAt(0)+
                word.charAt(1)+word.charAt(2)+"/"+word+".txt";
        StringBuilder answer = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(route));

            String line;

            while ((line = br.readLine()) != null){
                answer.append(line);
            }

            br.close();

            return answer.toString();
        } catch (FileNotFoundException e) {
            System.out.println("Word not found");
            return " ";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exploreDirectory(File directory){
        File[] files = directory.listFiles();

        if (files != null){
            for (File file :files){
                if (file.isDirectory()){
                    exploreDirectory(file);
                }
                else {
                    if (file.getName().endsWith(".txt")){
                        String fileName = file.getName();
                        if (!indexedFiles.contains(fileName)) {
                            new FileIndexer(file, this.datamart);
                            indexedFiles.add(fileName);
                    }
                }
            }
        }
    }}

    public boolean emptyDatamart() {
        File datamart = new File(this.datamart);

        if (!datamart.exists()){
            return true;
        } else if (datamart.isDirectory()) {
            String[] files = datamart.list();
            return files == null || files.length == 0;
        } else{
            return false;
        }
    }

    public String getDatalake(){
        return this.datalake;
    }

    public String getDatamart(){
        return this.datamart;
    }

    public void setDatalake(String newDatalake) {
        this.datalake = newDatalake;
    }

    public void setDatamart(String newDatamart){
        this.datamart = newDatamart;
    }
}

