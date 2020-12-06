package ws.bmocanu.aoc.flex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class FlexChars {

    private final Map<Character, Integer> charMap = new HashMap<>();

    public FlexChars addChars(String str) {
        for (char chr : str.toCharArray()) {
            charMap.merge(chr, 1, Integer::sum);
        }
        return this;
    }

    public FlexChars retainCommonWithCounting(String str) {
        Set<Character> input = new HashSet<>();
        for (char chr : str.toCharArray()) {
            input.add(chr);
        }
        charMap.entrySet().removeIf((entry) -> !input.contains(entry.getKey()));
        for (Character chr : input) {
            Integer count = charMap.get(chr);
            if (count != null) {
                charMap.put(chr, count + 1);
            }
        }
        return this;
    }

    public FlexChars retainCommonWithoutCounting(String str) {
        Set<Character> input = new HashSet<>();
        for (char chr : str.toCharArray()) {
            input.add(chr);
        }
        charMap.entrySet().removeIf((entry) -> !input.contains(entry.getKey()));
        return this;
    }

    public int size() {
        return charMap.size();
    }

    public boolean isEmpty() {
        return charMap.isEmpty();
    }

    public FlexChars reset() {
        charMap.clear();
        return this;
    }

    public Set<Character> getCharsWithCount(int count) {
        Set<Character> result = new HashSet<>();
        charMap.entrySet().forEach((entry) -> {
            if (entry.getValue() == count) {
                result.add(entry.getKey());
            }
        });
        return result;
    }

}
