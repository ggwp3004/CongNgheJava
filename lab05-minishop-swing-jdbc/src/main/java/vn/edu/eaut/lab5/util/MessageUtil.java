package vn.edu.eaut.lab5.util;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MessageUtil {

    public static void showInfo(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Loi", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String msg) {
        int result = JOptionPane.showConfirmDialog(parent, msg, "Xac nhan", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}
