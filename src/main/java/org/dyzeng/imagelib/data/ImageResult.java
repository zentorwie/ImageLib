package org.dyzeng.imagelib.data;

import java.util.Comparator;

public class ImageResult implements Comparable<ImageResult> {
    private String url;
    private double similarness;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSimilarness() {
        return similarness;
    }

    public void setSimilarness(double similarness) {
        this.similarness = similarness;
    }

    public ImageResult(String url, double similarness) {
        this.url = url;
        this.similarness = similarness;
    }


    @Override
    public int compareTo(ImageResult o) {
        return Double.compare(o.similarness, similarness);
    }
}
