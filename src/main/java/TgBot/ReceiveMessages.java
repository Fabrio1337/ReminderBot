package TgBot;

import DB_Operations.*;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@Scope("prototype")
public class ReceiveMessages {
    private SavingMessages savingMessages;
    private SavingUser savingUser;
    private SetSession setSession;

    @Autowired
    public ReceiveMessages(SavingMessages savingMessages, SavingUser savingUser, SetSession setSession) {
        this.savingMessages = savingMessages;
        this.savingUser = savingUser;
        this.setSession = setSession;
    }


    public boolean saveMessage(String message, String timeMessage, long chatId) {
       return savingMessages.saveMessage(setSession.getSession(), message, timeMessage, chatId);
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
