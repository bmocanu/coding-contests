package ws.bmocanu.aoc.utils;

import java.util.HashMap;

public class SMap<K, V> extends HashMap<K, V> {

    public SMap() {
        super();
    }

    public V getSafely(K key, V initValue) {
        V value = get(key);
        if (value == null) {
            put(key, initValue);
            value = initValue;
        }
        return value;
    }

}
