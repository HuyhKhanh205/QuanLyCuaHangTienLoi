package dao;

import connectDB.ConnectDB;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhanVien_Dao {
    public void themNhanVien(NhanVien nv) {
        save(nv);
    }

    public void capNhatNhanVien(NhanVien nv) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "UPDATE NhanVien SET TenNhanVien = ?, ChucVu = ?, Sdt = ? WHERE MaNV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nv.getTenNhanVien());
            stmt.setString(2, nv.getChucVu());
            stmt.setString(3, nv.getSdt());
            stmt.setString(4, nv.getMaNV());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật nhân viên: " + e.getMessage());
        }
    }

    public void xoaNhanVien(String maNV) {
        delete(maNV);
    }

    public NhanVien timNhanVien(String maNV) {
        return findByMaNV(maNV);
    }

    public void save(NhanVien nv) {
        // Giữ nguyên hàm save đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM NhanVien WHERE MaNV = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, nv.getMaNV());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                capNhatNhanVien(nv);
            } else {
                String insertSql = "INSERT INTO NhanVien (MaNV, TenNhanVien, ChucVu, Sdt) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, nv.getMaNV());
                insertStmt.setString(2, nv.getTenNhanVien());
                insertStmt.setString(3, nv.getChucVu());
                insertStmt.setString(4, nv.getSdt());
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu nhân viên: " + e.getMessage());
        }
    }

    public ArrayList<NhanVien> findAll() {
        // Giữ nguyên hàm findAll đã có
        ArrayList<NhanVien> dsNhanVien = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaNV, TenNhanVien, ChucVu, Sdt FROM NhanVien";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsNhanVien.add(new NhanVien(
                    rs.getString("MaNV"),
                    rs.getString("TenNhanVien"),
                    rs.getString("ChucVu"),
                    rs.getString("Sdt")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách nhân viên: " + e.getMessage());
        }
        return dsNhanVien;
    }

    public void delete(String maNV) {
        // Giữ nguyên hàm delete đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM NhanVien WHERE MaNV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNV);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa nhân viên: " + e.getMessage());
        }
    }

    public NhanVien findByMaNV(String maNV) {
        // Giữ nguyên hàm findByMaNV đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaNV, TenNhanVien, ChucVu, Sdt FROM NhanVien WHERE MaNV = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhanVien(
                    rs.getString("MaNV"),
                    rs.getString("TenNhanVien"),
                    rs.getString("ChucVu"),
                    rs.getString("Sdt")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm nhân viên: " + e.getMessage());
        }
        return null;
    }
}