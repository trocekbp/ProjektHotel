package Project.frames;

import Project.service.MainService;

import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;


public class MainFrame extends JFrame{

    private MainService mainService;

    private JPanel loginPanel;
    private JLabel selectUserLabel;
    private JButton confirmLogin;
    private JComboBox<String> usersList;

    public void setUsers(){
        mainService.getUsersList().forEach(it -> usersList.addItem(it.getLogin()));
    }

    public MainFrame(String title, MainService mainService) throws HeadlessException {
        super(title);
        this.mainService = mainService;

        confirmLogin = new JButton("Zatwierdź");
        usersList = new JComboBox<>();
        usersList.setBounds(0,100,400,50);
        confirmLogin.setBounds(420,100,200,50);
        selectUserLabel = new JLabel("Wybierz swojego użytkownika:");
        selectUserLabel.setBounds(250, 0, 250, 150);

        loginPanel = new JPanel();
        loginPanel.add(selectUserLabel);
        loginPanel.add(usersList);
        loginPanel.add(confirmLogin);

        add(loginPanel);
        setBounds(300, 90, 650, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUsers();
        confirmLogin.addActionListener(
                e -> {
                    mainService.setActiveUser(usersList.getItemAt(usersList.getSelectedIndex()));
                    System.out.println(mainService.getActiveUser().getLogin());
                    dispose();
                    JFrame systemFrame = new SystemFrame("System", mainService);
                    systemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    systemFrame.setVisible(true);
                }
        );
    }

}



