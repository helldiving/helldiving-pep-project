package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        if (message.getPosted_by() <= 0 
        || message.getMessage_text().isBlank() 
        || message.getMessage_text().length() > 255) {
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessagesById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessage(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    public Message updateMessage(int message_id, String message_text) {
        if (message_id <= 0 || message_text.isBlank() || message_text.length() > 255)
         {
            return null;
        }
        return messageDAO.updateMessage(message_id, message_text);

    }

    public List<Message> getUserMessages(int account_id) {
        return messageDAO.getUserMessages(account_id);
    }

    // createMessage(Message message)
    // getAllMessages()
    // getMessageById(int message_id)
    // deleteMessage(int message_id)
    // updateMessage(int message_id, String message_text)
    // getUserMessages(int account_id)




}
