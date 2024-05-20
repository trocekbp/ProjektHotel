package Project.service;

import Project.model.Client;
import Project.model.Employee;
import Project.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersService {

    private List<User> userList;
    private User activeUser;

    public UsersService() {
        loadUsers();
    }

    List<User> getUserList() {
        return userList;
    }

    public User getActiveUser() {
        return activeUser;
    }

    void setActiveUser(String userLogin) {
        this.activeUser = userList.stream().filter(it -> it.getLogin().equals(userLogin)).findFirst().get();
    }

    private void loadUsers(){
        userList = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "users"));
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                if(data[0].equals("employee"))
                    userList.add(new Employee(data[1], data[2], data[3], data[4]));
                else
                    userList.add(new Client(data[1], data[2], data[3], data[4]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
