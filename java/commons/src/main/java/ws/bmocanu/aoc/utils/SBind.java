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

    public List<T> bindAll(List<String> stringList) {
        List<T> entryList = new ArrayList<>();
        for (String str : stringList) {
            T entry = bindOne(str);
            if (entry != null) {
                entryList.add(entry);
            }
        }
        return entryList;
    }

    public List<T> bindAll(List<String> stringList, int startIndex) {
        List<T> entryList = new ArrayList<>();
        for (int index = startIndex; index < stringList.size(); index++) {
            T entry = bindOne(stringList.get(index));
            if (entry != null) {
                entryList.add(entry);
            }
        }
        return entryList;
    }

    public T bindOne(String str) {
        T entry = null;
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            try {
                entry = clazz.getDeclaredConstructor().newInstance();
                for (int fieldIndex = 0; fieldIndex < fieldNames.length; fieldIndex++) {
                    String fieldName = fieldNames[fieldIndex];
                    String[] fieldComponentArray = fieldName.split("\\.");
                    String targetFieldName = fieldName; // this could be a deep field hierarchy (field1.field2...)
                    Object targetInstance = entry;
                    Class<?> targetClass = clazz;
                    // check if traversal of the field hierarchy is needed
                    // if YES, then traverse and create missing instances along the way
                    if (fieldComponentArray.length > 1) {
                        for (int index = 0; index < fieldComponentArray.length - 1; index++) {
                            Field field = targetClass.getDeclaredField(fieldComponentArray[index]);
                            if (!field.canAccess(targetInstance)) {
                                field.setAccessible(true);
                            }
                            Object nextTargetInstance = field.get(targetInstance);
                            Class<?> nextTargetClass = field.getType();
                            if (nextTargetInstance == null) {
                                nextTargetInstance = nextTargetClass.getDeclaredConstructor().newInstance();
                                field.set(targetInstance, nextTargetInstance);
                            }
                            targetInstance = nextTargetInstance;
                            targetClass = nextTargetClass;
                        }
                        targetFieldName = fieldComponentArray[fieldComponentArray.length - 1];
                    }
                    // now set the final (primitive) value
                    String fieldValue = matcher.group(fieldIndex + 1);
                    Field field = targetClass.getDeclaredField(targetFieldName);
                    if (!field.canAccess(targetInstance)) {
                        field.setAccessible(true);
                    }
                    switch (field.getType().getName()) {
                        case "int":
                            field.set(targetInstance, Integer.parseInt(fieldValue));
                            break;
                        case "char": // falls through
                        case "java.lang.Character":
                            field.set(targetInstance, fieldValue.charAt(0));
                            break;
                        case "long":
                            field.set(targetInstance, Long.parseLong(fieldValue));
                            break;
                        case "double":
                            field.set(targetInstance, Double.parseDouble(fieldValue));
                            break;
                        case "java.lang.String":
                            field.set(targetInstance, fieldValue);
                            break;
                        default:
                            Log.error("Unknown field type [%s] for field name [%s], " +
                                      "in the hierarchy structure [%s]",
                                      field.getType().getName(), targetFieldName, fieldName);
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
