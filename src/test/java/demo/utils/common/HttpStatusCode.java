package demo.utils.common;

public enum HttpStatusCode {

    //2xx: Success
    OK(200, "OK");

    private final int value;
    private final String description;

    HttpStatusCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " " + description;
    }
}
