package TgBot;

import DB_Operations.*;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ReceiveMessages {
    private SavingMessages savingMessages;
    private SavingUser savingUser;

    @Autowired
    public ReceiveMessages(SavingMessages savingMessages, SavingUser savingUser) {
        this.savingMessages = savingMessages;
        this.savingUser = savingUser;
    }

    public boolean saveMessage(String message) {
        try
        {

            return  true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public boolean saveUser(User user) {
        try
        {

            return  true;
        }
        catch(Exception e)
        {
            return false;
        }
    }




}
