package demo.utilsCommon.ws.rest.pojo;

public class FilteredDataCombi {
    private int id;
    private String description;
    private boolean isFound;

    public FilteredDataCombi(int id, String description, boolean isFound) {
        this.id = id;
        this.description = description;
        this.isFound = isFound;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsFound() {
        return isFound;
    }

    public void setIsFound(boolean isFound) {
        this.isFound = isFound;
    }
}
