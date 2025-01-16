package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if (pkeyResultSet.next()) {
                int generatedMessageId = (int) pkeyResultSet.getLong(1);
                return new Message(generatedMessageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageById(int id) {
        Message message = getMessageById(id);
        if (message == null) {
            return null;
        }

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return message;
    }

    public boolean updateMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message.getMessage_text());
            ps.setInt(2, message.getMessage_id());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public List<Message> getUserMessages(int accountId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> userMessages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userMessages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userMessages;
    }
}
