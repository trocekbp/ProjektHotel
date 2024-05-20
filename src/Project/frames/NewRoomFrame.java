package Project.frames;

import Project.service.MainService;

import javax.swing.*;

public class NewRoomFrame extends JFrame {

    private MainService mainService;

    private JPanel roomPanel;

    private JLabel guestsLabel;
    private JSpinner spinnerGuests;
    private JLabel floorLabel;
    private JSpinner spinnerFloor;
    private JLabel standardLabel;
    private JComboBox<String> standardComboBox;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel priceLabel;
    private JSpinner spinnerPrice;

    private JButton saveButton;

    public NewRoomFrame(MainService mainService) {
        this.mainService = mainService;

        guestsLabel = new JLabel("Liczba gości:");
        floorLabel = new JLabel("Piętro:");
        standardLabel = new JLabel("Standard:");
        priceLabel = new JLabel("Cena za noc:");
        nameLabel = new JLabel("Nazwa:");
        roomPanel = new JPanel();

        SpinnerNumberModel guestsSpinnerModel = new SpinnerNumberModel(2, 2, 10, 1);
        SpinnerNumberModel floorSpinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
        SpinnerNumberModel priceSpinnerModel = new SpinnerNumberModel(50.0, 50.0, 10000.0, 25.0);
        spinnerGuests = new JSpinner(guestsSpinnerModel);
        spinnerFloor = new JSpinner(floorSpinnerModel);
        spinnerPrice = new JSpinner(priceSpinnerModel);
        nameField = new JTextField();

        standardComboBox = new JComboBox<>();
        standardComboBox.addItem("Budget");
        standardComboBox.addItem("Normal");
        standardComboBox.addItem("Premium");

        guestsLabel.setBounds(10, 10, 100, 25);
        floorLabel.setBounds(10, 50, 100, 25);
        standardLabel.setBounds(10, 90, 100, 25);
        priceLabel.setBounds(10, 130, 100, 25);
        nameLabel.setBounds(10, 170, 100, 25);

        spinnerGuests.setBounds(150, 10, 50, 25);
        spinnerFloor.setBounds(150, 50, 50, 25);
        standardComboBox.setBounds(150, 90, 90, 25);
        spinnerPrice.setBounds(150, 130, 50, 25);
        nameField.setBounds(150, 170, 200, 25);

        saveButton = new JButton("Zapisz");
        saveButton.setBounds(100, 270, 200, 25);

        saveButton.addActionListener(
                e -> {
                    mainService.createNewRoom(
                            Integer.parseInt(spinnerGuests.getValue().toString()),
                            Integer.parseInt(spinnerFloor.getValue().toString()),
                            standardComboBox.getItemAt(standardComboBox.getSelectedIndex()),
                            nameField.getText(),
                            Double.parseDouble(spinnerPrice.getValue().toString())
                    );
                    dispose();
                }
        );

        roomPanel.add(guestsLabel);
        roomPanel.add(floorLabel);
        roomPanel.add(standardLabel);
        roomPanel.add(priceLabel);
        roomPanel.add(nameLabel);

        roomPanel.add(spinnerGuests);
        roomPanel.add(spinnerFloor);
        roomPanel.add(spinnerPrice);
        roomPanel.add(standardComboBox);
        roomPanel.add(nameField);
        roomPanel.add(saveButton);
        roomPanel.setLayout(null);


        add(roomPanel);
        setTitle("Dodaj pokój");
        setBounds(300, 90, 400, 400);
        setResizable(false);
    }

}