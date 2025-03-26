package TgBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class MessagesHandler {
    private SendMessages sendMessages;

    @Autowired
    MessagesHandler(SendMessages sendMessages)
    {
        this.sendMessages = sendMessages;
    }

}
