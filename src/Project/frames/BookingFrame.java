package Project.frames;

import Project.model.Booking;
import Project.model.User;
import Project.service.MainService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BookingFrame extends JFrame {
    private MainService mainService;
    private Booking booking;
    private LocalDate tempStayDate;

    private JPanel panel;

    private JButton extendBookingTimeButton;
    private JButton actionButton;
    private JButton finishButton;

    private JLabel labelFrom;
    private JLabel dateFromValueLabel;
    private JLabel labelTo;
    private JLabel labelToValue;
    private JLabel userLabel;
    private JLabel userNameLabel;
    private JLabel priceLabel;
    private JLabel priceValue;
    private JLabel labelPaid;
    private JCheckBox jCheckBoxPaid;
    private JLabel labelConfirmed;
    private JCheckBox jCheckBoxConfirmed;
    private JCheckBox extendStayCheckbox;
    private JSpinner extendStayField;

    public BookingFrame(MainService mainService, Booking booking, User currentUser) throws HeadlessException {
        this.booking = booking;
        this.mainService = mainService;
        SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 1000, 1);
        extendBookingTimeButton = new JButton("Przedłuż pobyt");
        actionButton = new JButton(currentUser.getActionButtonLabel(booking));
        actionButton.setEnabled(currentUser.getActionButtonEnabled(booking));
        tempStayDate = booking.getDateTo();

        panel = new JPanel();
        labelFrom = new JLabel("Od");
        dateFromValueLabel = new JLabel();
        labelTo = new JLabel("Do");
        labelToValue = new JLabel();
        userLabel = new JLabel("Użytkownik");
        userNameLabel = new JLabel();
        priceLabel = new JLabel("Cena");
        priceValue = new JLabel();
        labelPaid = new JLabel("Opłacono");
        jCheckBoxPaid = new JCheckBox();
        labelConfirmed = new JLabel("Zatwierdzono");
        jCheckBoxConfirmed = new JCheckBox();
        extendStayCheckbox = new JCheckBox();
        extendStayField = new JSpinner(model);
        finishButton = new JButton("Powrót");

        labelFrom.setBounds(20, 0, 100, 25);
        labelTo.setBounds(20, 50, 100, 25);
        userLabel.setBounds(20, 100, 100, 25);
        priceLabel.setBounds(20, 150, 100, 25);
        labelPaid.setBounds(20, 200, 100, 25);
        labelConfirmed.setBounds(20, 250, 100, 25);


        dateFromValueLabel.setBounds(120, 0, 200, 25);
        labelToValue.setBounds(120, 50, 100, 25);
        extendStayCheckbox.setBounds(230, 50, 150, 25);
        extendStayField.setBounds(400, 50, 50, 25);
        userNameLabel.setBounds(120, 100, 200, 25);
        priceValue.setBounds(120, 150, 200, 25);
        jCheckBoxPaid.setBounds(120, 200, 200, 25);
        jCheckBoxConfirmed.setBounds(120, 250, 200, 25);

        extendBookingTimeButton.setBounds(60, 350, 200, 25);
        actionButton.setBounds(270, 350, 200, 25);
        finishButton.setBounds(135, 400, 200, 25);

        panel.add(labelFrom);
        panel.add(dateFromValueLabel);
        panel.add(labelTo);
        panel.add(labelToValue);
        panel.add(userLabel);
        panel.add(userNameLabel);
        panel.add(priceLabel);
        panel.add(priceValue);
        panel.add(labelPaid);
        panel.add(jCheckBoxPaid);
        panel.add(labelConfirmed);
        panel.add(jCheckBoxConfirmed);
        panel.add(extendBookingTimeButton);
        panel.add(actionButton);
        panel.add(extendStayCheckbox);
        panel.add(extendStayField);
        panel.add(finishButton);

        actionButton.addActionListener(
                e -> {
                    currentUser.performBookingAction(booking);
                    mainService.saveBookings();
                    refreshControls();
                    actionButton.setEnabled(currentUser.getActionButtonEnabled(booking));
                }
        );

        finishButton.addActionListener(
                e -> {
                    JFrame newRoomFrame = new BookingsFrame(currentUser, mainService);
                    newRoomFrame.setVisible(true);
                    dispose();
                }
        );

        extendStayCheckbox.setVisible(currentUser.canExtendBooking(booking));
        extendStayField.setVisible(currentUser.canExtendBooking(booking));
        extendStayField.setEnabled(false);
        extendStayCheckbox.setText("Przedłużyć pobyt?");
        userNameLabel.setText(booking.getClientLogin());
        dateFromValueLabel.setText(booking.getDateFrom().toString());
        jCheckBoxPaid.setEnabled(false);
        jCheckBoxPaid.setSelected(booking.getPaid());
        jCheckBoxConfirmed.setEnabled(false);
        jCheckBoxConfirmed.setSelected(booking.getConfirmed());
        priceValue.setText(booking.getTotalPrice().toString() + " zł");
        labelToValue.setText(booking.getDateTo().toString());
        extendBookingTimeButton.setVisible(currentUser.canExtendBooking(booking));
        extendBookingTimeButton.setEnabled(Integer.parseInt(extendStayField.getValue().toString()) > 0);

        extendBookingTimeButton.addActionListener(
                e -> {
                    mainService.extendBooking(booking.getBookingId(), booking.getRoomId(), currentUser, Long.parseLong(extendStayField.getValue().toString()));
                    extendBookingTimeButton.setEnabled(false);
                    extendStayCheckbox.setSelected(false);
                    extendStayField.setValue(0);
                    labelToValue.setText(booking.getDateTo().toString());
                }
        );

        extendStayCheckbox.addActionListener(
                e -> extendStayField.setEnabled(extendStayCheckbox.isSelected())
        );
        extendStayField.addChangeListener(
                e -> {
                    tempStayDate = booking.getDateTo().plusDays(Long.parseLong(extendStayField.getValue().toString()));
                    labelToValue.setText(tempStayDate.toString());
                    extendBookingTimeButton.setEnabled(Integer.parseInt(extendStayField.getValue().toString()) > 0);
                });


        panel.setLayout(null);


        add(panel);
        setTitle("Rezerwacja nr " + booking.getBookingId());
        setBounds(500, 200, 500, 500);

    }

    private void refreshControls() {
        jCheckBoxConfirmed.setSelected(booking.getConfirmed());
        jCheckBoxPaid.setSelected(booking.getPaid());
    }

}
