package TgBot;

import DB_Operations.GettingMessages;
import DB_Operations.GettingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SendMessages {
    private GettingMessages gettingMessages;
    private GettingUser gettingUser;

    @Autowired
    public SendMessages(GettingMessages gettingMessages, GettingUser gettingUser) {
        this.gettingMessages = gettingMessages;
        this.gettingUser = gettingUser;
    }
}
