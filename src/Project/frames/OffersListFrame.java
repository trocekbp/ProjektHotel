package Project.frames;

import Project.model.Room;
import Project.service.MainService;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


public class OffersListFrame extends JFrame {
    private MainService mainService;

    private JTable clientBookingsTable;
    private JButton createBookingButton;
    private JScrollPane scrollPane;
    private JPanel bookingsPanel;


    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Double priceFrom;
    private Double priceTo;
    private String standard;

    private Room selectedRoom;

    public OffersListFrame(MainService mainService, LocalDate dateFrom, LocalDate dateTo, Double priceFrom, Double priceTo, String standard, int guests) {
        this.mainService = mainService;

        System.out.println("Parametry wyszukiwania: od: " + dateFrom + " do: " + dateTo + " cenaOd " + priceFrom + " cena do " + priceTo + " standard:" + standard + " goście: " + guests);
        List<Room> roomsByCriteria = mainService.getRoomsByCriteria(dateFrom, dateTo, priceFrom, priceTo, standard, guests);
        bookingsPanel = new JPanel();
        scrollPane = new JScrollPane();
        createBookingButton = new JButton("Zarezerwuj");
        createBookingButton.setEnabled(false);
        int daysDifference = (int) DAYS.between(dateFrom, dateTo);
        if(daysDifference == 0)
            daysDifference = 1;
        int finalDaysDifference = daysDifference;
        createBookingButton.addActionListener(e -> {
            double pricePerNight = selectedRoom.getPricePerNight();
            mainService.createBooking(dateFrom, dateTo, mainService.getActiveUser(), pricePerNight * finalDaysDifference, selectedRoom.getRoomId());
            dispose();
        });
        setBounds(500, 200, 1000, 500);
        String[] columns = {"Nazwa", "Standard", "Piętro", "Miejsca", "Cena za noc", "Cena za pobyt"};
        String[][] data = new String[roomsByCriteria.size()][columns.length];
        for (int i = 0 ; i < roomsByCriteria.size(); i ++){
            data[i][0] = roomsByCriteria.get(i).getName();
            data[i][1] = roomsByCriteria.get(i).getStandard();
            data[i][2] = String.valueOf(roomsByCriteria.get(i).getFloor());
            data[i][3] = String.valueOf(roomsByCriteria.get(i).getSize());
            data[i][4] = String.valueOf(roomsByCriteria.get(i).getPricePerNight());
            data[i][5] = String.valueOf(roomsByCriteria.get(i).getPricePerNight() * (daysDifference));
        }
        clientBookingsTable = new JTable(data, columns);
        clientBookingsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        TableColumnModel model = clientBookingsTable.getColumnModel();
        model.getColumn(0).setWidth(100);
        clientBookingsTable.getSelectionModel().addListSelectionListener(event -> {
            if (clientBookingsTable.getSelectedRow() > -1) {
                createBookingButton.setEnabled(true);
                selectedRoom = roomsByCriteria.get(clientBookingsTable.getSelectedRow());
            }
        });
        scrollPane.setViewportView(clientBookingsTable);
        scrollPane.getViewport().setPreferredSize(new Dimension(1000,100 * roomsByCriteria.size()));
        bookingsPanel.add(scrollPane);
        bookingsPanel.add(createBookingButton);
        add(bookingsPanel);
    }
}
