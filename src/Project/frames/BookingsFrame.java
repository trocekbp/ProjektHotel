package Project.frames;

import Project.model.Booking;
import Project.model.User;
import Project.service.MainService;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;


public class BookingsFrame extends JFrame {
    private MainService mainService;

    private JTable clientBookingsTable;
    private JButton manageBookingButton;
    private JScrollPane scrollPane;
    private JPanel bookingsPanel;

    public BookingsFrame(User user, MainService mainService) {
        this.mainService = mainService;
        List<Booking> userBookings = mainService.getUserBookings(user);
        bookingsPanel = new JPanel();
        scrollPane = new JScrollPane();
        manageBookingButton = new JButton("Zarządzaj rezerwacją");
        manageBookingButton.setEnabled(false);
        manageBookingButton.addActionListener(e -> {
            JFrame bookingFrame = new BookingFrame(mainService, userBookings.get(clientBookingsTable.getSelectedRow()), user);
            bookingFrame.setVisible(true);
            dispose();
        });
        setBounds(500, 200, 1000, 500);
        String[] columns = {"Od", "Do", "Klient","Numer Pokoju", "Cena", "Potwierdzona", "Opłacona"};
        String[][] data = new String[userBookings.size()][columns.length];
        for (int i = 0 ; i < userBookings.size(); i ++){
            data[i][0] = userBookings.get(i).getDateFrom().toString();
            data[i][1] = userBookings.get(i).getDateTo().toString();
            data[i][2] = userBookings.get(i).getClientLogin();
            data[i][3] = userBookings.get(i).getRoomId().toString();
            data[i][4] = userBookings.get(i).getTotalPrice().toString();
            data[i][5] = userBookings.get(i).getConfirmed().toString();
            data[i][6] = userBookings.get(i).getPaid().toString();
        }
        clientBookingsTable = new JTable(data, columns);
        clientBookingsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel model = clientBookingsTable.getColumnModel();
        model.getColumn(0).setWidth(100);
        clientBookingsTable.getSelectionModel().addListSelectionListener(event -> {
            if (clientBookingsTable.getSelectedRow() > -1) {
                manageBookingButton.setEnabled(true);
            }
        });
        scrollPane.setViewportView(clientBookingsTable);
        scrollPane.getViewport().setPreferredSize(new Dimension(1000,100 * userBookings.size()));
        bookingsPanel.add(scrollPane);
        bookingsPanel.add(manageBookingButton);
        add(bookingsPanel);
    }
}
