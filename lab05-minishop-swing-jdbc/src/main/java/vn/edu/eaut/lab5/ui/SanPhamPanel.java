package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SanPhamPanel extends JPanel {

    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JTextField txtMaSp, txtTenSp, txtDonGia, txtSoLuong, txtTimKiem;
    private JTable table;
    private DefaultTableModel model;

    public SanPhamPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));

        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        txtTenSp = new JTextField();
        txtDonGia = new JTextField();
        txtSoLuong = new JTextField();
        txtTimKiem = new JTextField();

        panel.add(new JLabel("Ma SP:"));
        panel.add(txtMaSp);
        panel.add(new JLabel("Ten SP:"));
        panel.add(txtTenSp);
        panel.add(new JLabel("Tim kiem (ten):"));

        panel.add(new JLabel("Don gia:"));
        panel.add(txtDonGia);
        panel.add(new JLabel("So luong:"));
        panel.add(txtSoLuong);
        panel.add(txtTimKiem);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        model = new DefaultTableModel(new Object[]{"Ma SP", "Ten SP", "Don gia", "So luong"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
        return new JScrollPane(table);
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));

        JButton btnThem = new JButton("Them");
        JButton btnSua = new JButton("Sua");
        JButton btnXoa = new JButton("Xoa");
        JButton btnLamMoi = new JButton("Lam moi");
        JButton btnTimKiem = new JButton("Tim kiem");

        btnThem.addActionListener(e -> themSanPham());
        btnSua.addActionListener(e -> suaSanPham());
        btnXoa.addActionListener(e -> xoaSanPham());
        btnLamMoi.addActionListener(e -> {
            clearForm();
            loadData();
        });
        btnTimKiem.addActionListener(e -> timKiem());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLamMoi);
        panel.add(btnTimKiem);
        return panel;
    }

    private void loadData() {
        try {
            fillTable(sanPhamBUS.findAll());
        } catch (SQLException e) {
            MessageUtil.showError(this, "Loi tai du lieu san pham: " + e.getMessage());
        }
    }

    private void fillTable(List<SanPham> list) {
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()});
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        txtMaSp.setText(model.getValueAt(row, 0).toString());
        txtTenSp.setText(model.getValueAt(row, 1).toString());
        txtDonGia.setText(model.getValueAt(row, 2).toString());
        txtSoLuong.setText(model.getValueAt(row, 3).toString());
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        table.clearSelection();
    }

    private SanPham buildSanPhamFromForm() {
        SanPham sp = new SanPham();
        if (!txtMaSp.getText().trim().isEmpty()) {
            sp.setMaSp(Integer.parseInt(txtMaSp.getText().trim()));
        }
        sp.setTenSp(txtTenSp.getText().trim());
        try {
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Don gia phai la so");
        }
        try {
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("So luong phai la so nguyen");
        }
        return sp;
    }

    private void themSanPham() {
        try {
            SanPham sp = buildSanPhamFromForm();
            sp.setMaSp(0); // ep them moi
            sanPhamBUS.save(sp);
            MessageUtil.showInfo(this, "Them san pham thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, e.getMessage());
        }
    }

    private void suaSanPham() {
        if (txtMaSp.getText().trim().isEmpty()) {
            MessageUtil.showError(this, "Vui long chon san pham can sua");
            return;
        }
        try {
            SanPham sp = buildSanPhamFromForm();
            sanPhamBUS.save(sp);
            MessageUtil.showInfo(this, "Cap nhat san pham thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, e.getMessage());
        }
    }

    private void xoaSanPham() {
        if (txtMaSp.getText().trim().isEmpty()) {
            MessageUtil.showError(this, "Vui long chon san pham can xoa");
            return;
        }
        if (!MessageUtil.confirm(this, "Ban co chac muon xoa san pham nay?")) return;
        try {
            int maSp = Integer.parseInt(txtMaSp.getText().trim());
            sanPhamBUS.delete(maSp);
            MessageUtil.showInfo(this, "Xoa san pham thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, "Khong the xoa (co the san pham da phat sinh hoa don): " + e.getMessage());
        }
    }

    private void timKiem() {
        try {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadData();
            } else {
                fillTable(sanPhamBUS.searchByName(keyword));
            }
        } catch (SQLException e) {
            MessageUtil.showError(this, "Loi tim kiem: " + e.getMessage());
        }
    }
}
