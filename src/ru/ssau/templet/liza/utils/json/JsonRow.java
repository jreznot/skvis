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

    public void pack() {
        List<JsonPoint> packed = new ArrayList<>();

        int i = 0;
        while (i < 2 && i < points.size()) {
            packed.add(points.get(i));
            i++;
        }

        boolean prevEquals = false;
        if (packed.size() == 2) {
            prevEquals = packed.get(0).getValue() == packed.get(1).getValue();
        }

        while (i < points.size()) {
            JsonPoint p = points.get(i);

            boolean equals = p.getValue() == packed.get(packed.size() - 1).getValue();
            if (prevEquals && equals) {
                packed.remove(packed.size() - 1);
            }
            packed.add(p);

            prevEquals = equals;
            i++;
        }

        this.points = packed;
    }
}