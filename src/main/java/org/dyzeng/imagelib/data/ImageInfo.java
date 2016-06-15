package org.dyzeng.imagelib.data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class ImageInfo {
    static Long nextId = 0L;

    @Id
    private Long id;

    private String fileName;

    @ElementCollection
    private List<Long> features;

    public ImageInfo() {
        synchronized (nextId) {
            id = nextId++;
        }
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Long> getFeatures() {
        return features;
    }

    public void setFeatures(List<Long> features) {
        this.features = features;
    }
}
