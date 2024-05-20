package Project.model;

import Project.frames.BookingsFrame;
import Project.frames.NewRoomFrame;
import Project.service.MainService;

import javax.swing.*;
import java.util.List;

public class Employee extends User {

    public Employee(String login, String name, String contactNumber, String email) {
        super(login, name, contactNumber, email);
    }

    @Override
    public JPanel getPanel(boolean hasReservations, User activeUser, MainService mainService) {
        JPanel panel = new JPanel();
        JButton addRoomButton = new JButton("Dodaj pokój");
        panel.add(addRoomButton);
        addRoomButton.addActionListener(
                e -> {
                    JFrame newRoomFrame = new NewRoomFrame(mainService);
                    newRoomFrame.setVisible(true);
                }
        );
        JButton manageBookingsButton = new JButton("Zarządzaj rezerwacjami");
        panel.add(manageBookingsButton);
        manageBookingsButton.addActionListener(
                e -> {
                    JFrame newRoomFrame = new BookingsFrame(activeUser, mainService);
                    newRoomFrame.setVisible(true);
                }
        );
        panel.add(manageBookingsButton);
        return panel;
    }

    @Override
    public boolean canExtendBooking(Booking booking) {
        return false;
    }

    @Override
    public String getActionButtonLabel(Booking booking) {
        return "Potwierdź";
    }

    @Override
    public Boolean getActionButtonEnabled(Booking booking) {
        return !booking.getConfirmed();
    }

    @Override
    public List<Booking> getBookings(List<Booking> bookings) {
        return bookings;
    }

    @Override
    public void performBookingAction(Booking booking) {
        booking.setConfirmed(true);
    }
}
