package ChocolateInventoryApp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Main extends JFrame implements ActionListener{
	private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
	public Main() {
		setTitle("초콜릿 재고 관리 프로그램");
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("로그인");
        registerButton = new JButton("회원가입");
        
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
		
		JLabel titleLabel = new JLabel("papa galaxy");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setForeground(Color.BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("아이디:"));
        panel.add(usernameField);
        panel.add(new JLabel("비밀번호:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        
		setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
