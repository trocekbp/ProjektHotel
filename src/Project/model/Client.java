package Project.model;

import Project.frames.BookingsFrame;
import Project.frames.OfferSpecificationFrame;
import Project.service.MainService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class Client extends User {

    public Client(String login, String name, String contactNumber, String email) {
        super(login, name, contactNumber, email);
    }

    @Override
    public JPanel getPanel(boolean hasReservations, User activeUser, MainService mainService) {
        JPanel panel = new JPanel();

        JButton offerButton = new JButton("Wyświetl ofertę");
        offerButton.addActionListener(
                e -> {
                    JFrame offerFrame = new OfferSpecificationFrame(activeUser, mainService);
                    offerFrame.setVisible(true);
                }
        );

        panel.add(offerButton);
        if(hasReservations) {
            JButton bookingsManagement = new JButton("Zarządzaj rezerwacjami");
            bookingsManagement.addActionListener(                                               //dodajemy rezerwacje
                    e -> {
                        JFrame clientFrame = new BookingsFrame(activeUser, mainService);
                        clientFrame.setVisible(true);
                    }
            );
            panel.add(bookingsManagement);
        }
        return panel;
    }

    @Override
    public boolean canExtendBooking(Booking booking) {
        return booking.getClientLogin().equals(getLogin());
    }

    @Override
    public String getActionButtonLabel(Booking booking) {
        if(booking.getClientLogin().equals(getLogin()))
            return "Opłać";
        else return "Zarezerwuj";
    }

    @Override
    public Boolean getActionButtonEnabled(Booking booking) {
        if(booking.getClientLogin().equals(getLogin()))
            return !booking.getPaid();
        else return true;
    }

    @Override
    public List<Booking> getBookings(List<Booking> bookings) {
        return bookings.stream().filter(it -> it.getClientLogin().equals(getLogin())).collect(Collectors.toList());
    }

    @Override
    public void performBookingAction(Booking booking) {
        booking.setPaid(true);
    }

}