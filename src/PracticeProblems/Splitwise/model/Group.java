package PracticeProblems.Splitwise.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupId;
    private String groupName;
    private List<User> users;

    public Group(String groupId, String groupName, List<User> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        users = new ArrayList<>();
    }

    public void addUserToGroup(User user) {
        users.add(user);
    }
}
