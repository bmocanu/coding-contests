package ws.bmocanu.aoc.utils;

import java.util.ArrayList;
import java.util.Collection;

public class SList<T> extends ArrayList<T> {

    public SList() {
    }

    public SList(Collection<? extends T> c) {
        super(c);
    }

    public T first() {
        return get(0);
    }

    public void removeFirst() {
        remove(0);
    }

    public void removeLast() {
        remove(size() - 1);
    }

    public T last() {
        return get(size() - 1);
    }

    public T popFirst() {
        T result = get(0);
        remove(0);
        return result;
    }

    public T popLast() {
        int listSize = size();
        T result = get(listSize - 1);
        remove(listSize - 1);
        return result;
    }

    public void rollToTheLeft() {
        T elem = get(0);
        remove(0);
        add(elem);
    }

    public void rollToTheLeft(int positions) {
        for (int index = 0; index < positions; index++) {
            T elem = get(0);
            remove(0);
            add(elem);
        }
    }

}
