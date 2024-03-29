package ws.bmocanu.aoc.flex;

public class Pointer<ValueType> {

    public ValueType value;

    public int position;

    public Pointer(ValueType value, int position) {
        this.value = value;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "value=" + value +
                ", position=" + position +
                '}';
    }
}
