package ru.ssau.templet.liza.utils.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Yuriy Artamonov
 */
public class JsonRow {

    private String name;

    private List<JsonPoint> points = new ArrayList<>();

    public JsonRow(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonPoint> getPoints() {
        return points;
    }

    public void setPoints(List<JsonPoint> points) {
        this.points = points;
    }

    public void normalize() {
        Collections.sort(points, new Comparator<JsonPoint>() {
            @Override
            public int compare(JsonPoint o1, JsonPoint o2) {
                return Long.valueOf(o1.getTimeStamp()).compareTo(o2.getTimeStamp());
            }
        });
    }
}