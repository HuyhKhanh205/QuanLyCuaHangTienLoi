package dao;

import connectDB.ConnectDB;
import entity.NhaCungCap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NhaCungCap_Dao {
    public void themNhaCungCap(NhaCungCap ncc) {
        save(ncc);
    }

    public void capNhatNhaCungCap(NhaCungCap ncc) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "UPDATE NhaCungCap SET TenNCC = ? WHERE MaNCC = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ncc.getTenncc());
            stmt.setString(2, ncc.getMancc());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật nhà cung cấp: " + e.getMessage());
        }
    }

    public void xoaNhaCungCap(String maNCC) {
        delete(maNCC);
    }

    public NhaCungCap timNhaCungCap(String maNCC) {
        return findByMaNCC(maNCC);
    }

    public void save(NhaCungCap ncc) {
        // Giữ nguyên hàm save đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String checkSql = "SELECT COUNT(*) FROM NhaCungCap WHERE MaNCC = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, ncc.getMancc());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                capNhatNhaCungCap(ncc);
            } else {
                String insertSql = "INSERT INTO NhaCungCap (MaNCC, TenNCC) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, ncc.getMancc());
                insertStmt.setString(2, ncc.getTenncc());
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu nhà cung cấp: " + e.getMessage());
        }
    }

    public ArrayList<NhaCungCap> findAll() {
        // Giữ nguyên hàm findAll đã có
        ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaNCC, TenNCC FROM NhaCungCap";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsNhaCungCap.add(new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách nhà cung cấp: " + e.getMessage());
        }
        return dsNhaCungCap;
    }

    public void delete(String maNCC) {
        // Giữ nguyên hàm delete đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM NhaCungCap WHERE MaNCC = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNCC);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa nhà cung cấp: " + e.getMessage());
        }
    }

    public NhaCungCap findByMaNCC(String maNCC) {
        // Giữ nguyên hàm findByMaNCC đã có
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT MaNCC, TenNCC FROM NhaCungCap WHERE MaNCC = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNCC);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm nhà cung cấp: " + e.getMessage());
        }
        return null;
    }
}