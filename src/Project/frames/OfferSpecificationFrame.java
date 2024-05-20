package Project.frames;

import Project.model.User;
import Project.service.MainService;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;


public class OfferSpecificationFrame extends JFrame {
    private MainService mainService;

    private Date dateFrom = Date.from(Instant.ofEpochMilli(System.currentTimeMillis()));
    private Date dateTo = Date.from(Instant.ofEpochMilli(System.currentTimeMillis()));
    private JPanel offerSpecificationPanel;
    private JButton searchButton;

    private JSpinner dateFromSpinner;
    private JLabel dateFromLabel;
    private JSpinner dateToSpinner;
    private JLabel dateToLabel;

    private JSpinner priceMinSpinner;
    private JLabel priceMinLabel;
    private JSpinner priceMaxSpinner;
    private JLabel priceMaxLabel;
    private JSpinner guestsSpinner;
    private JLabel guestLabel;

    private JLabel standardLabel;
    private JComboBox<String> standardComboBox;

    private String standard = "Budget";
    private Double priceMin = 50.0;
    private Double priceMax = 75.0;
    private Double guests = 2.0;

    public OfferSpecificationFrame(User user, MainService mainService) {
        this.mainService = mainService;
        offerSpecificationPanel = new JPanel();
        setBounds(500, 200, 240, 350);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        searchButton = new JButton("Wyszukaj");

        dateFromLabel = new JLabel("Data od:");
        dateToLabel = new JLabel("Data do:");
        priceMinLabel = new JLabel("Cena od:");
        priceMaxLabel = new JLabel("Cena do:");
        standardLabel = new JLabel("Standard:");
        guestLabel = new JLabel("Goście:");

        dateFromLabel.setBounds(10, 10, 70, 25);
        dateToLabel.setBounds(10, 50, 70, 25);
        priceMinLabel.setBounds(10, 90, 70, 25);
        priceMaxLabel.setBounds(10, 130, 70, 25);
        standardLabel.setBounds(10, 170, 70, 25);
        guestLabel.setBounds(10, 210, 70, 25);

        SpinnerNumberModel priceMinSpinnerModel = new SpinnerNumberModel(50.0, 50.0, 10000.0, 25.0);
        SpinnerNumberModel priceMaxSpinnerModel = new SpinnerNumberModel(75.0, 75.0, 10000.0, 25.0);
        SpinnerNumberModel guestsSpinnerModel = new SpinnerNumberModel(1.0, 1.0, 20.0, 1.0);
        guestsSpinner = new JSpinner(guestsSpinnerModel);
        priceMinSpinner = new JSpinner(priceMinSpinnerModel);
        priceMaxSpinner = new JSpinner(priceMaxSpinnerModel);

        standardComboBox = new JComboBox<>();
        standardComboBox.addItem("Budget");
        standardComboBox.addItem("Normal");
        standardComboBox.addItem("Premium");

        SpinnerDateModel dateFromSpinnerModel = new SpinnerDateModel(dateFrom,
                Date.from(Instant.ofEpochSecond((1679330112))),
                Date.from(Instant.ofEpochSecond(1703954112)),
                Calendar.DAY_OF_YEAR);
        dateFromSpinner = new JSpinner(dateFromSpinnerModel);
        JSpinner.DateEditor dateFromEditor = new JSpinner.DateEditor(dateFromSpinner, "dd.MM.yyyy");
        dateFromEditor.getTextField().setEditable(false);
        dateFromSpinner.setEditor(dateFromEditor);

        SpinnerDateModel dateToSpinnerModel = new SpinnerDateModel(dateTo,
                Date.from(Instant.ofEpochSecond(1687090800)),
                Date.from(Instant.ofEpochSecond(1718713200)),
                Calendar.DAY_OF_YEAR);
        dateToSpinner = new JSpinner(dateToSpinnerModel);
        JSpinner.DateEditor dateToEditor = new JSpinner.DateEditor(dateToSpinner, "dd.MM.yyyy");
        dateToEditor.getTextField().setEditable(false);
        dateToSpinner.setEditor(dateToEditor);

        dateFromSpinner.setBounds(90, 10, 100, 25);
        dateToSpinner.setBounds(90, 50, 100, 25);
        priceMinSpinner.setBounds(90, 90, 100, 25);
        priceMaxSpinner.setBounds(90, 130, 100, 25);
        standardComboBox.setBounds(90, 170, 100, 25);
        guestsSpinner.setBounds(90, 210, 100, 25);

        searchButton.setBounds(60, 250, 100, 25);

        priceMinSpinner.addChangeListener(
                e -> priceMin = Double.valueOf(priceMinSpinner.getValue().toString())
        );

        priceMaxSpinner.addChangeListener(
                e -> priceMax = Double.valueOf(priceMaxSpinner.getValue().toString())
        );

        guestsSpinner.addChangeListener(
                e -> guests = (Double) guestsSpinner.getValue()
        );

        dateFromSpinner.addChangeListener(
                e -> {
                    dateFrom = (Date) dateFromSpinner.getValue();
                    if(dateTo.before(dateFrom)){
                        dateToEditor.getModel().setValue(dateFrom);
                    }
                }
        );

        dateToSpinner.addChangeListener(
                e -> {
                    dateTo = (Date) dateToSpinner.getValue();
                    if(dateFrom.after(dateTo)){
                        dateFromEditor.getModel().setValue(dateTo);
                    }
                }
        );

        standardComboBox.addActionListener(
                e -> standard = standardComboBox.getItemAt(standardComboBox.getSelectedIndex())
        );


        offerSpecificationPanel.add(dateFromLabel);
        offerSpecificationPanel.add(dateToLabel);
        offerSpecificationPanel.add(dateFromSpinner);
        offerSpecificationPanel.add(dateToSpinner);
        offerSpecificationPanel.add(priceMaxLabel);
        offerSpecificationPanel.add(priceMinLabel);
        offerSpecificationPanel.add(priceMaxSpinner);
        offerSpecificationPanel.add(priceMinSpinner);
        offerSpecificationPanel.add(standardComboBox);
        offerSpecificationPanel.add(standardLabel);
        offerSpecificationPanel.add(searchButton);
        offerSpecificationPanel.add(guestLabel);
        offerSpecificationPanel.add(guestsSpinner);

        offerSpecificationPanel.setLayout(null);

        searchButton.addActionListener(
                e -> {
                    JFrame offerListFrame = new OffersListFrame(mainService, dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), priceMin, priceMax, standard, guests.intValue());
                    offerListFrame.setVisible(true);
                    dispose();
                }
        );

        add(offerSpecificationPanel);
        setTitle("Specyfikacja");
    }
}
