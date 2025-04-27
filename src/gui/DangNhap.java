package gui;

import javax.swing.*;

import dao.NhanVien_Dao;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DangNhap extends JFrame {
    private JComboBox<String> nhanVienComboBox;
    private JPasswordField passwordField;
    private JButton loginButton;
    private NhanVien_Dao nhanVienDao;
    private ArrayList<NhanVien> dsNhanVien;

    public DangNhap() {
        setTitle("Đăng nhập hệ thống");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        nhanVienDao = new NhanVien_Dao();
        dsNhanVien = nhanVienDao.findAll();

        // Panel nền có ảnh
        JPanel bgPanel = new JPanel() {
            ImageIcon bg = new ImageIcon(getClass().getResource("/img/bg.jpg"));
            Image image = bg.getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        bgPanel.setLayout(new GridBagLayout());

        // Panel giao diện đăng nhập
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Chọn Nhân Viên:");
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        nhanVienComboBox = new JComboBox<>();
        updateNhanVienComboBox();
        panel.add(nhanVienComboBox);

        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        panel.add(passwordField);

        loginButton = new JButton("Đăng Nhập");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        // Thêm panel vào giữa bgPanel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        bgPanel.add(panel, gbc);

        add(bgPanel);
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

        String inputPassword = new String(passwordField.getPassword()).trim();
        if (inputPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!");
            return;
        }

        String maNV = nvStr.split(" - ")[0];
        NhanVien nv = nhanVienDao.findByMaNV(maNV);
        if (nv == null) {
            JOptionPane.showMessageDialog(this, "Nhân viên không tồn tại!");
            return;
        }

        if (inputPassword.equals(maNV)) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công: " + nv.getChucVu() + " - " + nv.getTenNhanVien());
            dispose();
            QuanLyCuaHang quanLy = new QuanLyCuaHang(nv);
            quanLy.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sai mật khẩu!");
        }
    }

    public static void main(String[] args) {
        new DangNhap().setVisible(true);
    }
}
