import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignupForm extends JDialog {
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JTextField tfPhone;
    private JTextField tfAdress;
    private JTextField tfEmail;
    private JPasswordField tfPassword;
    private JButton signUpButton;
    private JButton backButton;
    private JPanel SignupPanel;


    public SignupForm(JFrame parent) {
        super(parent);
        setTitle("Signup");
        setContentPane(SignupPanel);
        setMinimumSize(new Dimension(600, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signedupUser();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });

        setVisible(true);
    }

    public User user;

    private User signedupUser() {
        user = new User();
        user.setFirstName(tfFirstName.getText());
        user.setLastName(tfLastName.getText());
        user.setPhone(tfPhone.getText());
        user.setAddress(tfAdress.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(String.valueOf(tfPassword.getPassword()));


        final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        final String USERNAME = "postgres";
        final String PASSWORD = "inica2002";


        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);


            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO \"Guest\" VALUES(DEFAULT,'" + user.firstName.toString() + "','" + user.lastName.toString() + "','" + user.phone.toString() + "','" + user.address.toString() + "','" + user.email.toString() + "','" + user.password.toString() + "')";


            if (!user.firstName.toString().equals("") && !user.lastName.toString().equals("") && !user.phone.toString().equals("") && !user.address.toString().equals("") && !user.email.toString().equals("") && !user.password.toString().equals("")) {
                stmt.execute(sql);
            }


            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

        if (!user.firstName.toString().equals("") && !user.lastName.toString().equals("") && !user.phone.toString().equals("") && !user.address.toString().equals("") && !user.email.toString().equals("") && !user.password.toString().equals("")) {

            System.out.println("Successful Registration of: " + user.firstName + " " + user.lastName);
            System.out.println("                     Email: " + user.email);
            System.out.println("                     Phone: " + user.phone);
            System.out.println("                   Address: " + user.address);
            dispose();

            LoginForm loginForm = new LoginForm(null);

        } else JOptionPane.showMessageDialog(SignupForm.this,
                "Missing data. Enter data again.",
                "Try again",
                JOptionPane.ERROR_MESSAGE);


        return user;
    }

}
