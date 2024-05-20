package Project.frames;

import Project.model.Client;
import Project.model.User;
import Project.service.MainService;

import javax.swing.*;
import java.awt.*;

public class SystemFrame extends JFrame {

    private MainService mainService;
    private JLabel userLabel;
    private JPanel systemPanel;

    public SystemFrame(String title, MainService mainService) throws HeadlessException {
        super(title);
        this.mainService = mainService;
        setBounds(300, 90, 650, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        systemPanel = new JPanel();
        User user = mainService.getActiveUser();
        userLabel = new JLabel(user.getName());
        systemPanel.add(userLabel);
        add(systemPanel);
        boolean userHasBookings = false;
        if(user instanceof Client)
            userHasBookings = !mainService.getUserBookings(user).isEmpty(); //jesli nie jest puste to true i wtedy dodaje przycis
            systemPanel.add(user.getPanel(userHasBookings, user, mainService));
    }

}
