package ws.bmocanu.aoc.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ws.bmocanu.aoc.support.Log;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class SBind<T> {

    private final Pattern pattern;
    private final String[] fieldNames;
    private final Class<T> clazz;

    public SBind(String regex, Class<T> clazz, String... fieldNames) {
        this.pattern = Pattern.compile(regex);
        this.fieldNames = fieldNames;
        this.clazz = clazz;
    }

    public List<T> bind(List<String> stringList) {
        List<T> entryList = new ArrayList<>();
        for (String str : stringList) {
            T entry = bind(str);
            if (entry != null) {
                entryList.add(entry);
            }
        }
        return entryList;
    }

    public T bind(String str) {
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
                        case "java.lang.Character":
                            field.set(entry, Character.valueOf(fieldValue.charAt(0)));
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
            Log.error("Pattern did not match string: [%s]", str);
        }
        return entry;
    }

}
