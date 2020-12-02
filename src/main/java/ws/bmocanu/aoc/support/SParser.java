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
        List<T> resultList = new ArrayList<>();
        for (String str : stringList) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                try {
                    T entry = clazz.getDeclaredConstructor().newInstance();
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
                            case "java.lang.String":
                                field.set(entry, fieldValue);
                                break;
                            default:
                                // TODO
                                System.out.println("ERROR: Unknown field type [" + field.getType().getName() +
                                        "] for field name [" + fieldName + "]");
                        }
                    }
                    resultList.add(entry);
                } catch (Exception e) {
                    // TODO
                    e.printStackTrace();
                }
            } else {
                // TODO
                System.out.println("ERROR: pattern did not match string: " + str);
            }
        }
        return resultList;
    }

}
