package com.hq.model;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午10:08
 * To change this template use File | Settings | File Templates.
 */
public class Region {
    private int id;
    private String name;
    private String urlTag;

    public Region(int id, String name, String urlTag) {
        this.id = id;
        this.name = name;
        this.urlTag = urlTag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlTag() {
        return urlTag;
    }

    public void setUrlTag(String urlTag) {
        this.urlTag = urlTag;
    }
}
