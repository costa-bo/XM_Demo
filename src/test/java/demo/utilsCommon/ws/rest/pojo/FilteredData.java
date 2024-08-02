package demo.utilsCommon.ws.rest.pojo;

public class FilteredData {
    private int id;
    private String variable;
    private String value;

    public FilteredData(int id, String variable, String value) {
        this.id = id;
        this.variable = variable;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getVariable() {
        return variable;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() { return getVariable(); }
}
