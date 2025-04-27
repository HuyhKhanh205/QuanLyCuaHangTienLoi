package gui;

import javax.swing.*;

import entity.NhanVien;

import java.awt.*;
import java.awt.event.*;

public class DangNhap extends JFrame {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
	private NhanVien nv;

    public DangNhap() {
        setTitle("Đăng nhập hệ thống");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Panel có ảnh nền
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Label + TextField
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        lblUser.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 0;
        bgPanel.add(lblUser, gbc);

        txtTenDangNhap = new JTextField(15);
        gbc.gridx = 1;
        bgPanel.add(txtTenDangNhap, gbc);

        JLabel lblPass = new JLabel("Mật khẩu:");
        lblPass.setForeground(Color.BLACK);
        gbc.gridx = 0; gbc.gridy = 1;
        bgPanel.add(lblPass, gbc);

        txtMatKhau = new JPasswordField(15);
        gbc.gridx = 1;
        bgPanel.add(txtMatKhau, gbc);

        btnDangNhap = new JButton("Đăng nhập");
        gbc.gridx = 1; gbc.gridy = 2;
        bgPanel.add(btnDangNhap, gbc);

        // Sự kiện đăng nhập
        btnDangNhap.addActionListener(e -> dangNhap());

        add(bgPanel);
    }

    private void dangNhap() {
    	
        String user = txtTenDangNhap.getText().trim();
        String pass = new String(txtMatKhau.getPassword()).trim();
        if (user.equals("admin") && pass.equals("admin")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            this.dispose();
            nv = new NhanVien("admin", "Quản trị viên", "Admin", "0123456789");
            new QuanLyCuaHang(nv).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!");
        }
    }

    public static void main(String[] args) {
        new DangNhap().setVisible(true);
    }
}
