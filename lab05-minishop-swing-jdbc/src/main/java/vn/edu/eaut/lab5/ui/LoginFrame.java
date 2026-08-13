package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.TaiKhoanBUS;
import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private final TaiKhoanBUS taiKhoanBUS = new TaiKhoanBUS();

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;

    public LoginFrame() {
        setTitle("MiniShop - Dang nhap");
        setSize(380, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setLayout(new BorderLayout());
        add(buildFormPanel(), BorderLayout.CENTER);

        getRootPane().setDefaultButton(btnLogin);
    }

    private JPanel buildFormPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("MINISHOP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        outer.add(lblTitle, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2, 2, 8, 12));
        form.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        form.add(new JLabel("Ten dang nhap:"));
        txtUsername = new JTextField();
        form.add(txtUsername);

        form.add(new JLabel("Mat khau:"));
        txtPassword = new JPasswordField();
        form.add(txtPassword);

        outer.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnLogin = new JButton("Dang nhap");
        btnExit = new JButton("Thoat");
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);
        outer.add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(this::onLogin);
        btnExit.addActionListener(e -> System.exit(0));

        // Cho phep nhan Enter o o mat khau de dang nhap
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onLogin(null);
                }
            }
        });

        return outer;
    }

    private void onLogin(ActionEvent e) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        btnLogin.setEnabled(false);
        try {
            TaiKhoan taiKhoan = taiKhoanBUS.login(username, password);
            MessageUtil.showInfo(this, "Xin chao, " + taiKhoan.getHoTen() + "!");
            openMainFrame(taiKhoan);
        } catch (IllegalArgumentException ex) {
            MessageUtil.showError(this, ex.getMessage());
            txtPassword.setText("");
        } catch (SQLException ex) {
            MessageUtil.showError(this, "Loi ket noi CSDL: " + ex.getMessage());
        } finally {
            btnLogin.setEnabled(true);
        }
    }

    private void openMainFrame(TaiKhoan taiKhoan) {
        dispose();
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(taiKhoan);
            frame.setVisible(true);
        });
    }
}
