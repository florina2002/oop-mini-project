import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RoomsAvailable extends JDialog {
    private JPanel availablePanel;
    private JButton backToRoomTypeButton;
    private JComboBox comboMeal;
    private JTextArea taDetails;
    private JSpinner spinnerRoom;
    private JButton goToFinalDetailsButton;

    public String roomType;
    public int GuestID;

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public RoomsAvailable(JFrame parent, String roomType, int GuestID) {
        super(parent);
        this.roomType = roomType;
        this.GuestID = GuestID;
        setTitle("RoomType");
        setContentPane(availablePanel);
        setMinimumSize(new Dimension(600, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.taDetails.setEditable(false);


        backToRoomTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Room room = new Room(null, GuestID);

            }
        });

        goToFinalDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Reservation reservation = new Reservation(null, GuestID, roomType, comboMeal.getSelectedItem().toString(), (int) spinnerRoom.getValue());
            }
        });

        roomsText();

        setVisible(true);
    }

    public void roomsText() {

        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT rooms.\"RoomNumber\" FROM \"Rooms\" rooms " +
                    "JOIN \"RoomType\" roomType ON  rooms.\"RoomTypeID\" = roomType.\"RoomTypeID\" " +
                    "WHERE rooms.\"RoomStatus\" = 'available' AND roomType.\"RoomType\" = '" + roomType + "'";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            String text = new String();

            while (resultSet.next()) {
                text = text + resultSet.getString("RoomNumber") + System.lineSeparator();
            }

            taDetails.setText(text);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}



