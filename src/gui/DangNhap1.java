package gui;

import dao.NhanVien_Dao;
import entity.NhanVien;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DangNhap1 extends JFrame {
    private JComboBox<String> nhanVienComboBox;
    private JButton loginButton;
    private NhanVien_Dao nhanVienDao;
    private ArrayList<NhanVien> dsNhanVien;

    public DangNhap1() {
        nhanVienDao = new NhanVien_Dao();
        dsNhanVien = nhanVienDao.findAll();
        initUI();
    }

    private void initUI() {
        setTitle("Đăng Nhập");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Chọn Nhân Viên:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        nhanVienComboBox = new JComboBox<>();
        updateNhanVienComboBox();
        panel.add(nhanVienComboBox);

        loginButton = new JButton("Đăng Nhập");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        add(panel);
    }

    private void updateNhanVienComboBox() {
        nhanVienComboBox.removeAllItems();
        for (NhanVien nv : dsNhanVien) {
            nhanVienComboBox.addItem(nv.getMaNV() + " - " + nv.getTenNhanVien());
        }
    }

    private void login() {
        String nvStr = (String) nhanVienComboBox.getSelectedItem();
        if (nvStr == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên!");
            return;
        }

        String maNV = nvStr.split(" - ")[0];
        NhanVien nv = nhanVienDao.findByMaNV(maNV);
        if (nv == null) {
            JOptionPane.showMessageDialog(this, "Nhân viên không tồn tại!");
            return;
        }

        JOptionPane.showMessageDialog(this, nv.getChucVu() + " - " + nv.getTenNhanVien());
        dispose(); 
        QuanLyCuaHang quanLy = new QuanLyCuaHang(nv);
        quanLy.setVisible(true);
    }

    public static void main(String[] args) {
        new DangNhap1().setVisible(true);
    }
}