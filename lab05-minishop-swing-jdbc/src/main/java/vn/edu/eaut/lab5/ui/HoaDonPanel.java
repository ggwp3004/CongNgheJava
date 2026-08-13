package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {

    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JComboBox<KhachHang> cboKhachHang;
    private JComboBox<SanPham> cboSanPham;
    private JTextField txtSoLuong;
    private JLabel lblTongTien;

    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    private final List<ChiTietHoaDon> chiTietTam = new ArrayList<>();

    public HoaDonPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildBottomPanel(), BorderLayout.SOUTH);

        loadComboData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Lap hoa don"));

        cboKhachHang = new JComboBox<>();
        cboSanPham = new JComboBox<>();
        txtSoLuong = new JTextField();

        panel.add(new JLabel("Khach hang:"));
        panel.add(cboKhachHang);
        panel.add(new JLabel("San pham:"));
        panel.add(cboSanPham);

        JButton btnThemDong = new JButton("Them dong");
        btnThemDong.addActionListener(e -> themDongChiTiet());

        panel.add(new JLabel("So luong:"));
        panel.add(txtSoLuong);
        panel.add(new JLabel(""));
        panel.add(btnThemDong);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        modelChiTiet = new DefaultTableModel(
                new Object[]{"Ma SP", "Ten SP", "So luong", "Don gia", "Thanh tien"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblChiTiet = new JTable(modelChiTiet);
        return new JScrollPane(tblChiTiet);
    }

    private JPanel buildBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        lblTongTien = new JLabel("Tong tien: 0 VND");
        lblTongTien.setFont(lblTongTien.getFont().deriveFont(Font.BOLD, 14f));
        lblTongTien.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JButton btnXoaDong = new JButton("Xoa dong da chon");
        JButton btnLuuHoaDon = new JButton("Luu hoa don");
        JButton btnLamMoi = new JButton("Lam moi");

        btnXoaDong.addActionListener(e -> xoaDongChiTiet());
        btnLuuHoaDon.addActionListener(e -> luuHoaDon());
        btnLamMoi.addActionListener(e -> lamMoi());

        btnPanel.add(btnXoaDong);
        btnPanel.add(btnLuuHoaDon);
        btnPanel.add(btnLamMoi);

        panel.add(lblTongTien, BorderLayout.WEST);
        panel.add(btnPanel, BorderLayout.EAST);
        return panel;
    }

    private void loadComboData() {
        try {
            cboKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cboKhachHang.addItem(kh);
            }
            cboSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cboSanPham.addItem(sp);
            }
        } catch (SQLException e) {
            MessageUtil.showError(this, "Loi tai du lieu: " + e.getMessage());
        }
    }

    private void themDongChiTiet() {
        SanPham sp = (SanPham) cboSanPham.getSelectedItem();
        if (sp == null) {
            MessageUtil.showError(this, "Vui long chon san pham");
            return;
        }
        int soLuong;
        try {
            soLuong = Integer.parseInt(txtSoLuong.getText().trim());
        } catch (NumberFormatException e) {
            MessageUtil.showError(this, "So luong phai la so nguyen");
            return;
        }
        if (soLuong <= 0) {
            MessageUtil.showError(this, "So luong phai lon hon 0");
            return;
        }
        if (soLuong > sp.getSoLuong()) {
            MessageUtil.showError(this, "So luong ban vuot qua ton kho (con lai: " + sp.getSoLuong() + ")");
            return;
        }

        ChiTietHoaDon ct = new ChiTietHoaDon(sp.getMaSp(), sp.getTenSp(), soLuong, sp.getDonGia());
        chiTietTam.add(ct);
        modelChiTiet.addRow(new Object[]{ct.getMaSp(), ct.getTenSp(), ct.getSoLuong(), ct.getDonGia(), ct.getThanhTien()});
        txtSoLuong.setText("");
        capNhatTongTien();
    }

    private void xoaDongChiTiet() {
        int row = tblChiTiet.getSelectedRow();
        if (row < 0) {
            MessageUtil.showError(this, "Vui long chon dong can xoa");
            return;
        }
        chiTietTam.remove(row);
        modelChiTiet.removeRow(row);
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        BigDecimal tong = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietTam) {
            tong = tong.add(ct.getThanhTien());
        }
        lblTongTien.setText("Tong tien: " + tong + " VND");
    }

    private void luuHoaDon() {
        KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
        if (kh == null) {
            MessageUtil.showError(this, "Vui long chon khach hang");
            return;
        }
        try {
            int maHd = hoaDonBUS.save(kh.getMaKh(), chiTietTam);
            MessageUtil.showInfo(this, "Da luu hoa don thanh cong! Ma hoa don: " + maHd);
            lamMoi();
        } catch (Exception e) {
            MessageUtil.showError(this, "Loi luu hoa don: " + e.getMessage());
        }
    }

    private void lamMoi() {
        chiTietTam.clear();
        modelChiTiet.setRowCount(0);
        txtSoLuong.setText("");
        lblTongTien.setText("Tong tien: 0 VND");
        loadComboData(); // tai lai ton kho moi nhat
    }
}
