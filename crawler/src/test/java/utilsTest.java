import org.junit.Assert;
import org.junit.Test;
import es.ulpgc.Indexer.Utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class utilsTest {

    @Test
    public void testEliminateMetadata() throws IOException {

        File file = new File("test.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("*** metadata ***\n");
        writer.write("content to be shown");
        writer.close();

        String result = Utils.eliminateMetadata(file);

        file.delete();

        Assert.assertEquals("content to be shown\n", result);
    }

    @Test
    public void testNormalize(){
        String result = Utils.normalize("This phrase sh;ould be normalize;");
        Assert.assertEquals("this phrase sh ould be normalize ", result);
    }

}
