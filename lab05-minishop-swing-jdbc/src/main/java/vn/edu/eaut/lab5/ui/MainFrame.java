package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.TaiKhoan;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final TaiKhoan currentUser;

    public MainFrame(TaiKhoan currentUser) {
        this.currentUser = currentUser;

        setTitle("MiniShop - Quan ly ban hang");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("San pham", new SanPhamPanel());
        tabbedPane.addTab("Khach hang", new KhachHangPanel());
        tabbedPane.addTab("Hoa don", new HoaDonPanel());
        tabbedPane.addTab("Thong ke", new ThongKePanel());

        setLayout(new BorderLayout());
        add(buildTopBar(), BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel buildTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        String vaiTro = currentUser.isAdmin() ? "Quan tri vien" : "Nhan vien";
        JLabel lblUser = new JLabel("Xin chao, " + currentUser.getHoTen() + " (" + vaiTro + ")");
        lblUser.setFont(lblUser.getFont().deriveFont(Font.BOLD));
        topBar.add(lblUser, BorderLayout.WEST);

        JButton btnLogout = new JButton("Dang xuat");
        btnLogout.addActionListener(e -> onLogout());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.add(btnLogout);
        topBar.add(right, BorderLayout.EAST);

        return topBar;
    }

    private void onLogout() {
        boolean confirm = MessageUtil.confirm(this, "Ban co chac muon dang xuat?");
        if (!confirm) {
            return;
        }
        dispose();
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
