package app.circle.foodmood.model;


public class Response<T> {


    public Boolean isResultAvailable = false;
    public Boolean isSuccessful = false;
    public T result;

    public String[] message;

    public Boolean getResultAvailable() {
        return isResultAvailable;
    }

    public void setResultAvailable(Boolean resultAvailable) {
        isResultAvailable = resultAvailable;
    }

    public Boolean getSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String[] getMessage() {
        return message;
    }

    public void setMessage(String[] message) {
        this.message = message;
    }
}
