import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginStaff extends JDialog {
    private JPanel loginStaffPanel;
    private JTextField tfEmail;
    private JButton logInButton;
    private JPasswordField tfPassword;
    private JButton backButton;

    public LoginStaff(JFrame parent) {
        super(parent);
        setTitle("LoginStaff");
        setContentPane(loginStaffPanel);
        setMinimumSize(new Dimension(550, 550));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(tfPassword.getPassword());

                staff = getAuthenticatedUser(email, password);

                if (staff != null) {

                    System.out.println("Successful Authentication of: " + staff.firstName + " " + staff.lastName);
                    System.out.println("                       Email: " + staff.email);
                    System.out.println("                       Phone: " + staff.phone);
                    dispose();
                    StaffRoom staffRoom = new StaffRoom(null);
                } else {
                    JOptionPane.showMessageDialog(LoginStaff.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrontPage frontPage = new FrontPage(null);
            }
        });


        setVisible(true);


    }

    public Staff staff;

    private Staff getAuthenticatedUser(String email, String password) {
        Staff staff = null;

        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM \"Staff\" WHERE \"Email\"='" + email + "' AND \"Password\"='" + password + "'";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            if (resultSet.next()) {
                staff = new Staff();
                staff.firstName = resultSet.getString("FirstNameStaff");
                staff.lastName = resultSet.getString("LastNameStaff");
                staff.email = resultSet.getString("Email");
                staff.phone = resultSet.getString("PhoneNumber");
                staff.password = resultSet.getString("Password");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return staff;
    }

}
