import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileFrame extends JFrame implements ActionListener {
    private String username;
    private final Container container;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel userLabel;
    private final JLabel idLabel;
    private final JLabel passwordLabel;
    private final JLabel firstName;
    private final JLabel lastName;
    private final JLabel uname;
    private final JLabel id;
    private final JLabel password;
    private final JLabel resetLabel;
    private final JButton returnButton;
    private final JButton resetPasswordButton;
    private final JTextField newPwdField;
    private Customer customer;
    private BankManager manager;


    public ProfileFrame(String username) {
        setTitle("User Profile");
        setVisible(true);
        setBounds(10,10,1000,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        container = getContentPane();
        this.username = username;
        //customer = (Customer) FileHandler.checkUser(username);
        customer = null;
        manager = null;
        User temp = FileHandler.checkUser(username);
        if (temp.getUserType() == "M") {
            manager = (BankManager) temp;
            firstNameLabel = new JLabel("First Name: " + manager.getName());
            lastNameLabel = new JLabel("Last Name: " + "Manager");
            userLabel = new JLabel("Username: " + username);
            idLabel = new JLabel("User Id: " + manager.getManagerId());
            passwordLabel = new JLabel("Password: " + manager.getUserPwd());
        }
        else {
            customer = (Customer) temp;
            firstNameLabel = new JLabel("First Name: " + customer.getFirstName());
            lastNameLabel = new JLabel("Last Name: " + customer.getLastName());
            userLabel = new JLabel("Username: " + username);
            idLabel = new JLabel("User Id: " + customer.getUserId());
            passwordLabel = new JLabel("Password: " + customer.getUserPwd());
        }
        /* 
        firstNameLabel = new JLabel("First Name: " + customer.getFirstName());
        lastNameLabel = new JLabel("Last Name: " + customer.getLastName());
        userLabel = new JLabel("Username: " + username);
        idLabel = new JLabel("User Id: " + customer.getUserId());
        passwordLabel = new JLabel("Password: " + customer.getUserPwd());
        */
        firstName = new JLabel();
        lastName = new JLabel();
        uname = new JLabel();
        id = new JLabel();
        password = new JLabel();
        resetLabel = new JLabel("Reset Password");
        returnButton = new JButton("Back");
        resetPasswordButton = new JButton("Reset Password");
        newPwdField = new JTextField();
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        idLabel.setBounds(300, 150, 100, 40);
        firstNameLabel.setBounds(300, 200, 100, 40);
        lastNameLabel.setBounds(300, 250, 100, 40);
        userLabel.setBounds(300, 300, 100, 40);
        passwordLabel.setBounds(300, 350, 100, 40);
        id.setBounds(550, 150, 200, 40);
        firstName.setBounds(550, 200, 200, 40);
        lastName.setBounds(550, 250, 200, 40);
        uname.setBounds(550, 300, 200, 40);
        password.setBounds(550, 350, 200, 40);
        resetLabel.setBounds(300, 400, 100, 40);
        newPwdField.setBounds(550, 400, 200, 30);
        returnButton.setBounds(600, 500, 100, 35);
        resetPasswordButton.setBounds(300, 500, 200, 35);
    }

    public void addComponentsToContainer() {
        container.add(firstNameLabel);
        container.add(lastNameLabel);
        container.add(userLabel);
        container.add(idLabel);
        container.add(passwordLabel);
        container.add(firstName);
        container.add(lastName);
        container.add(uname);
        container.add(id);
        container.add(password);
        container.add(resetPasswordButton);
        container.add(returnButton);
        container.add(resetLabel);
        container.add(newPwdField);
    }

    public void addActionEvent() {
        resetPasswordButton.addActionListener(this);
        returnButton.addActionListener(this);
    }

//TODO
    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of LOGIN button
        if (e.getSource() == resetPasswordButton) {
            String newPwd;
            newPwd = newPwdField.getText();
            if(newPwd.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter the new password");
            }
            else{
                //TODO: update Password in database
                if (customer == null) {
                    manager.setUserPwd(newPwd);
                }
                customer.setUserPwd(newPwd);
                FileHandler.writeFiles();
                JOptionPane.showMessageDialog(this, "Password Updated");
                //TODO: update Password in this frame
                //password.setText( need to show new password here );
                newPwdField.setText("");
            }
        }
        //back button
        if (e.getSource() == returnButton) {
            dispose();
        }
    }

    //Preview
//    public static void main(String[] args){
//        ProfileFrame frame=new ProfileFrame("1");
//        frame.setTitle("User Profile");
//        frame.setLocation(10,10);
//        frame.setSize(1000,800);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }


}