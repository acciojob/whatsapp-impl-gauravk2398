package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String, User> userAcc;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userAcc=new HashMap<String,User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exist");
        if(!adminMap.get(group).equals(approver))
            throw new Exception("Approver does not have rights");
        if(!this.userExistsInGroup(group,user))
            throw new Exception("User is not in the Group");
        adminMap.put(group,user);
        return "SUCCESS";
    }

    private boolean userExistsInGroup(Group group, User sender) {
        List<User> users= groupUserMap.get(group);
        for(User user:users){
            if(user.equals(sender))
                return true;

        }
        return false;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        if(!groupUserMap.containsKey(group))
            throw new Exception("Group does not exists");
        if(!this.userExistsInGroup(group,sender))
            throw new Exception("not allowed to send message");
        List<Message> mssges=new ArrayList<>();
        mssges.add(message);
        groupMessageMap.put(group,mssges);
        return mssges.size();
    }

    public String createUser(String name, String mobile) {
        userAcc.put(mobile,new User(name,mobile));
        return "User added";
    }

    public  Group createGroup(List<User> users) {
        if(users.size()==2)
            return this.createPersnlChat(users);
        this.customGroupCount++;
        String grpName="Group"+this.customGroupCount;
        Group group=new Group(grpName,users.size());
        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));
        return group;
    }

    private Group createPersnlChat(List<User> users) {
        String grpName=users.get(1).getName();
        Group prsnlGrp=new Group(grpName,2);
        groupUserMap.put(prsnlGrp,users);
        return prsnlGrp;
    }

    public int createMessage(String content) {
        this.messageId++;
        Message mssg=new Message(messageId,content,new Date());
        return this.messageId;
    }

    public boolean ifNewUser(String mobile) {
        if(userAcc.containsKey(mobile))
            return false;
        return true;
    }
}
