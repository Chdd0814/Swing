package ChocolateInventoryApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class MainScreen extends JFrame{
	
	public MainScreen() {
        setTitle("초콜릿 재고 관리 프로그램");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("C:/Users/KimChaeSung/OneDrive/바탕 화면/JavaAlgo/ChocolateManagement/src/image/chocoMain.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel titleLabel = new JLabel("Papa Galaxy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JTextField useridField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JButton loginButton = new JButton("로그인");
        JButton registerButton = new JButton("회원가입");

        loginButton.addActionListener(e -> {
            String userId = useridField.getText();
            String password = String.valueOf(passwordField.getPassword());
            
            userDAO dao = new userDAO(DBConnection.getConnection());
            boolean loggedIn = dao.loginUser(userId, password);
            if (loggedIn) {
            	ChocolateInventoryApp inventoryScreen = new ChocolateInventoryApp();
            	inventoryScreen.setVisible(true);
            	dispose();
            } else {
            	JOptionPane.showMessageDialog(this, "로그인 실패. 아이디 또는 비밀번호가 올바르지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            new RegisterScreen();
        });

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("아이디 : "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(useridField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("비밀번호 : "), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 50));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        centerPanel.add(inputPanel, BorderLayout.SOUTH);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> new MainScreen());
	}

}
