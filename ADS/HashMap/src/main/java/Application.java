import io.github.atommed.hashmap.HashMap;

/**
 * Created by gregory on 25.05.16.
 */
public class Application {
    public static void main(String... args){
        HashMap<String, String> map = new HashMap<>();
        map.put("Key","Value");
        System.out.println(map.get("Key"));
    }
}
