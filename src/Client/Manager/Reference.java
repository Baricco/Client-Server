package Manager;

public class Reference<DataType> {
    private DataType value;

    public Reference(DataType value) { this.value = value; }

    public Reference() { }

    public DataType getValue() { return value; }

    public void setValue(DataType value) { this.value = value; }

    @Override public String toString() { return value.toString(); }

    public boolean isEqual(Reference<DataType> other) { return this.value == other.getValue(); }
    
    public boolean isEqual(DataType other) { return this.value == other; }

    public void swap(Reference<DataType> reference) {
        DataType temp = reference.value;
        reference.value = this.value;
        this.value = temp;
    }
}
