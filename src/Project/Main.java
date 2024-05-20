package Project;

import Project.frames.MainFrame;
import Project.service.BookingService;
import Project.service.MainService;
import Project.service.RoomsService;
import Project.service.UsersService;
import javax.swing.*;

public class Main extends JFrame{

    private static void createFrame()  {
        UsersService usersService = new UsersService();
        RoomsService roomsService = new RoomsService();
        BookingService bookingService = new BookingService();
        MainService mainService = new MainService(bookingService, usersService, roomsService);

        JFrame main = new MainFrame("Projekt hotel", mainService);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setVisible(true);
    }

    public static void main(String args[]) {
        createFrame();
    }
}
