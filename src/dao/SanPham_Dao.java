package dao;

import connectDB.ConnectDB;
import entity.SanPham;
import entity.NhaCungCap;
import entity.DanhMucSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class SanPham_Dao {
    private NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();
    private DanhMucSP_Dao danhMucDao = new DanhMucSP_Dao();

    public void save(SanPham sp) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "INSERT INTO SanPham (MaSP, TenSP, SoLuongTon, HSD, MaNCC, Gia, MaDanhMuc) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sp.getMaSP());
            stmt.setString(2, sp.getTenSP());
            stmt.setInt(3, sp.getSoLuongTon());
            stmt.setDate(4, new java.sql.Date(sp.getHSD().getTime()));
            stmt.setString(5, sp.getNhaCungCap().getMancc());
            stmt.setDouble(6, sp.getGia());
            stmt.setString(7, sp.getLoai().getMaDanhMuc());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu sản phẩm: " + e.getMessage());
        }
    }

    public void update(SanPham sp) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "UPDATE SanPham SET TenSP = ?, SoLuongTon = ?, HSD = ?, MaNCC = ?, Gia = ?, MaDanhMuc = ? WHERE MaSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sp.getTenSP());
            stmt.setInt(2, sp.getSoLuongTon());
            stmt.setDate(3, new java.sql.Date(sp.getHSD().getTime()));
            stmt.setString(4, sp.getNhaCungCap().getMancc());
            stmt.setDouble(5, sp.getGia());
            stmt.setString(6, sp.getLoai().getMaDanhMuc());
            stmt.setString(7, sp.getMaSP());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    public void delete(String maSP) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "DELETE FROM SanPham WHERE MaSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSP);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }

    public ArrayList<SanPham> findAll() {
        ArrayList<SanPham> dsSanPham = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.HSD, sp.MaNCC, sp.Gia, sp.MaDanhMuc, ncc.TenNCC, dm.TenDanhMuc " +
                         "FROM SanPham sp " +
                         "JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC " +
                         "JOIN DanhMucSP dm ON sp.MaDanhMuc = dm.MaDanhMuc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String maSP = rs.getString("MaSP");
                String tenSP = rs.getString("TenSP");
                int soLuong = rs.getInt("SoLuongTon");
                Date hsd = rs.getDate("HSD");
                String maNCC = rs.getString("MaNCC");
                String tenNCC = rs.getString("TenNCC");
                double gia = rs.getDouble("Gia");
                String maDanhMuc = rs.getString("MaDanhMuc");
                String tenDanhMuc = rs.getString("TenDanhMuc");

                SanPham sp = new SanPham(
                    maSP,
                    tenSP,
                    soLuong,
                    hsd,
                    new NhaCungCap(maNCC, tenNCC),
                    gia,
                    new DanhMucSP(maDanhMuc, tenDanhMuc)
                );
                dsSanPham.add(sp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách sản phẩm: " + e.getMessage());
        }
        return dsSanPham;
    }

    public SanPham findByMaSP(String maSP) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.HSD, sp.MaNCC, sp.Gia, sp.MaDanhMuc, ncc.TenNCC, dm.TenDanhMuc " +
                         "FROM SanPham sp " +
                         "JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC " +
                         "JOIN DanhMucSP dm ON sp.MaDanhMuc = dm.MaDanhMuc " +
                         "WHERE sp.MaSP = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSP);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new SanPham(
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuongTon"),
                    rs.getDate("HSD"),
                    new NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC")),
                    rs.getDouble("Gia"),
                    new DanhMucSP(rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc"))
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm sản phẩm: " + e.getMessage());
        }
        return null;
    }
}