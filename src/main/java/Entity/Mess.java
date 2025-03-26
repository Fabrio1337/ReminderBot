package Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Mess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "message_date", nullable = false, updatable = false)
    private LocalDateTime messageDate = LocalDateTime.now();

    @Column(name = "delayed_message_date")
    private LocalDate delayedMessageDate;

    @Column(name = "is_delayed", nullable = false)
    private Boolean isDelayed = true;

    Mess() {}

    public Mess(String message, LocalDateTime messageDate, LocalDate delayedMessageDate, Boolean isDelayed) {
        this.message = message;
        this.messageDate = messageDate;
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

    public LocalDateTime getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(LocalDateTime messageDate) {
        this.messageDate = messageDate;
    }

    public LocalDate getDelayedMessageDate() {
        return delayedMessageDate;
    }

    public void setDelayedMessageDate(LocalDate delayedMessageDate) {
        this.delayedMessageDate = delayedMessageDate;
    }

    public Boolean getDelayed() {
        return isDelayed;
    }

    public void setDelayed(Boolean delayed) {
        isDelayed = delayed;
    }
}
