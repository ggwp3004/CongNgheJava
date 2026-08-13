package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class KhachHangPanel extends JPanel {

    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    private JTextField txtMaKh, txtTenKh, txtSdt, txtDiaChi, txtTimKiem;
    private JTable table;
    private DefaultTableModel model;

    public KhachHangPanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadData();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));

        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        txtTenKh = new JTextField();
        txtSdt = new JTextField();
        // Chan nhap sai dinh dang SDT ngay tren giao dien
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());
        txtDiaChi = new JTextField();
        txtTimKiem = new JTextField();

        panel.add(new JLabel("Ma KH:"));
        panel.add(txtMaKh);
        panel.add(new JLabel("Ten KH:"));
        panel.add(txtTenKh);
        panel.add(new JLabel("Tim kiem:"));

        panel.add(new JLabel("SDT:"));
        panel.add(txtSdt);
        panel.add(new JLabel("Dia chi:"));
        panel.add(txtDiaChi);
        panel.add(txtTimKiem);

        return panel;
    }

    private JScrollPane buildTablePanel() {
        model = new DefaultTableModel(new Object[]{"Ma KH", "Ten KH", "SDT", "Dia chi"}, 0) {
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

        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
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

    void loadData() {
        try {
            fillTable(khachHangBUS.findAll());
        } catch (SQLException e) {
            MessageUtil.showError(this, "Loi tai du lieu khach hang: " + e.getMessage());
        }
    }

    private void fillTable(List<KhachHang> list) {
        model.setRowCount(0);
        for (KhachHang kh : list) {
            model.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()});
        }
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        txtMaKh.setText(model.getValueAt(row, 0).toString());
        txtTenKh.setText(model.getValueAt(row, 1).toString());
        txtSdt.setText(model.getValueAt(row, 2).toString());
        Object diaChi = model.getValueAt(row, 3);
        txtDiaChi.setText(diaChi == null ? "" : diaChi.toString());
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        table.clearSelection();
    }

    private KhachHang buildFromForm() {
        KhachHang kh = new KhachHang();
        if (!txtMaKh.getText().trim().isEmpty()) {
            kh.setMaKh(Integer.parseInt(txtMaKh.getText().trim()));
        }
        kh.setTenKh(txtTenKh.getText().trim());
        kh.setSdt(txtSdt.getText().trim());
        kh.setDiaChi(txtDiaChi.getText().trim());
        return kh;
    }

    private void themKhachHang() {
        try {
            KhachHang kh = buildFromForm();
            kh.setMaKh(0);
            khachHangBUS.save(kh);
            MessageUtil.showInfo(this, "Them khach hang thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, e.getMessage());
        }
    }

    private void suaKhachHang() {
        if (txtMaKh.getText().trim().isEmpty()) {
            MessageUtil.showError(this, "Vui long chon khach hang can sua");
            return;
        }
        try {
            khachHangBUS.save(buildFromForm());
            MessageUtil.showInfo(this, "Cap nhat khach hang thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, e.getMessage());
        }
    }

    private void xoaKhachHang() {
        if (txtMaKh.getText().trim().isEmpty()) {
            MessageUtil.showError(this, "Vui long chon khach hang can xoa");
            return;
        }
        if (!MessageUtil.confirm(this, "Ban co chac muon xoa khach hang nay?")) return;
        try {
            khachHangBUS.delete(Integer.parseInt(txtMaKh.getText().trim()));
            MessageUtil.showInfo(this, "Xoa khach hang thanh cong");
            clearForm();
            loadData();
        } catch (Exception e) {
            MessageUtil.showError(this, "Khong the xoa (co the khach hang da co hoa don): " + e.getMessage());
        }
    }

    private void timKiem() {
        try {
            String keyword = txtTimKiem.getText().trim();
            if (keyword.isEmpty()) {
                loadData();
            } else {
                fillTable(khachHangBUS.search(keyword));
            }
        } catch (SQLException e) {
            MessageUtil.showError(this, "Loi tim kiem: " + e.getMessage());
        }
    }
}
