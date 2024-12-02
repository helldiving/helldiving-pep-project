package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

public Message createMessage(Message message) {
    
    Connection connection = ConnectionUtil.getConnection();

    try {

        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, message.getPosted_by());
        preparedStatement.setString(2, message.getMessage_text());
        preparedStatement.setLong(3, message.getTime_posted_epoch());

        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            int generated_message_id = (int) rs.getLong(1);
            return new Message(
                generated_message_id, 
                message.getPosted_by(), 
                message.getMessage_text(), 
                message.getTime_posted_epoch()
                );
        }
  
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    return null;
}

public List<Message> getAllMessages() {

    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>();

    try {

        String sql = "SELECT * from message";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Message message = new Message(
                rs.getInt("message_id"), 
                rs.getInt("posted_by"), 
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch")
                );
                messages.add(message);
        }

    }catch(Exception e){
        System.out.println(e.getMessage());
    }

    return messages;
}

public Message getMessageById(int message_id) {

    Connection connection = ConnectionUtil.getConnection();

    try {

        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, message_id);

        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            Message message = new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
                );

                return message;
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }

    return null;
}

/* create table message (
    message_id int primary key auto_increment,
    posted_by int,
    message_text varchar(255),
    time_posted_epoch bigint,
    foreign key (posted_by) references  account(account_id)
); */

public Message deleteMessage(int message_id) {

}

public Message updateMessage(int message_id, String message_text) {

}

public List<Message> getUserMessages(int account_id) {

}

    
}

