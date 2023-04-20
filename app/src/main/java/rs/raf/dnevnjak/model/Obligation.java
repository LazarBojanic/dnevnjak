package rs.raf.dnevnjak.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Obligation implements Serializable {
    private Integer id;
    private Integer userId;
    private String title;
    private String description;
    private String priority;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;



    public Obligation() {
        this.id = -1;
        this.userId = -1;
        this.title = "";
        this.description = "";
        this.priority = "";
        this.date = LocalDate.now();
        this.startTime = LocalTime.now();
        this.endTime = LocalTime.now().plusHours(1);
    }

    public Obligation(Integer id, Integer userId, String title, String description, String priority, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Obligation{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
