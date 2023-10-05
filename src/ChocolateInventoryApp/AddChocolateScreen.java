package ChocolateInventoryApp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddChocolateScreen extends JFrame{
	private JTextField chocoNameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JButton addButton;

    public AddChocolateScreen() {
        setTitle("제품 추가");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        chocoNameField = new JTextField(10);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);
        addButton = new JButton("추가");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chocoName = chocoNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                int price = Integer.parseInt(priceField.getText());

                chocoDTO chocolate = new chocoDTO(0, chocoName, price, quantity);

                chocoDAO dao = new chocoDAO(DBConnection.getConnection());
                boolean success = dao.addChocolate(chocolate);

                if (success) {
                    JOptionPane.showMessageDialog(AddChocolateScreen.this, "제품이 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new ChocolateInventoryApp();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(AddChocolateScreen.this, "제품 추가에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("초콜릿 이름:"));
        panel.add(chocoNameField);
        panel.add(new JLabel("재고 수량:"));
        panel.add(quantityField);
        panel.add(new JLabel("가격:"));
        panel.add(priceField);
        panel.add(new JLabel(""));
        panel.add(addButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }
}
