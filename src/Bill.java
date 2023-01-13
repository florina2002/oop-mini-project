import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bill extends JDialog {
    private JTextArea taBill;
    private JButton exitButton;
    private JPanel billPanel;

    private HotelBooking hotelBooking;

    public Bill(JFrame parent, HotelBooking hotelBooking) {

        super(parent);

        this.hotelBooking = hotelBooking;
        setTitle("Login");
        setContentPane(billPanel);
        setMinimumSize(new Dimension(550, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.taBill.setEditable(false);

        editBill();


        setVisible(true);

    }

    public void editBill() {
        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();

            String sql = "SELECT \"FirstName\", \"LastName\", \"Phone\", \"Address\", \"Email\" FROM \"Guest\" " +
                    "WHERE \"GuestID\" = '" + hotelBooking.guestID + "'";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            resultSet.next();

            User user = new User();
            user.firstName = resultSet.getString("FirstName");
            user.lastName = resultSet.getString("LastName");
            user.phone = resultSet.getString("Phone");
            user.address = resultSet.getString("Address");
            user.email = resultSet.getString("Email");



            sql = "SELECT \"MealPlanType\" FROM \"MealPlan\" WHERE \"MealPlanID\" = '" + hotelBooking.mealPlan + "'";

            stmt.execute(sql);
            ResultSet resultSet2 = stmt.getResultSet();

            resultSet2.next();
            String mealPlanType = resultSet2.getString("MealPlanType");


            sql = "SELECT type.\"RoomType\" FROM \"RoomType\" type JOIN \"Rooms\" rooms ON type.\"RoomTypeID\" = rooms.\"RoomTypeID\" WHERE \"RoomNumber\" = '" + hotelBooking.roomID + "'";

            stmt.execute(sql);
            ResultSet resultSet3 = stmt.getResultSet();

            resultSet3.next();
            String roomType = resultSet3.getString("RoomType");

            String text = "Reservation information:" + "\n" +
                    "Name: " + user.firstName + " " + user.lastName + "\n" +
                    "Phone: " + user.phone + "\n" +
                    "Address: " + user.address + "\n" +
                    "Email: " + user.email + "\n" +
                    "Room Number: " + hotelBooking.roomID + "\n" +
                    "Room Type: " + roomType + "\n" +
                    "Meal Plan: " + mealPlanType + "\n" +
                    "Date From: " + hotelBooking.dateFrom + "\n" +
                    "Number of nights: " + hotelBooking.nrNights + "\n\n" +
                    "Final Price: " + hotelBooking.price + " Ron";
            taBill.setText(text);

        } catch (SQLException e) {
            System.out.println(e);
        }


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrontPage frontPage = new FrontPage(null);
            }
        });
    }
}
