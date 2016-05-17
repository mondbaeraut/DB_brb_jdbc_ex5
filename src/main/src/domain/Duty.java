package domain;

import java.sql.Timestamp;

/**
 * Created by mond on 10.05.2016.
 * Domain Object. Represents "Dienst" in the DB
 */
public class Duty extends DomainObject{

    private String place;
    private Timestamp begin;
    private String dayTime;
    private double duration;
    private String name;
    private String remark;
    private String dutyType;
    private int besId;


    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public int getBesId() {
        return besId;
    }

    public void setBesId(String besId) {
        if(besId != null) {
            this.besId = Integer.parseInt(besId);
        }
    }
}
