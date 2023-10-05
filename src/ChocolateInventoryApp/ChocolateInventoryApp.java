package ChocolateInventoryApp;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ChocolateInventoryApp extends JFrame{

    public ChocolateInventoryApp() {
        setTitle("초콜릿 재고 관리");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("초콜릿 재고 관리");
        Font defaultFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
        titleLabel.setFont(defaultFont);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton addChocoButton = new JButton("제품 추가");
        JButton orderButton = new JButton("주문");
        JButton addQuantityButton = new JButton("재고 추가");
        JButton priceButton = new JButton("가격 변경");
        JButton deleteButton = new JButton("제품 삭제");
        JButton outButton = new JButton("로그아웃");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addChocoButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(addQuantityButton);
        buttonPanel.add(priceButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(outButton);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        chocoDAO dao = new chocoDAO(DBConnection.getConnection());
        List<chocoDTO> chocolates = dao.getChocolates();
        String[] columnNames = {"초콜릿 ID", "초콜릿 이름", "재고 수량", "가격"};
        Object[][] data = new Object[chocolates.size()][4];

        for (int i = 0; i < chocolates.size(); i++) {
            data[i][0] = chocolates.get(i).getChocoID();
            data[i][1] = chocolates.get(i).getChocoName();
            data[i][2] = chocolates.get(i).getQuantity();
            data[i][3] = chocolates.get(i).getPrice();
        }
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane tableScrollPane = new JScrollPane(table);

        addChocoButton.addActionListener(e -> {
            new AddChocolateScreen();
        });

        outButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                    MainScreen mainScreen = new MainScreen();
                    mainScreen.setVisible(true);
                }
            }
        });

        deleteButton.addActionListener(e -> {
            String chocoName = JOptionPane.showInputDialog(this, "삭제할 제품의 이름을 입력하세요:");
            if (chocoName != null) {
                new Thread(() -> {
                    boolean success = dao.deleteChocolate(chocoName);

                    if (success) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "제품이 삭제되었습니다.", "제품 삭제 성공", JOptionPane.INFORMATION_MESSAGE);
                            new ChocolateInventoryApp().setVisible(true);
                            dispose();
                        });
                    } else {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(this, "제품 삭제에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        });
                    }
                }).start();
            }
        });
        
        addQuantityButton.addActionListener(e -> {
            String chocoName = JOptionPane.showInputDialog(this, "재고를 추가할 제품의 이름을 입력하세요:");
            if (chocoName != null) {
            	if(dao.chocoNameCheck(chocoName)) {
            		SwingUtilities.invokeLater(() -> new AddQuantityScreen(dao, chocoName));
            	}else {
            		JOptionPane.showMessageDialog(this, "존재하지 않는 제품입니다.", "오류", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        
        orderButton.addActionListener(e -> {
            String chocoName = JOptionPane.showInputDialog(this, "주문할 제품의 이름을 입력하세요:");

            if (chocoName != null) {
                // 초콜릿이 존재하는지 확인
                boolean chocoExists = dao.chocoNameCheck(chocoName);

                if (chocoExists) {
                    String quantityOrder = JOptionPane.showInputDialog(this, chocoName + "의 주문 수량을 입력하세요:");
                    if (quantityOrder != null) {
                        try {
                            int quantityToOrder = Integer.parseInt(quantityOrder);
                            boolean success = dao.orderChocolate(chocoName, quantityToOrder);

                            if (success) {
                                JOptionPane.showMessageDialog(this, "주문이 완료되었습니다.", "주문 성공", JOptionPane.INFORMATION_MESSAGE);
                                SwingUtilities.invokeLater(() -> new ChocolateInventoryApp());
                            } else {
                                JOptionPane.showMessageDialog(this, "주문에 실패했습니다. 재고가 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "유효하지 않은 숫자입니다. 다시 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "제품이 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        priceButton.addActionListener(e -> {
            String chocoName = JOptionPane.showInputDialog(this, "가격을 변경할 초콜릿의 이름을 입력하세요:");

            if (chocoName != null) {
                boolean chocoExists = dao.chocoNameCheck(chocoName);

                if (chocoExists) {
                    String priceString = JOptionPane.showInputDialog(this, chocoName + "의 새로운 가격을 입력하세요:");
                    if (priceString != null) {
                        try {
                            int newPrice = Integer.parseInt(priceString);
                            boolean success = dao.changeChocolatePrice(chocoName, newPrice);

                            if (success) {
                                JOptionPane.showMessageDialog(this, "가격이 변경되었습니다.", "가격 변경 완료", JOptionPane.INFORMATION_MESSAGE);
                                SwingUtilities.invokeLater(() -> new ChocolateInventoryApp());
                            } else {
                                JOptionPane.showMessageDialog(this, "가격 변경에 실패했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "유효하지 않은 숫자입니다. 다시 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "제품이 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(titleLabel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        setVisible(true);
	    }
	
	public static void main(String[] args) {
        new ChocolateInventoryApp();
    }
}
