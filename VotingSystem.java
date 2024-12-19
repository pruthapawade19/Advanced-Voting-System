import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

class Home extends JFrame implements ActionListener {
    private JButton btnVote;
    private JButton btnRegister;
    private JButton btnResult;
    private JButton btnExit;

    public Home(String title) {
        super(title);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnVote = new JButton("Vote");
        btnRegister = new JButton("Register");
        btnResult = new JButton("Result");
        btnExit = new JButton("Exit");

        btnVote.addActionListener(this);
        btnRegister.addActionListener(this);
        btnResult.addActionListener(this);
        btnExit.addActionListener(this);

        btnVote.setFont(new Font("Arial", Font.BOLD, 20));
        btnRegister.setFont(new Font("Arial", Font.BOLD, 20));
        btnResult.setFont(new Font("Arial", Font.BOLD, 20));
        btnExit.setFont(new Font("Arial", Font.BOLD, 20));

        btnVote.setBounds(450, 200, 200, 40);
        btnRegister.setBounds(450, 270, 200, 40);
        btnResult.setBounds(450, 340, 200, 40);
        btnExit.setBounds(770, 535, 100, 40);

        add(btnVote);
        add(btnRegister);
        add(btnResult);
        add(btnExit);

        generateBackground();
        setSize(900, 623);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void generateBackground() {
        // Generating the Background
        ImageIcon imgBackground = new ImageIcon("./img/background.jpg");
        ImageIcon imgLogo = new ImageIcon("./img/logo.png");
        ImageIcon imgVote = new ImageIcon("./img/vote.png");

        JLabel lblLogo = new JLabel(imgLogo);
        JLabel lblBackground = new JLabel(imgBackground);
        JLabel lblVote = new JLabel(imgVote);

        lblBackground.setBounds(0, 0, 900, 623);
        lblLogo.setBounds(10, 20, 368, 80);
        lblVote.setBounds(7, 50, 512, 512);

        add(lblVote);
        add(lblLogo);
        add(lblBackground);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVote) {
            new Login();
            dispose();
        }
        if (e.getSource() == btnRegister) {
            new Register();
            dispose();
        }
        if (e.getSource() == btnResult) {
            new Result();
            dispose();
        }
        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }
}

class Login extends JFrame implements ActionListener {
    private JLabel lblCardNo = new JLabel("Card No");
    private JLabel lblPassword = new JLabel("Password");
    private JTextField txtCardNo = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private JButton btnLogin = new JButton("Login");
    private JButton btnBack = new JButton("Back");

    public Login() {
        super("Login");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblVote = new JLabel("Voting details");

        lblVote.setFont(new Font("Arial", Font.BOLD, 28));
        lblCardNo.setFont(new Font("Arial", Font.PLAIN, 20));
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        txtCardNo.setFont(new Font("Arial", Font.PLAIN, 20));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 20));
        btnBack.setFont(new Font("Arial", Font.PLAIN, 20));

        lblVote.setBounds(350, 40, 200, 40);
        lblCardNo.setBounds(480, 200, 100, 30);
        lblPassword.setBounds(480, 270, 100, 30);
        txtCardNo.setBounds(600, 200, 200, 30);
        txtPassword.setBounds(600, 270, 200, 30);
        btnLogin.setBounds(585, 320, 100, 30);
        btnBack.setBounds(770, 535, 100, 35);

        add(lblVote);
        add(lblCardNo);
        add(lblPassword);
        add(txtCardNo);
        add(txtPassword);
        add(btnLogin);
        add(btnBack);

        btnLogin.addActionListener(this);
        btnBack.addActionListener(this);
        generateBackground();
        setSize(900, 623);
        setLocationRelativeTo(null);  //window pop up from center of the screen
        setVisible(true);
    }

    public void generateBackground() {
        // Generating the Background
        ImageIcon imgBackground = new ImageIcon("./img/background.jpg");
        ImageIcon imgLogin = new ImageIcon("./img/login.png");

        JLabel lblBackground = new JLabel(imgBackground);
        JLabel lblLogin = new JLabel(imgLogin);

        lblBackground.setBounds(0, 0, 900, 623);
        lblLogin.setBounds(0, 50, 561, 561);

        add(lblLogin);
        add(lblBackground);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String cardNo = txtCardNo.getText();
            String password = txtPassword.getText();

            String url = "jdbc:mysql://localhost:3306/voting_system";
            String username = "root";
            String p = "";
            String query = "SELECT * FROM register WHERE cardno = ? AND password = ?";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, username, p);
                PreparedStatement ps = con.prepareStatement(query);

                ps.setString(1, cardNo);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String status = rs.getString("status");
                    if (status.equals("voted")) {
                        JOptionPane.showMessageDialog(null, "You have already voted");
                        
                    } else {
                        new Vote(Integer.parseInt(cardNo));
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Card No or Password");
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }
        if (e.getSource() == btnBack) {
            new Home("Home");
            dispose();
        }
    }
}

class Vote extends JFrame implements ActionListener {
    private JPanel panPrutha = new JPanel();
    private JPanel panMangesh = new JPanel();
    private JPanel panShruti = new JPanel();
    private JPanel panShivam = new JPanel();
    private JPanel panMansi = new JPanel();
    private JButton btnPrutha = new JButton("Vote");
    private JButton btnMangesh = new JButton("Vote");
    private JButton btnShruti = new JButton("Vote");
    private JButton btnShivam = new JButton("Vote");
    private JButton btnMansi = new JButton("Vote");
    private JButton btnBack = new JButton("Back");
    private JLabel l;
    private ImageIcon img;
    private JLabel lblImg;
    private int cardNo;

    public Vote(int card_no) {
        super("Vote");
        this.cardNo = card_no;
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblVote = new JLabel("Vote");
        lblVote.setFont(new Font("Arial", Font.BOLD, 28));
        lblVote.setBounds(410, 35, 100, 40);
        add(lblVote);

        panPrutha.setLayout(new GridLayout());
        l = new JLabel("Prutha Pawade");
        l.setFont(new Font("Arial", Font.PLAIN, 19));
        btnPrutha.setFont(new Font("Arial", Font.PLAIN, 20));
        img = new ImageIcon("./img/shruti.png");
        lblImg = new JLabel(img);
        panPrutha.add(lblImg);
        panPrutha.add(l);
        panPrutha.add(btnPrutha);
        
        panMangesh.setLayout(new GridLayout());
        l = new JLabel("Mangesh Wannerkar");
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        btnMangesh.setFont(new Font("Arial", Font.PLAIN, 20));
        img = new ImageIcon("./img/mangesh.png");
        lblImg = new JLabel(img);
        panMangesh.add(lblImg);
        panMangesh.add(l);
        panMangesh.add(btnMangesh);

        panShruti.setLayout(new GridLayout());
        l = new JLabel("Shruti Dhote");
        l.setFont(new Font("Arial", Font.PLAIN, 19));
        btnShruti.setFont(new Font("Arial", Font.PLAIN, 20));
        img = new ImageIcon("./img/prutha.png");
        lblImg = new JLabel(img);
        panShruti.add(lblImg);
        panShruti.add(l);
        panShruti.add(btnShruti);

        panShivam.setLayout(new GridLayout());
        l = new JLabel("Shivam Awagan");
        l.setFont(new Font("Arial", Font.PLAIN, 18));
        btnShivam.setFont(new Font("Arial", Font.PLAIN, 20));
        img = new ImageIcon("./img/shivam.png");
        lblImg = new JLabel(img);
        panShivam.add(lblImg);
        panShivam.add(l);
        panShivam.add(btnShivam);

        panMansi.setLayout(new GridLayout());
        l = new JLabel("Mansi Kariye");
        l.setFont(new Font("Arial", Font.PLAIN, 20));
        btnMansi.setFont(new Font("Arial", Font.PLAIN, 20));
        img = new ImageIcon("./img/mansi.png");
        lblImg = new JLabel(img);
        panMansi.add(lblImg);
        panMansi.add(l);
        panMansi.add(btnMansi);

        panPrutha.setBounds(460, 80, 410, 80);
        panMangesh.setBounds(460, 170, 410, 80);
        panShruti.setBounds(460, 260, 410, 80);
        panShivam.setBounds(460, 350, 410, 80);
        panMansi.setBounds(460, 440, 410, 80);
        btnBack.setBounds(460, 530, 410, 40);

        btnPrutha.addActionListener(this);
        btnMangesh.addActionListener(this);
        btnShruti.addActionListener(this);
        btnShivam.addActionListener(this);
        btnMansi.addActionListener(this);
        btnBack.addActionListener(this);

        add(panPrutha);
        add(panMangesh);
        add(panShruti);
        add(panShivam);
        add(panMansi);
        add(btnBack);

        generateBackground();
        setSize(900, 623);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void generateBackground() {
        // Generating the Background
        ImageIcon imgBackground = new ImageIcon("./img/background.jpg");
        ImageIcon imgVote = new ImageIcon("./img/vote.png");

        JLabel lblBackground = new JLabel(imgBackground);
        JLabel lblVote = new JLabel(imgVote);

        lblBackground.setBounds(0, 0, 900, 623);
        lblVote.setBounds(0, 0, 561, 561);

        add(lblVote);
        add(lblBackground);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            new Home("Home");
            dispose();
            return;
        }
        int confomation = JOptionPane.showConfirmDialog(null, "Are you sure you want to vote?");
        if (confomation == JOptionPane.YES_OPTION) {
            try {
                String url = "jdbc:mysql://localhost:3306/voting_system";
                String username = "root";
                String password = "";
                String query = "UPDATE register SET status = 'voted' WHERE cardno = ?";
                Connection con = DriverManager.getConnection(url, username, password);
                PreparedStatement st = con.prepareStatement(query);
                st.setInt(1, cardNo);
                st.executeUpdate();

                if (e.getSource() == btnPrutha) {
                    query = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = 1";
                    st = con.prepareStatement(query);
                    st.executeUpdate();
                    query = "insert into votes values(?, 1)";
                    st = con.prepareStatement(query);
                    st.setInt(1, cardNo);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have voted for Prutha Pawade");
                }
                if (e.getSource() == btnMangesh) {
                    query = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = 2";
                    st = con.prepareStatement(query);
                    st.executeUpdate();
                    query = "insert into votes values(?, 2)";
                    st = con.prepareStatement(query);
                    st.setInt(1, cardNo);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have voted for Mangesh Wannerkar");
                }
                if (e.getSource() == btnShruti) {
                    query = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = 3";
                    st = con.prepareStatement(query);
                    st.executeUpdate();
                    query = "insert into votes values(?, 3)";
                    st = con.prepareStatement(query);
                    st.setInt(1, cardNo);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have voted for Shruti Dhote");
                }
                if (e.getSource() == btnShivam) {
                    query = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = 4";
                    st = con.prepareStatement(query);
                    st.executeUpdate();
                    query = "insert into votes values(?, 4)";
                    st = con.prepareStatement(query);
                    st.setInt(1, cardNo);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have voted for Shivam Awagan");
                }
                if (e.getSource() == btnMansi) {
                    query = "UPDATE candidates SET votes = votes + 1 WHERE candidate_id = 5";
                    st = con.prepareStatement(query);
                    st.executeUpdate();
                    query = "insert into votes values(?, 5)";
                    st = con.prepareStatement(query);
                    st.setInt(1, cardNo);
                    st.executeUpdate();
                    JOptionPane.showMessageDialog(null, "You have voted for Mansi Kariye");
                }
                new Home("Home");
                dispose();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}

class Register extends JFrame implements ActionListener {
    private JLabel lblRegister = new JLabel("Registration Form");
    private JLabel lblName = new JLabel("Name");
    private JLabel lblEmail = new JLabel("Email");
    private JLabel lblPhone = new JLabel("Phone");
    private JLabel lblGender = new JLabel("Gender");
    private JLabel lblAge = new JLabel("Age");
    private JLabel lblAadhaarNo = new JLabel("Aadhaar No.");
    private JLabel lblPanNo = new JLabel("Pan No.");
    private JLabel lblPassword = new JLabel("Set Password");
    private JButton btnRegister = new JButton("Register");
    private JButton btnBack = new JButton("Back");
    private JTextField txtName = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JRadioButton rbMale = new JRadioButton("Male");
    private JRadioButton rbFemale = new JRadioButton("Female");
    private JTextField txtAge = new JTextField();
    private JTextField txtAadhaarNo = new JTextField();
    private JTextField txtPanNo = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();

    public Register() {
        super("Register");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font font = new Font("Arial", Font.PLAIN, 20);

        ButtonGroup bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);

        lblRegister.setFont(new Font("Arial", Font.BOLD, 28));
        lblName.setFont(font);
        lblEmail.setFont(font);
        lblPhone.setFont(font);
        lblGender.setFont(font);
        lblAge.setFont(font);
        lblAadhaarNo.setFont(font);
        lblPanNo.setFont(font);
        lblPassword.setFont(font);
        btnRegister.setFont(font);
        btnBack.setFont(font);
        txtName.setFont(font);
        txtEmail.setFont(font);
        txtPhone.setFont(font);
        rbMale.setFont(font);
        rbFemale.setFont(font);
        txtAge.setFont(font);
        txtAadhaarNo.setFont(font);
        txtPanNo.setFont(font);
        txtPassword.setFont(font);

        lblRegister.setBounds(325, 40, 250, 40);
        lblName.setBounds(250, 130, 100, 20);
        lblEmail.setBounds(250, 180, 100, 20);
        lblPhone.setBounds(250, 230, 100, 20);
        lblGender.setBounds(250, 280, 100, 20);
        lblAge.setBounds(250, 330, 150, 20);
        lblAadhaarNo.setBounds(250, 380, 150, 20);
        lblPanNo.setBounds(250, 430, 150, 20);
        lblPassword.setBounds(250, 480, 150, 20);
        btnRegister.setBounds(360, 530, 150, 35);
        btnBack.setBounds(770, 535, 100, 35);
        txtName.setBounds(400, 130, 210, 28);
        txtEmail.setBounds(400, 180, 210, 28);
        txtPhone.setBounds(400, 230, 210, 28);
        rbMale.setBounds(400, 280, 70, 28);
        rbFemale.setBounds(480, 280, 100, 28);
        txtAge.setBounds(400, 330, 210, 28);
        txtAadhaarNo.setBounds(400, 380, 210, 28);
        txtPanNo.setBounds(400, 430, 210, 28);
        txtPassword.setBounds(400, 480, 210, 28);

        add(lblRegister);
        add(lblName);
        add(lblEmail);
        add(lblPhone);
        add(lblGender);
        add(lblAge);
        add(lblAadhaarNo);
        add(lblPanNo);
        add(lblPassword);
        add(btnRegister);
        add(btnBack);
        add(txtName);
        add(txtEmail);
        add(txtPhone);
        add(rbMale);
        add(rbFemale);
        add(txtAge);
        add(txtAadhaarNo);
        add(txtPanNo);
        add(txtPassword);

        btnRegister.addActionListener(this);
        btnBack.addActionListener(this);
        generateBackground();
        setSize(900, 623);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void generateBackground() {
        // Generating the Background
        ImageIcon imgBackground = new ImageIcon("./img/background.jpg");

        JLabel lblBackground = new JLabel(imgBackground);

        lblBackground.setBounds(0, 0, 900, 623);

        add(lblBackground);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            // data will insert into database
            String url = "jdbc:mysql://localhost:3306/voting_system";
            String username = "root";
            String password = "";
            String query = "INSERT INTO `register`(`name`, `email`, `phone`, `gender`, `age`, `aadhaar_no`, `pan_no`, `password`) VALUES (?,?,?,?,?,?,?,?)";

            if (txtName.getText().equals("") || txtEmail.getText().equals("") || txtPhone.getText().equals("")
                    || txtAge.getText().equals("") || txtAadhaarNo.getText().equals("") || txtPanNo.getText().equals("")
                    || txtPassword.getText().equals("")
                    || rbMale.isSelected() == false && rbFemale.isSelected() == false) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
            } else {
                if (Integer.parseInt(txtAge.getText()) < 18) {
                    JOptionPane.showMessageDialog(null, "You are not eligible to vote");
                    return;
                }

                try {
                    Connection con = DriverManager.getConnection(url, username, password);
                    Statement stmt = con.createStatement();
                    PreparedStatement pstmt = con.prepareStatement(query);
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    pstmt.setString(1, txtName.getText());
                    pstmt.setString(2, txtEmail.getText());
                    pstmt.setString(3, txtPhone.getText());
                    if (rbMale.isSelected())
                        pstmt.setString(4, "Male");
                    else if (rbFemale.isSelected())
                        pstmt.setString(4, "Female");
                    pstmt.setString(5, txtAge.getText());
                    pstmt.setString(6, txtAadhaarNo.getText());
                    pstmt.setString(7, txtPanNo.getText());
                    pstmt.setString(8, txtPassword.getText());

                    pstmt.executeUpdate();

                    query = "select cardno from register where name = ?";
                    pstmt = con.prepareStatement(query);
                    pstmt.setString(1, txtName.getText());
                    ResultSet rs = pstmt.executeQuery();
                    rs.next();
                    String cNo = rs.getString(1);
                    JOptionPane.showMessageDialog(null, "Registration Successful\nYour Card No is "+cNo);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                dispose();
                new Home("Home");
            }
        }
        if (e.getSource() == btnBack) {
            new Home("Advance voting system");
            dispose();
        }
    }
}

class Result {
    public Result() {
        JFrame frame = new JFrame("Result");
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 623);
        frame.setVisible(true);
    }
}

public class VotingSystem {
    public static void main(String[] args) {
        new Home("Advance voting system");
        // new Register();
        // new Login();
        // new Vote(3);
    }
}