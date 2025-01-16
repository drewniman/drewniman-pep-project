package Service;

import Model.Account;
import Model.Message;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        if (
            message.getMessage_text().isEmpty() ||
            message.getMessage_text().length() > 255 ||
            accountDAO.getAccountById(message.getPosted_by())  == null
        ) {
            return null;
        }
        return messageDAO.insertMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }
}
