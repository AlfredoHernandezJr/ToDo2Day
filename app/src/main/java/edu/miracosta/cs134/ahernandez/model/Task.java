package edu.miracosta.cs134.ahernandez.model;

public class Task {

    private long mId ;
    private String mDescription ;
    private boolean mIsDone ;

    public Task(long id, String description, boolean isDone) {
        mId = id;
        mDescription = description;
        mIsDone = isDone;
    }

    public Task (String description, boolean isDone)
    {
        this(-1, description, isDone) ;
    }

    public Task(String description)
    {
        this(-1,description, false) ;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean isDone) {
        mIsDone = isDone;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + mId +
                ", description='" + mDescription + '\'' +
                ", isDone=" + mIsDone +
                '}';
    }
}