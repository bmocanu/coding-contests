package ws.bmocanu.aoc.support;

public class Pointer<ValueType> {

    public ValueType value;

    public int position;

    public Pointer(ValueType value, int position) {
        this.value = value;
        this.position = position;
    }
    
}
