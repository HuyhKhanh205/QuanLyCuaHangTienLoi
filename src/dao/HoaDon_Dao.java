package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.SanPham;
import entity.CTHoaDon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HoaDon_Dao {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();

    public void save(HoaDon hd) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO HoaDon (MaHD, NgayTao) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hd.getMaHD());
            stmt.setDate(2, new java.sql.Date(hd.getNgayTao().getTime()));
            stmt.executeUpdate();

            for (CTHoaDon ct : hd.getDsSanPham()) {
                String ctSql = "INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
                PreparedStatement ctStmt = conn.prepareStatement(ctSql);
                ctStmt.setString(1, hd.getMaHD());
                ctStmt.setString(2, ct.getSanPham().getMaSP());
                ctStmt.setInt(3, ct.getSoLuong());
                ctStmt.setDouble(4, ct.getDonGia());
                ctStmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException("Lỗi khi lưu hóa đơn: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ArrayList<HoaDon> findAll() {
        ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT hd.MaHD, hd.NgayTao, ct.MaSP, ct.SoLuong, ct.DonGia " +
                         "FROM HoaDon hd LEFT JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            HoaDon currentHD = null;
            String currentMaHD = null;
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                if (currentMaHD == null || !maHD.equals(currentMaHD)) {
                    currentHD = new HoaDon(maHD, rs.getDate("NgayTao"));
                    dsHoaDon.add(currentHD);
                    currentMaHD = maHD;
                }
                String maSP = rs.getString("MaSP");
                if (maSP != null) {
                    SanPham sp = sanPhamDao.findByMaSP(maSP);
                    int soLuong = rs.getInt("SoLuong");
                    double donGia = rs.getDouble("DonGia");
                    CTHoaDon ct = new CTHoaDon(currentHD, sp, soLuong, donGia);
                    currentHD.getDsSanPham().add(ct);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách hóa đơn: " + e.getMessage());
        }
        return dsHoaDon;
    }

    public HoaDon findByMaHD(String maHD) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT hd.MaHD, hd.NgayTao, ct.MaSP, ct.SoLuong, ct.DonGia " +
                         "FROM HoaDon hd LEFT JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD WHERE hd.MaHD = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            HoaDon hd = null;
            while (rs.next()) {
                if (hd == null) {
                    hd = new HoaDon(rs.getString("MaHD"), rs.getDate("NgayTao"));
                }
                String maSP = rs.getString("MaSP");
                if (maSP != null) {
                    SanPham sp = sanPhamDao.findByMaSP(maSP);
                    int soLuong = rs.getInt("SoLuong");
                    double donGia = rs.getDouble("DonGia");
                    CTHoaDon ct = new CTHoaDon(hd, sp, soLuong, donGia);
                    hd.getDsSanPham().add(ct);
                }
            }
            return hd;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
    }
}