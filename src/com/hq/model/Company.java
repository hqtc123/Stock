package com.hq.model;

/**
 * Created with IntelliJ IDEA.
 * User: He Qing
 * Date: 13-7-21
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */
public class Company {
    private int id;
    private String name;

    public Company(String name, String numStr, int industryId, int sectionId, int regionId) {
        this.name = name;
        this.numStr = numStr;
        this.industryId = industryId;
        this.sectionId = sectionId;
        this.regionId = regionId;
    }

    private String numStr;
    private int industryId;
    private int sectionId;
    private int regionId;

    public Company() {

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

    public String getNumStr() {
        return numStr;
    }

    public void setNumStr(String numStr) {
        this.numStr = numStr;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
