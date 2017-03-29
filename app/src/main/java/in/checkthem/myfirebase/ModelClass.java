package in.checkthem.myfirebase;

/**
 * Created by reach on 3/25/2017.
 */

public class ModelClass {
    String title,message, status;

    public ModelClass(String title, String message, String status) {
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public ModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
