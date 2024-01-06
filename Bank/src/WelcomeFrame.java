import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame implements ActionListener {
    private final Container container;
    private final JLabel welcomeLabel;
    private final JButton loginButton;
    private final JButton createButton;

    public WelcomeFrame(){
        setTitle("Welcome");
        setVisible(true);
        setLocation(10,10);
        setSize(1000,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Initialize a container, then we can add controls to the container
        container = getContentPane();
        welcomeLabel = new JLabel("FancyBank");
        loginButton = new JButton("Login");
        createButton = new JButton("Create your account");
        container.add(welcomeLabel);
        container.add(loginButton);
        container.add(createButton);
        setLayoutManager();
        setLocationAndSize();
        addActionEvent();

    }

    private void setLayoutManager() {
        container.setLayout(null);
    }

    private void addActionEvent() {
        loginButton.addActionListener(this);
        createButton.addActionListener(this);
    }

    private void setLocationAndSize() {
        welcomeLabel.setFont(new Font("Arial", Font.BOLD,40));
        welcomeLabel.setBounds(400, 200, 250, 60);
        loginButton.setBounds(250, 400, 200, 45);
        createButton.setBounds(600, 400, 200, 45);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            new LoginFrame();
        }
        else if(e.getSource() == createButton) {
            new SignUpFrame();
        }
    }

    public static void main(String[] args){
        FileHandler.readFiles();
        if (FileHandler.checkUser("Test") == null)
            FileHandler.addUser(new BankManager("Test","test",-1));
        FileHandler.writeFiles();
        WelcomeFrame frame=new WelcomeFrame();
        frame.setTitle("Welcome");
        frame.setLocation(10,10);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
