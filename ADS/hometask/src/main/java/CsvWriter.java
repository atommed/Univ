import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    private FileWriter w;
    public CsvWriter(String filePath) throws IOException {
        File f = new File(filePath);
        if(!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        w = new FileWriter(filePath);
    }

    public void write(long n, long... other) throws IOException {
        w.write(n+"");
        for(int i = 0; i < other.length; i++)
            w.write(", "+other[i]);
        w.write("\n");
    }

    public void save() throws IOException {
        w.close();
    }
}