import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Room extends JDialog {
    private JPanel roomPanel;
    private JComboBox tbRoomtype;
    private JButton showAvailableRoomsButton;
    private JButton backButton;

    public int GuestID;

    public Room(JFrame parent, int GuestID) {
        super(parent);
        this.GuestID = GuestID;
        setTitle("RoomType");
        setContentPane(roomPanel);
        setMinimumSize(new Dimension(600, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        showAvailableRoomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RoomsAvailable roomsAvailable = new RoomsAvailable(null, tbRoomtype.getSelectedItem().toString(), GuestID);
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

}
