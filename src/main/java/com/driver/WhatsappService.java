package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository=new WhatsappRepository();
    public boolean ifNewUser(String mobile){
        return whatsappRepository.ifNewUser(mobile);
    }
    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public Group createGroup(List<User> users) {
        return whatsappRepository.createGroup(users);
    }

    public String createUser(String name, String mobile) {
         whatsappRepository.createUser(name,mobile);

         return "user added";
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return whatsappRepository.sendMessage(message,sender,group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        return whatsappRepository.changeAdmin(approver,user,group);
    }


}
