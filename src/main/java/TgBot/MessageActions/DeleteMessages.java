package TgBot.MessageActions;

import DB_Operations.DeletionData;
import DB_Operations.GettingData;
import Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteMessages {

    private DeletionData deletionData;
    private GettingData gettingData;

    @Autowired
    public DeleteMessages(DeletionData deletionData, GettingData gettingData) {
        this.deletionData = deletionData;
        this.gettingData = gettingData;
    }

    public boolean deleteMessage(long id, long userId) {
        User user = gettingData.getUserByChatId(userId);
        return deletionData.deleteMessage(id, user.getId());
    }
}
