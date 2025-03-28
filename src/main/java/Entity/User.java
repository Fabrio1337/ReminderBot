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

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},  fetch = FetchType.EAGER)
    private List<Message> messages;

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }


}
