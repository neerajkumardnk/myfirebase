package in.checkthem.myfirebase;

/**
 * Created by reach on 3/18/2017.
 */

public class Blog {

    private String Title, Message,Status;

    public Blog() {
    }

    public Blog(String title, String message, String status) {

        this.Title = title;
        this.Message = message;
        this.Status=status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }



}
