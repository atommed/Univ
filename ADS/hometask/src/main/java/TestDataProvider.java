import java.util.Arrays;
import java.util.Random;

public class TestDataProvider {
    private Random r = new Random();
    private static String okChars;

    static {
        okChars = "";
        for(char c = 'a'; c < 'z'; c++)
            okChars += c;
        for(char c = 'A'; c < 'Z'; c++)
            okChars += c;
    }

    public int[] genIntsRandom(int size){
        int[] ret = new int[size];
        for (int i = 0; i < size; i++) {
            ret[i] = r.nextInt();
        }
        return ret;
    }
    public int[] genIntsSorted(int size){
        int[] ret = genIntsRandom(size);
        Arrays.sort(ret);
        return ret;
    }
    public int[] genIntsReverseSorted(int size){
        int[] ret = genIntsSorted(size);
        for (int i = 0; i < size / 2; i++) {
            int tmp = ret[i];
            ret[i] = ret[size - i - 1];
            ret[size - i - 1] = tmp;
        }
        return ret;
    }

    public String[] genStringRandom(int size,int maxLength){
        String[] ret = new String[size];
        for(int i = 0; i < size; i++){
            int sLen = r.nextInt(maxLength - 1) + 1;
            String s = "";
            for (int c = 0; c < sLen; c++) {
                s+=okChars.charAt(r.nextInt(okChars.length()));
            }
            ret[i] = s;
        }
        return ret;
    }
}
