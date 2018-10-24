package com.agp.pbgit.model;

import java.net.URL;
import java.util.Objects;

public class RevisionModel {
    private String type;
    private URL url;
    private String sha1;

    public RevisionModel() {
    }

    public RevisionModel(String type, URL url, String sha1) {
        this.type = type;
        this.url = url;
        this.sha1 = sha1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevisionModel that = (RevisionModel) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(url, that.url) &&
                Objects.equals(sha1, that.sha1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, url, sha1);
    }

    @Override
    public String toString() {
        return "RevisionModel{" +
                "type='" + type + '\'' +
                ", url=" + url +
                ", sha1='" + sha1 + '\'' +
                '}';
    }
}
