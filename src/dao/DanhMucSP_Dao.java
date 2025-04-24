package dao;

import connectDB.ConnectDB;
import entity.DanhMucSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DanhMucSP_Dao {
    public void themDanhMuc(DanhMucSP dm) {
        save(dm);
    }

    public void capNhatDanhMuc(DanhMucSP dm) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "UPDATE DanhMucSP SET TenDanhMuc = ? WHERE MaDanhMuc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, dm.getTenDanhMuc());
            stmt.setString(2, dm.getMaDanhMuc());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật danh mục: " + e.getMessage());
        }
    }

    public void xoaDanhMuc(String maDanhMuc) {
        delete(maDanhMuc);
    }

    public DanhMucSP timDanhMuc(String maDanhMuc) {
        return findByMaDanhMuc(maDanhMuc);
    }

    public void save(DanhMucSP dm) {
        if (findByMaDanhMuc(dm.getMaDanhMuc()) != null) {
            throw new IllegalArgumentException("Mã danh mục đã tồn tại.");
        }
        try (Connection conn = ConnectDB.getConnection()) {
            String insertSql = "INSERT INTO DanhMucSP (MaDanhMuc, TenDanhMuc) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, dm.getMaDanhMuc());
            insertStmt.setString(2, dm.getTenDanhMuc());
            insertStmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu danh mục: " + e.getMessage());
        }
    }

    public ArrayList<DanhMucSP> findAll() {
        // Giữ nguyên hàm findAll đã có
        ArrayList<DanhMucSP> dsDanhMuc = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaDanhMuc, TenDanhMuc FROM DanhMucSP";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsDanhMuc.add(new DanhMucSP(rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách danh mục: " + e.getMessage());
        }
        return dsDanhMuc;
    }

    public void delete(String maDanhMuc) {
        // Giữ nguyên hàm delete đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM DanhMucSP WHERE MaDanhMuc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maDanhMuc);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa danh mục: " + e.getMessage());
        }
    }

    public DanhMucSP findByMaDanhMuc(String maDanhMuc) {
        // Giữ nguyên hàm findByMaDanhMuc đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaDanhMuc, TenDanhMuc FROM DanhMucSP WHERE MaDanhMuc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maDanhMuc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DanhMucSP(rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm danh mục: " + e.getMessage());
        }
        return null;
    }
}