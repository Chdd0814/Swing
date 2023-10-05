package ChocolateInventoryApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class AddQuantityScreen extends JFrame{
    private chocoDAO dao;
    private String chocoName;

    public AddQuantityScreen(chocoDAO dao, String chocoName) {
        this.dao = dao;
        this.chocoName = chocoName;

        setTitle("재고 추가");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField quantityField = new JTextField(20);
        JButton addButton = new JButton("추가");

        addButton.addActionListener(e -> {
            String quantityString = quantityField.getText();
            try {
                int quantityToAdd = Integer.parseInt(quantityString);
                boolean success = dao.addQuantity(chocoName, quantityToAdd);
                if (success) {
                    JOptionPane.showMessageDialog(this, "재고가 추가되었습니다.", "재고 추가 완료", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new ChocolateInventoryApp();
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "재고 추가에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "유효하지 않은 숫자입니다. 다시 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        JLabel chocoNameLabel = new JLabel("제품 이름: " + chocoName);
        chocoNameLabel.setFont(font);
        chocoNameLabel.setForeground(Color.BLUE);
        chocoNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chocoNameLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(new JLabel("추가 재고 수량 "));
        inputPanel.add(quantityField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(addButton);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(inputPanel);

        add(panel);
        setVisible(true);
    }
}
