package dao;

import connectDB.ConnectDB;
import entity.SanPham;
import entity.DanhMucSP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DSSanPham_Dao {
    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private ArrayList<SanPham> dsSanPham;
	private SanPham sp;
    
    public void themSanPham(SanPham sp) {
        sanPhamDao.save(sp);
    }

    public void xoaSanPham(String maSP) {
        sanPhamDao.delete(maSP);
    }

    public SanPham timSanPham(String maSP) {
        return sanPhamDao.findByMaSP(maSP);
    }

    public void capNhatSanPham(SanPham sp) {
        sanPhamDao.update(sp);
    }

    public ArrayList<SanPham> layDanhSachTheoDanhMuc(String maDanhMuc) {
        dsSanPham = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.HSD, sp.MaNCC, sp.Gia, sp.MaDanhMuc, ncc.TenNCC, dm.TenDanhMuc " +
                         "FROM SanPham sp " +
                         "JOIN NhaCungCap ncc ON sp.MaNCC = ncc.MaNCC " +
                         "JOIN DanhMucSP dm ON sp.MaDanhMuc = dm.MaDanhMuc " +
                         "WHERE sp.MaDanhMuc = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maDanhMuc);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                	sp = new SanPham(
                    rs.getString("MaSP"),
                    rs.getString("TenSP"),
                    rs.getInt("SoLuongTon"),
                    rs.getDate("HSD"),
                    new entity.NhaCungCap(rs.getString("MaNCC"), rs.getString("TenNCC")),
                    rs.getDouble("Gia"),
                    new DanhMucSP(rs.getString("MaDanhMuc"), rs.getString("TenDanhMuc"))
                );
                dsSanPham.add(sp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách sản phẩm theo danh mục: " + e.getMessage());
        }
        return dsSanPham;
    }

    public boolean kiemTraTonKho(String maSP, int soLuong) {
        sp = sanPhamDao.findByMaSP(maSP);
        return sp != null && sp.getSoLuongTon() >= soLuong;
    }

    public void nhapKho(String maSP, int soLuong) {
        sp = sanPhamDao.findByMaSP(maSP);
        if (sp != null) {
            sp.capNhatSoLuong(soLuong);
            sanPhamDao.update(sp);
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại.");
        }
    }

    public void xuatKho(String maSP, int soLuong) {
        sp = sanPhamDao.findByMaSP(maSP);
        if (sp != null) {
            if (sp.getSoLuongTon() >= soLuong) {
                sp.capNhatSoLuong(-soLuong);
                sanPhamDao.update(sp);
            } else {
                throw new RuntimeException("Số lượng tồn kho không đủ.");
            }
        } else {
            throw new RuntimeException("Sản phẩm không tồn tại.");
        }
    }
}