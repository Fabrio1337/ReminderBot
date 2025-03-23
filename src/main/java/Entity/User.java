package Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_chat_id")
    private long UserChatId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private List<Mess> messages;

    public User() {}

    public User(int UserChatId) {
        this.UserChatId = UserChatId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getChatId() {
        return UserChatId;
    }

    public void setChatId(long UserChatId) {
        this.UserChatId = UserChatId;
    }

    public List<Mess> getMessages() {
        return messages;
    }

    public void setMessages(List<Mess> messages) {
        this.messages = messages;
    }
}
