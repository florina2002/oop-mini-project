import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class StaffRoom extends JDialog {
    private JPanel editPanel;
    private JTextArea taRooms;
    private JSpinner spinnerRoom;
    private JButton modifyRoomButton;
    private JComboBox comboAv;
    private JButton backButton;

    public StaffRoom(JFrame parent) {
        super(parent);
        setTitle("Modify Rooms");
        setContentPane(editPanel);
        setMinimumSize(new Dimension(550, 800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.taRooms.setEditable(false);

        getText();

        modifyRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getText();
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginStaff loginStaff = new LoginStaff(null);
            }
        });
        setVisible(true);
    }

    public void getText() {

        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM \"Rooms\"";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            int room = (int) spinnerRoom.getValue();
            String roomAv = comboAv.getSelectedItem().toString();

            String text = "";

            while (resultSet.next()) {
                if (resultSet.getInt("RoomNumber") > 9) {
                    text = text + "Room number: " + resultSet.getString("RoomNumber") + "       Room Availability: " + resultSet.getString("RoomStatus") + System.lineSeparator();
                } else
                    text = text + "Room number: " + resultSet.getString("RoomNumber") + "  " + "       Room Availability: " + resultSet.getString("RoomStatus") + System.lineSeparator();

            }

            taRooms.setText(text);


            String sql2 = "UPDATE \"Rooms\" SET \"RoomStatus\" = '" + roomAv + "' WHERE \"RoomNumber\" = " + room;

            stmt.execute(sql2);


            stmt.execute(sql);
            ResultSet resultSet2 = stmt.getResultSet();


            text = "";

            while (resultSet2.next()) {
                if (resultSet2.getInt("RoomNumber") > 9) {
                    text = text + "Room number: " + resultSet2.getString("RoomNumber") + "      Room Availability: " + resultSet2.getString("RoomStatus") + System.lineSeparator();
                } else
                    text = text + "Room number: " + resultSet2.getString("RoomNumber") + "  " + "      Room Availability: " + resultSet2.getString("RoomStatus") + System.lineSeparator();


            }

            taRooms.setText(text);


            stmt.close();
            conn.close();


        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
