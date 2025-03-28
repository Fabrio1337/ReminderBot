package TgBot;

import DB_Operations.*;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Scope("prototype")
public class ReceiveMessages {
    private SavingData savingData;


    @Autowired
    public ReceiveMessages(SavingData savingData, SetSession setSession) {
        this.savingData = savingData;

    }


    public boolean saveMessage(String message, String timeMessage, long chatId) {
       return savingData.saveMessage(message, timeMessage, chatId);
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
