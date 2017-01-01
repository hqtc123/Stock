package net.hqdo.stock.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author Qing
 * @since 2016/12/6.
 */
@Entity
@Table(name = "price", schema = "stock")
public class PriceEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    private String code;
    private Double now;
    private Double diffPercentage;
    private Double lastFinish;
    private Double start;
    private Double highest;
    private Double lowest;
    private Date date;
    private Timestamp createTime;
    private Timestamp modifyTime;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "now")
    public Double getNow() {
        return now;
    }

    public void setNow(Double now) {
        this.now = now;
    }

    @Basic
    @Column(name = "diff_percentage")
    public Double getDiffPercentage() {
        return diffPercentage;
    }

    public void setDiffPercentage(Double diffPercentage) {
        this.diffPercentage = diffPercentage;
    }

    @Basic
    @Column(name = "last_finish")
    public Double getLastFinish() {
        return lastFinish;
    }

    public void setLastFinish(Double lastFinish) {
        this.lastFinish = lastFinish;
    }

    @Basic
    @Column(name = "start")
    public Double getStart() {
        return start;
    }

    public void setStart(Double start) {
        this.start = start;
    }

    @Basic
    @Column(name = "highest")
    public Double getHighest() {
        return highest;
    }

    public void setHighest(Double highest) {
        this.highest = highest;
    }

    @Basic
    @Column(name = "lowest")
    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "modify_time")
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceEntity that = (PriceEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (now != null ? !now.equals(that.now) : that.now != null) return false;
        if (diffPercentage != null ? !diffPercentage.equals(that.diffPercentage) : that.diffPercentage != null)
            return false;
        if (lastFinish != null ? !lastFinish.equals(that.lastFinish) : that.lastFinish != null) return false;
        if (start != null ? !start.equals(that.start) : that.start != null) return false;
        if (highest != null ? !highest.equals(that.highest) : that.highest != null) return false;
        if (lowest != null ? !lowest.equals(that.lowest) : that.lowest != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (now != null ? now.hashCode() : 0);
        result = 31 * result + (diffPercentage != null ? diffPercentage.hashCode() : 0);
        result = 31 * result + (lastFinish != null ? lastFinish.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (highest != null ? highest.hashCode() : 0);
        result = 31 * result + (lowest != null ? lowest.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }
}
