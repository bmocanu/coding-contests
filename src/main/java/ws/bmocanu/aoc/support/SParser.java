package ws.bmocanu.aoc.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SParser {

    private final Pattern pattern;
    private final String[] fieldNames;

    public SParser(String regex, String... fieldNames) {
        this.pattern = Pattern.compile(regex);
        this.fieldNames = fieldNames;
    }

    public <T> List<T> parse(List<String> stringList, Class<T> clazz) {
        List<T> entryList = new ArrayList<>();
        for (String str : stringList) {
            T entry = parse(str, clazz);
            if (entry != null) {
                entryList.add(entry);
            }
        }
        return entryList;
    }

    public <T> T parse(String str, Class<T> clazz) {
        T entry = null;
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            try {
                entry = clazz.getDeclaredConstructor().newInstance();
                for (int fieldIndex = 0; fieldIndex < fieldNames.length; fieldIndex++) {
                    String fieldName = fieldNames[fieldIndex];
                    String fieldValue = matcher.group(fieldIndex + 1);
                    Field field = clazz.getDeclaredField(fieldName);
                    if (!field.canAccess(entry)) {
                        field.setAccessible(true);
                    }
                    switch (field.getType().getName()) {
                        case "int":
                            field.set(entry, Integer.parseInt(fieldValue));
                            break;
                        case "char":
                            field.set(entry, fieldValue.charAt(0));
                            break;
                        case "long":
                            field.set(entry, Long.parseLong(fieldValue));
                            break;
                        case "double":
                            field.set(entry, Double.parseDouble(fieldValue));
                            break;
                        case "java.lang.String":
                            field.set(entry, fieldValue);
                            break;
                        default:
                            Log.error("Unknown field type [%s] for field name [%s]",
                                      field.getType().getName(), fieldName);
                    }
                }
            } catch (Exception e) {
                Log.error(e, "Failed to instantiate the type or set one of the fields of type [%s]", clazz.getName());
            }
        } else {
            Log.error("Pattern did not match string: %s", str);
        }
        return entry;
    }

}
