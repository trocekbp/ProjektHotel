package Project.model;

import Project.service.MainService;

import javax.swing.*;
import java.util.List;

public abstract class User {
    private String login;
    private String name;
    private String contactNumber;
    private String email;

    public User(String login, String name, String contactNumber, String email) {
        this.login = login;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public abstract JPanel getPanel(boolean hasReservations, User activeUser, MainService mainService);
    public abstract boolean canExtendBooking(Booking booking);
    public abstract String getActionButtonLabel(Booking booking);
    public abstract Boolean getActionButtonEnabled(Booking booking);
    public abstract List<Booking> getBookings(List<Booking> bookings);
    public abstract void performBookingAction(Booking booking);
}
