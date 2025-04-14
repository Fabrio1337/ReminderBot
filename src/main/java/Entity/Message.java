package Entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "delayed_message_date")
    private String delayedMessageDate;

    @Column(name = "is_delayed", nullable = false)
    private Boolean isDelayed = true;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Message() {}

    public Message(String message, String delayedMessageDate, Boolean isDelayed) {
        this.message = message;
        this.delayedMessageDate = delayedMessageDate;
        this.isDelayed = isDelayed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDelayedMessageDate() {
        return delayedMessageDate;
    }

    public void setDelayedMessageDate(String delayedMessageDate) {
        this.delayedMessageDate = delayedMessageDate;
    }

    public Boolean getDelayed() {
        return isDelayed;
    }

    public void setDelayed(Boolean delayed) {
        isDelayed = delayed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ID сообщения: " + this.getId() +
                "\n Сообщение = " + this.getMessage()  +
                "\n Время отправки = " + this.getDelayedMessageDate();
    }
}
