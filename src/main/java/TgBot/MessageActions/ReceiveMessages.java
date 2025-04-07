package TgBot.MessageActions;

import DB_Operations.*;

import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class ReceiveMessages {
    private SavingData savingData;


    @Autowired
    public ReceiveMessages(SavingData savingData) {
        this.savingData = savingData;

    }


    public boolean saveMessage(String message, String timeMessage, long chatId) {
       return savingData.saveMessage(message, timeMessage, chatId);
    }

}
