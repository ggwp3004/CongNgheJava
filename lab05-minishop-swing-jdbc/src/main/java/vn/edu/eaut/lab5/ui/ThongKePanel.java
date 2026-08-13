package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ThongKePanel extends JPanel {

    private final ThongKeBUS thongKeBUS = new ThongKeBUS();

    private JTextField txtTuNgay, txtDenNgay;
    private JLabel lblDoanhThu, lblHoaDonCaoNhat, lblSanPhamBanChay;
    private JButton btnThongKe;

    public ThongKePanel() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildResultPanel(), BorderLayout.CENTER);
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 6, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Thong ke doanh thu (dinh dang ngay: yyyy-MM-dd)"));

        txtTuNgay = new JTextField(LocalDate.now().withDayOfMonth(1).toString());
        txtDenNgay = new JTextField(LocalDate.now().toString());
        btnThongKe = new JButton("Thong ke");
        btnThongKe.addActionListener(e -> runThongKe());

        panel.add(new JLabel("Tu ngay:"));
        panel.add(txtTuNgay);
        panel.add(new JLabel(""));

        panel.add(new JLabel("Den ngay:"));
        panel.add(txtDenNgay);
        panel.add(btnThongKe);

        return panel;
    }

    private JPanel buildResultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Ket qua"));

        lblDoanhThu = new JLabel("Doanh thu: -");
        lblHoaDonCaoNhat = new JLabel("Hoa don cao nhat: -");
        lblSanPhamBanChay = new JLabel("San pham ban chay nhat: -");

        for (JLabel l : new JLabel[]{lblDoanhThu, lblHoaDonCaoNhat, lblSanPhamBanChay}) {
            l.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            l.setFont(l.getFont().deriveFont(14f));
            panel.add(l);
        }
        return panel;
    }

    /** Chay thong ke bat dong bo bang SwingWorker de khong treo giao dien (EDT). */
    private void runThongKe() {
        final LocalDate tuNgay, denNgay;
        try {
            tuNgay = LocalDate.parse(txtTuNgay.getText().trim());
            denNgay = LocalDate.parse(txtDenNgay.getText().trim());
        } catch (Exception e) {
            MessageUtil.showError(this, "Dinh dang ngay khong hop le. Vui long nhap theo yyyy-MM-dd");
            return;
        }

        btnThongKe.setEnabled(false);
        lblDoanhThu.setText("Doanh thu: dang tinh...");
        lblHoaDonCaoNhat.setText("Hoa don cao nhat: dang tinh...");
        lblSanPhamBanChay.setText("San pham ban chay nhat: dang tinh...");

        new ThongKeWorker(tuNgay, denNgay).execute();
    }

    /** Ket qua thong ke tra ve tu doInBackground. */
    private static class KetQuaThongKe {
        BigDecimal doanhThu;
        String hoaDonCaoNhat;
        String sanPhamBanChay;
    }

    private class ThongKeWorker extends SwingWorker<KetQuaThongKe, Void> {
        private final LocalDate tuNgay;
        private final LocalDate denNgay;

        ThongKeWorker(LocalDate tuNgay, LocalDate denNgay) {
            this.tuNgay = tuNgay;
            this.denNgay = denNgay;
        }

        @Override
        protected KetQuaThongKe doInBackground() throws Exception {
            // Chay tren luong nen, khong lam treo EDT
            KetQuaThongKe kq = new KetQuaThongKe();
            kq.doanhThu = thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
            kq.hoaDonCaoNhat = thongKeBUS.hoaDonCaoNhat();
            kq.sanPhamBanChay = thongKeBUS.sanPhamBanChayNhat();
            return kq;
        }

        @Override
        protected void done() {
            btnThongKe.setEnabled(true);
            try {
                KetQuaThongKe kq = get();
                lblDoanhThu.setText("Doanh thu: " + kq.doanhThu + " VND");
                lblHoaDonCaoNhat.setText("Hoa don cao nhat: " + kq.hoaDonCaoNhat);
                lblSanPhamBanChay.setText("San pham ban chay nhat: " + kq.sanPhamBanChay);
            } catch (Exception e) {
                MessageUtil.showError(ThongKePanel.this, "Loi thong ke: " + e.getMessage());
            }
        }
    }
}
