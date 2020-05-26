package model;

import com.google.firebase.Timestamp;

public class Journal {
    String title,thought,imageURL,username,userID;
    Timestamp timestamp;

    public Journal() { }

    public Journal(String title, String thought, String imageURL, String username, String userID, Timestamp timestamp) {
        this.title = title;
        this.thought = thought;
        this.imageURL = imageURL;
        this.username = username;
        this.userID = userID;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
