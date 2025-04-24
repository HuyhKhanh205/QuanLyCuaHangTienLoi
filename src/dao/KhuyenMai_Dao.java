package dao;

import connectDB.ConnectDB;
import entity.KhuyenMai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KhuyenMai_Dao {
	public void themKhuyenMai(KhuyenMai km) {
	    if (timKhuyenMai(km.getIdKM()) != null) {
	        throw new IllegalArgumentException("Mã khuyến mãi đã tồn tại.");
	    }
	    try (Connection conn = ConnectDB.getConnection()) {
	        String sql = "INSERT INTO KhuyenMai (IdKM, MoTa, GiaTriGiam, NgayBatDau, NgayKetThuc) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, km.getIdKM());
	        stmt.setString(2, km.getMoTa());
	        stmt.setDouble(3, km.getGiaTriGiam());
	        stmt.setDate(4, new java.sql.Date(km.getNgayBatDau().getTime()));
	        stmt.setDate(5, new java.sql.Date(km.getNgayKetThuc().getTime()));
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Lỗi khi thêm khuyến mãi: " + e.getMessage());
	    }
	}

	public void capNhatKhuyenMai(KhuyenMai km) {
	    if (km.getGiaTriGiam() < 0) {
	        throw new IllegalArgumentException("Giá trị giảm không được âm.");
	    }
	    try (Connection conn = ConnectDB.getConnection()) {
	        String sql = "UPDATE KhuyenMai SET MoTa = ?, GiaTriGiam = ?, NgayBatDau = ?, NgayKetThuc = ? WHERE IdKM = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, km.getMoTa());
	        stmt.setDouble(2, km.getGiaTriGiam());
	        stmt.setDate(3, new java.sql.Date(km.getNgayBatDau().getTime()));
	        stmt.setDate(4, new java.sql.Date(km.getNgayKetThuc().getTime()));
	        stmt.setString(5, km.getIdKM());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        throw new RuntimeException("Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
	    }
	}

    public void xoaKhuyenMai(String idKM) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM KhuyenMai WHERE IdKM = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idKM);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa khuyến mãi: " + e.getMessage());
        }
    }

    public KhuyenMai timKhuyenMai(String idKM) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT IdKM, MoTa, GiaTriGiam, NgayBatDau, NgayKetThuc FROM KhuyenMai WHERE IdKM = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idKM);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhuyenMai(
                    rs.getString("IdKM"),
                    rs.getString("MoTa"),
                    rs.getDouble("GiaTriGiam"),
                    rs.getDate("NgayBatDau"),
                    rs.getDate("NgayKetThuc")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm khuyến mãi: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<KhuyenMai> findAll() {
        ArrayList<KhuyenMai> dsKhuyenMai = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT IdKM, MoTa, GiaTriGiam, NgayBatDau, NgayKetThuc FROM KhuyenMai";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsKhuyenMai.add(new KhuyenMai(
                    rs.getString("IdKM"),
                    rs.getString("MoTa"),
                    rs.getDouble("GiaTriGiam"),
                    rs.getDate("NgayBatDau"),
                    rs.getDate("NgayKetThuc")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách khuyến mãi: " + e.getMessage());
        }
        return dsKhuyenMai;
    }
}