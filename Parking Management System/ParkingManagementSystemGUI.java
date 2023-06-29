import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ParkingManagementSystemGUI extends JFrame {
    private ParkingLot parkingLot;
    private JLabel availableSlotsLabel;
    private JTextArea resultTextArea;

    public ParkingManagementSystemGUI(int capacity) {
        this.parkingLot = new ParkingLot(capacity);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setTitle("Parking Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel plateNumberLabel = new JLabel("Plate Number:");
        JTextField plateNumberTextField = new JTextField(15);
        JLabel vehicleTypeLabel = new JLabel("Vehicle Type:");
        JComboBox<String> vehicleTypeComboBox = new JComboBox<>(new String[]{"Car", "Motorcycle", "Truck"});
        JButton parkButton = new JButton("Park Vehicle");
        JButton searchButton = new JButton("Search Vehicle");
        JButton removeButton = new JButton("Remove Vehicle");
        resultTextArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        // Create label for displaying available slots
        availableSlotsLabel = new JLabel("Available Slots: " + parkingLot.getAvailableSpaces());

        // Create panel and add components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(plateNumberLabel);
        inputPanel.add(plateNumberTextField);
        inputPanel.add(vehicleTypeLabel);
        inputPanel.add(vehicleTypeComboBox);
        inputPanel.add(availableSlotsLabel); // Add available slots label
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(parkButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(removeButton);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Set event listeners
        parkButton.addActionListener(new ParkButtonListener(plateNumberTextField, vehicleTypeComboBox));
        searchButton.addActionListener(new SearchButtonListener(plateNumberTextField));
        removeButton.addActionListener(new RemoveButtonListener(plateNumberTextField));

        // Set main panel to content pane
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    private void updateAvailableSlotsLabel() {
        int availableSlots = parkingLot.getAvailableSpaces();
        availableSlotsLabel.setText("Available Slots: " + availableSlots);
    }

    private class ParkButtonListener implements ActionListener {
        private JTextField plateNumberTextField;
        private JComboBox<String> vehicleTypeComboBox;

        public ParkButtonListener(JTextField plateNumberTextField, JComboBox<String> vehicleTypeComboBox) {
            this.plateNumberTextField = plateNumberTextField;
            this.vehicleTypeComboBox = vehicleTypeComboBox;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String plateNumber = plateNumberTextField.getText();
            String vehicleType = (String) vehicleTypeComboBox.getSelectedItem();

            Vehicle vehicle = new Vehicle(plateNumber, vehicleType);
            boolean parked = parkingLot.parkVehicle(vehicle);
            if (parked) {
                resultTextArea.append("Vehicle with plate number " + plateNumber + " and type " + vehicleType + " parked successfully.\n");
                updateAvailableSlotsLabel(); // Update available slots label
            } else {
                resultTextArea.append("Parking lot is full. Vehicle with plate number " + plateNumber + " and type " + vehicleType + " could not be parked.\n");
            }

            plateNumberTextField.setText("");
        }
    }

    private class SearchButtonListener implements ActionListener {
        private JTextField plateNumberTextField;

        public SearchButtonListener(JTextField plateNumberTextField) {
            this.plateNumberTextField = plateNumberTextField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String plateNumber = plateNumberTextField.getText();
            Vehicle vehicle = parkingLot.findVehicleByPlateNumber(plateNumber);
            if (vehicle != null) {
                resultTextArea.append("Vehicle with plate number " + plateNumber + " found. Type: " + vehicle.getVehicleType() + ", Slot: " + vehicle.getSlotNumber() + "\n");
            } else {
                resultTextArea.append("Vehicle with plate number " + plateNumber + " not found.\n");
            }

            plateNumberTextField.setText("");
        }
    }

    private class RemoveButtonListener implements ActionListener {
        private JTextField plateNumberTextField;

        public RemoveButtonListener(JTextField plateNumberTextField) {
            this.plateNumberTextField = plateNumberTextField;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String plateNumber = plateNumberTextField.getText();
            boolean removed = parkingLot.removeVehicle(plateNumber);
            if (removed) {
                resultTextArea.append("Vehicle with plate number " + plateNumber + " removed successfully.\n");
                updateAvailableSlotsLabel(); // Update available slots label
            } else {
                resultTextArea.append("Vehicle with plate number " + plateNumber + " not found.\n");
            }

            plateNumberTextField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ParkingManagementSystemGUI(10);
        });
    }
}


