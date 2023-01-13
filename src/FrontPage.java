import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrontPage extends JDialog {
    private JPanel panel1;
    private JPanel FrontPage;
    private JButton iAmAnUserButton;
    private JButton iAmAnAdminButton;
    private JButton cancelButton;


    public FrontPage(JFrame parent) {
        super(parent);
        setTitle("FrontPage");
        setContentPane(FrontPage);
        setMinimumSize(new Dimension(600, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

            }
        });

        iAmAnUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm = new LoginForm(null);
            }
        });

        iAmAnAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginStaff loginStaff = new LoginStaff(null);
            }
        });


        setVisible(true);


    }

    public static void main(String[] args) {
        FrontPage frontPage = new FrontPage(null);
    }
}
