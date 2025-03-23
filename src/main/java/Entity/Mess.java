package Entity;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Mess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message")
    private String text_message;

    @Column(name= "message_date")
    private String date;

    @Column(name = "delayed_message_time")
    private String delayed_message_time;

    @Column(name = "delayed_message_date")
    private String delayed_message_date;

    @Column(name = "is_delayed")
    private Boolean is_delayed;

    Mess() {}

    public Mess(String text_message, String date, String delayed_message_time, String delayed_message_date, Boolean is_delayed) {
        this.text_message = text_message;
        this.date = date;
        this.delayed_message_time = delayed_message_time;
        this.delayed_message_date = delayed_message_date;
        this.is_delayed = is_delayed;
    }

    public String getText_message() {
        return text_message;
    }

    public void setText_message(String text_message) {
        this.text_message = text_message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDelayed_message_time() {
        return delayed_message_time;
    }

    public void setDelayed_message_time(String delayed_message_time) {
        this.delayed_message_time = delayed_message_time;
    }

    public String getDelayed_message_date() {
        return delayed_message_date;
    }

    public void setDelayed_message_date(String delayed_message_date) {
        this.delayed_message_date = delayed_message_date;
    }

    public Boolean getIs_delayed() {
        return is_delayed;
    }

    public void setIs_delayed(Boolean is_delayed) {
        this.is_delayed = is_delayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
