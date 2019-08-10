package app.circle.foodmood.security;

public class Error {


    private char code[];
    private String message;
    private String title;

    public Error(int code, String message, String title) {
        this.code = (code + "").toCharArray();
        this.message = message;
        this.title = title;
    }

    public char[] getCode() {
        return code;
    }

    public void setCode(char[] code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
