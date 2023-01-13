import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Reservation extends JDialog {
    private JTextField tfDate;
    private JTextArea taMplanRoom;
    private JButton backToRoomsAvailableButton;
    private JSpinner spinnerNights;
    private JTextArea taPrice;
    private JButton reserveButton;
    private JPanel finalPanel;
    private JButton finalPriceButton;

    public int GuestID;
    public String roomType;
    public String mealPlan;
    public int roomNr;

    private HotelBooking hotelBooking;


    public Reservation(JFrame parent, int GuestID, String roomType, String mealPlan, int roomNr) {
        super(parent);
        this.GuestID = GuestID;
        this.roomNr = roomNr;
        this.roomType = roomType;
        this.mealPlan = mealPlan;
        setTitle("Reservation");
        setContentPane(finalPanel);
        setMinimumSize(new Dimension(550, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.taPrice.setEditable(false);
        this.taMplanRoom.setEditable(false);

        getDetails();

        hotelBooking = new HotelBooking();

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                insertTable();
                Bill bill = new Bill(null, hotelBooking);

            }
        });

        backToRoomsAvailableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RoomsAvailable roomsAvailable = new RoomsAvailable(null, roomType, GuestID);

            }
        });

        finalPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getPrice();
            }
        });

        setVisible(true);
    }

    public void getDetails() {
        taMplanRoom.setText("You chose room " + roomNr + ", room type " + roomType + ", with meal plan " + mealPlan + ".");
    }


    public void getPrice() {
        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();

            String sql = "SELECT \"NrPeople\", \"Price/night\" FROM \"RoomType\" " +
                    "WHERE \"RoomType\" = '" + roomType + "'";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            resultSet.next();

            int nrNights = (int) spinnerNights.getValue();
            int roomPrice = resultSet.getInt("Price/night");
            int nrPeople = resultSet.getInt("NrPeople");


            sql = "SELECT \"Price/Person/Night\", \"MealPlanID\" FROM \"MealPlan\" WHERE \"MealPlanType\" = '" + mealPlan + "'";

            stmt.execute(sql);
            ResultSet resultSet2 = stmt.getResultSet();

            resultSet2.next();
            int mealID = resultSet2.getInt("MealPlanID");
            int mealPrice = resultSet2.getInt("Price/Person/Night");

            int text;

            text = nrNights * (roomPrice + nrPeople * mealPrice);


            hotelBooking.setPrice(text);
            hotelBooking.setDateFrom(tfDate.getText());
            hotelBooking.setGuestID(GuestID);
            hotelBooking.setMealPlan(mealID);
            hotelBooking.setNrNights(nrNights);
            hotelBooking.setRoomID(roomNr);


            taPrice.setText(Integer.toString(text) + " Ron");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void insertTable() {
        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();

            String sql = "INSERT INTO \"HotelBooking\" VALUES(" + hotelBooking.guestID + ", '" +
                    hotelBooking.dateFrom + "', " + hotelBooking.roomID + ", " + hotelBooking.mealPlan + ", " +
                    +hotelBooking.nrNights + ", " + hotelBooking.price + ")";

            stmt.execute(sql);

            System.out.println("Confirmation of the reservation: ");
            System.out.println("                       Guest ID: " + hotelBooking.guestID);
            System.out.println("                      Date From: " + hotelBooking.dateFrom);
            System.out.println("                        Room Nr: " + hotelBooking.roomID);
            System.out.println("                   Meal Plan ID: " + hotelBooking.mealPlan);
            System.out.println("                  Nr. of nights: " + hotelBooking.nrNights);
            System.out.println("                          Price: " + hotelBooking.price + " RON");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
