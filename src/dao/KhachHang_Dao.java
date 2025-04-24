package dao;

import connectDB.ConnectDB;
import entity.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhachHang_Dao {
    public void themKhachHang(KhachHang kh) {
        save(kh);
    }

    public void capNhatKhachHang(KhachHang kh) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "UPDATE KhachHang SET TenKH = ?, Sdt = ?, Email = ?, DiemTichLuy = ? WHERE MaKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getSdt());
            stmt.setString(3, kh.getEmail());
            stmt.setInt(4, kh.getDiemTichLuy());
            stmt.setString(5, kh.getMaKH());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật khách hàng: " + e.getMessage());
        }
    }

    public void xoaKhachHang(String maKH) {
        delete(maKH);
    }

    public KhachHang timKhachHang(String maKH) {
        return findByMaKH(maKH);
    }

    public void save(KhachHang kh) {
        if (findByMaKH(kh.getMaKH()) != null) {
            throw new IllegalArgumentException("Mã khách hàng đã tồn tại.");
        }
        try (Connection conn = ConnectDB.getConnection()) {
            String insertSql = "INSERT INTO KhachHang (MaKH, TenKH, Sdt, Email, DiemTichLuy) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, kh.getMaKH());
            insertStmt.setString(2, kh.getTenKH());
            insertStmt.setString(3, kh.getSdt());
            insertStmt.setString(4, kh.getEmail());
            insertStmt.setInt(5, kh.getDiemTichLuy());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu khách hàng: " + e.getMessage());
        }
    }

    public ArrayList<KhachHang> findAll() {
        // Giữ nguyên hàm findAll đã có
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaKH, TenKH, Sdt, Email, DiemTichLuy FROM KhachHang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsKhachHang.add(new KhachHang(
                    rs.getString("MaKH"),
                    rs.getString("TenKH"),
                    rs.getString("Sdt"),
                    rs.getString("Email"),
                    rs.getInt("DiemTichLuy")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách khách hàng: " + e.getMessage());
        }
        return dsKhachHang;
    }

    public void delete(String maKH) {
        // Giữ nguyên hàm delete đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
    }

    public KhachHang findByMaKH(String maKH) {
        // Giữ nguyên hàm findByMaKH đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaKH, TenKH, Sdt, Email, DiemTichLuy FROM KhachHang WHERE MaKH = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHang(
                    rs.getString("MaKH"),
                    rs.getString("TenKH"),
                    rs.getString("Sdt"),
                    rs.getString("Email"),
                    rs.getInt("DiemTichLuy")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm khách hàng: " + e.getMessage());
        }
        return null;
    }
}