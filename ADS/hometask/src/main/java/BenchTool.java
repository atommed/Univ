import java.io.FileWriter;
import java.io.IOException;

public class BenchTool {
    private FileWriter w;
    public BenchTool(String filePath) throws IOException {
        w = new FileWriter(filePath);
    }

    public void measure(long n, Runnable r) throws IOException {
        long beforeRun = System.nanoTime();
        r.run();
        long afterRun = System.nanoTime();
        w.write(n+", "+(afterRun - beforeRun));
    }

    public void save() throws IOException {
        w.close();
    }
}
