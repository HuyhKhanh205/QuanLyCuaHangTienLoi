package dao;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.SanPham;
import entity.CTHoaDon;
import entity.KhachHang;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HoaDon_Dao {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();

    public void save(HoaDon hd) {
        if (findByMaHD(hd.getMaHD()) != null) {
            throw new IllegalArgumentException("Mã hóa đơn đã tồn tại.");
        }
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection();
            conn.setAutoCommit(false);

            String sql = "INSERT INTO HoaDon (MaHD, NgayTao, MaKH, MaNV, DiemSuDung) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hd.getMaHD());
            stmt.setDate(2, new java.sql.Date(hd.getNgayTao().getTime()));
            stmt.setString(3, hd.getKhachHang() != null ? hd.getKhachHang().getMaKH() : null);
            stmt.setString(4, hd.getNhanVien() != null ? hd.getNhanVien().getMaNV() : null);
            stmt.setInt(5, hd.getDiemSuDung());
            stmt.executeUpdate();

            for (CTHoaDon ct : hd.getDsSanPham()) {
                String ctSql = "INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia, TongTien, MaKH, IdKM) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ctStmt = conn.prepareStatement(ctSql);
                ctStmt.setString(1, hd.getMaHD());
                ctStmt.setString(2, ct.getSanPham().getMaSP());
                ctStmt.setInt(3, ct.getSoLuong());
                ctStmt.setDouble(4, ct.getDonGia());
                ctStmt.setDouble(5, ct.getTongTien());
                ctStmt.setString(6, ct.getKhachHang() != null ? ct.getKhachHang().getMaKH() : null);
                ctStmt.setString(7, ct.getIdKM());
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
            String sql = "SELECT hd.MaHD, hd.NgayTao, hd.MaKH, hd.MaNV, hd.DiemSuDung, ct.MaSP, ct.SoLuong, ct.DonGia, ct.TongTien, ct.MaKH AS MaKH_CT, ct.IdKM " +
                         "FROM HoaDon hd LEFT JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            HoaDon currentHD = null;
            String currentMaHD = null;
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                if (currentMaHD == null || !maHD.equals(currentMaHD)) {
                    String maKH = rs.getString("MaKH");
                    String maNV = rs.getString("MaNV");
                    KhachHang kh = maKH != null ? khachHangDao.findByMaKH(maKH) : null;
                    NhanVien nv = maNV != null ? nhanVienDao.findByMaNV(maNV) : null;
                    currentHD = new HoaDon(maHD, kh, nv);
                    currentHD.setDiemSuDung(rs.getInt("DiemSuDung"));
                    dsHoaDon.add(currentHD);
                    currentMaHD = maHD;
                }
                String maSP = rs.getString("MaSP");
                if (maSP != null) {
                    SanPham sp = sanPhamDao.findByMaSP(maSP);
                    int soLuong = rs.getInt("SoLuong");
                    double donGia = rs.getDouble("DonGia");
                    double tongTien = rs.getDouble("TongTien");
                    String maKH = rs.getString("MaKH_CT");
                    KhachHang kh = maKH != null ? khachHangDao.findByMaKH(maKH) : null;
                    CTHoaDon ct = new CTHoaDon(currentHD, sp, soLuong, donGia, kh);
                    ct.setTongTien(tongTien);
                    ct.setIdKM(rs.getString("IdKM"));
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
            String sql = "SELECT hd.MaHD, hd.NgayTao, hd.MaKH, hd.MaNV, hd.DiemSuDung, ct.MaSP, ct.SoLuong, ct.DonGia, ct.TongTien, ct.MaKH AS MaKH_CT, ct.IdKM " +
                        "FROM HoaDon hd LEFT JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD WHERE hd.MaHD = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            HoaDon hd = null;
            while (rs.next()) {
                if (hd == null) {
                    String maKH = rs.getString("MaKH");
                    String maNV = rs.getString("MaNV");
                    KhachHang kh = maKH != null ? khachHangDao.findByMaKH(maKH) : null;
                    NhanVien nv = maNV != null ? nhanVienDao.findByMaNV(maNV) : null;
                    hd = new HoaDon(rs.getString("MaHD"), kh, nv);
                    hd.setDiemSuDung(rs.getInt("DiemSuDung"));
                }
                String maSP = rs.getString("MaSP");
                if (maSP != null) {
                    SanPham sp = sanPhamDao.findByMaSP(maSP);
                    int soLuong = rs.getInt("SoLuong");
                    double donGia = rs.getDouble("DonGia");
                    double tongTien = rs.getDouble("TongTien");
                    String maKH = rs.getString("MaKH_CT");
                    KhachHang kh = maKH != null ? khachHangDao.findByMaKH(maKH) : null;
                    CTHoaDon ct = new CTHoaDon(hd, sp, soLuong, donGia, kh);
                    ct.setTongTien(tongTien);
                    ct.setIdKM(rs.getString("IdKM"));
                    hd.getDsSanPham().add(ct);
                }
            }
            return hd;
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
    }
}