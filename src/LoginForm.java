import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JTextField tfemail;
    private JPasswordField tfpassword;
    private JButton logInButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    private JButton signupButton;
    private JButton adminButton;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(550, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfemail.getText();
                String password = String.valueOf(tfpassword.getPassword());

                user = getAuthenticatedUser(email, password);

                if (user != null) {

                    System.out.println("Successful Authentication of: " + user.firstName + " " + user.lastName);
                    System.out.println("                       Email: " + user.email);
                    System.out.println("                       Phone: " + user.phone);
                    System.out.println("                     Address: " + user.address);
                    dispose();

                    Room room = new Room(null, user.id);
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FrontPage frontPage = new FrontPage(null);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignupForm signupForm = new SignupForm(null);
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginStaff loginStaff = new LoginStaff(null);
            }
        });


        setVisible(true);


    }

    public User user;

    private User getAuthenticatedUser(String email, String password) {
        User user = null;

        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM \"Guest\" WHERE \"Email\"='" + email + "' AND \"password\"='" + password + "'";

            stmt.execute(sql);
            ResultSet resultSet = stmt.getResultSet();

            if (resultSet.next()) {
                user = new User();
                user.firstName = resultSet.getString("FirstName");
                user.lastName = resultSet.getString("LastName");
                user.email = resultSet.getString("Email");
                user.phone = resultSet.getString("Phone");
                user.address = resultSet.getString("Address");
                user.password = resultSet.getString("password");
                user.id = resultSet.getInt("GuestID");
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return user;
    }

}