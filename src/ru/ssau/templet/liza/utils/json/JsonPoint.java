package ru.ssau.templet.liza.utils.json;

/**
 * @author Yuriy Artamonov
 */
public class JsonPoint {

    private long timeStamp;

    private long value;

    public JsonPoint(long timeStamp, long value) {
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}