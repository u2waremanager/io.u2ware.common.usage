package backend.domain.properties;

import java.util.HashMap;
import java.util.Map;

public class JwtCrypto extends HashMap<String, Object> {

    private String text;

    public JwtCrypto() {
        super();
    }

    public JwtCrypto(String text) {
        super();
        this.text = text;
    }
    public JwtCrypto(Map<? extends String, ? extends Object> map) {
        super(map);
    }

    public boolean hasText() {
        return text != null;
    }

    public String getText() {
        return text;
    }

    public static Attributes of(String key, Object value){
        Attributes a = new Attributes();
        a.put(key, value);
        return a;
    }
}