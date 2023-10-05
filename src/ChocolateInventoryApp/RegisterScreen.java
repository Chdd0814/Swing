package ChocolateInventoryApp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterScreen extends JFrame{
    private JTextField idField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JPasswordField confirmPasswordField;
    Connection con = DBConnection.getConnection();

    public RegisterScreen() {
    	setTitle("회원가입");
        setSize(500, 500); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); 

        JLabel titleLabel = new JLabel("회원가입");
        Font defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        titleLabel.setFont(defaultFont);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        idField = new JTextField(10);
        nameField = new JTextField(10);
        phoneNumberField = new JTextField(10);
        passwordField = new JPasswordField(10);
        confirmPasswordField = new JPasswordField(10);

        JButton registerButton = new JButton("가입하기");
        registerButton.setPreferredSize(new Dimension(150, 30));

        registerButton.addActionListener(e -> {
        	String id = idField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String name = nameField.getText();
            String phoneNumberText = phoneNumberField.getText();

            if (id.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumberText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 항목을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int phoneNumber;
            try {
                phoneNumber = Integer.parseInt(phoneNumberText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "올바른 전화번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {
                userDTO user = new userDTO(id, password, name, phoneNumber);
                userDAO dao = new userDAO(con);
                boolean success = dao.registerUser(user);

                if (success) {
                    JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("아이디:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("비밀번호:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("비밀번호 확인:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("이름:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("전화번호:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(phoneNumberField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);

        add(titleLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); 
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); 

        setVisible(true);
    }
}
