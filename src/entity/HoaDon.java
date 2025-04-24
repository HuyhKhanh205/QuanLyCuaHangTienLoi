package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dao.HoaDon_Dao;

public class HoaDon {
    private String maHD;
    private ArrayList<CTHoaDon> dsSanPham;
    private Date ngayTao;

    public HoaDon(String maHD, Date ngayTao) {
        setMaHD(maHD);
        setNgayTao(ngayTao);
        this.dsSanPham = new ArrayList<>();
    }
    
    public HoaDon(String maHD) {
        setMaHD(maHD);
        this.ngayTao = new Date(); // Ngày hiện tại
        this.dsSanPham = new ArrayList<>();
    }

    // Tạo mã hóa đơn tự động
    public static String taoMaHoaDon(HoaDon_Dao hoaDonDao) {
        ArrayList<HoaDon> dsHoaDon = hoaDonDao.findAll();
        int maxSoHD = 0;
        for (HoaDon hd : dsHoaDon) {
            String soHD = hd.getMaHD().replace("HD", "");
            try {
                int so = Integer.parseInt(soHD);
                if (so > maxSoHD) {
                    maxSoHD = so;
                }
            } catch (NumberFormatException ignored) {}
        }
        return String.format("HD%03d", maxSoHD + 1);
    }

    public void themSanPham(SanPham sp, int soLuong, KhachHang khachHang) {
        CTHoaDon ct = new CTHoaDon(this, sp, soLuong, sp.getGia(), khachHang);
        dsSanPham.add(ct);
    }

    public void xoaSanPham(String maSP) {
        dsSanPham.removeIf(ct -> ct.getSanPham().getMaSP().equals(maSP));
    }

    public double tinhTongTien() {
        double tong = 0;
        for (CTHoaDon ct : dsSanPham) {
            tong += ct.tinhTongTien();
        }
        return tong;
    }

    public void apDungKhuyenMai(KhuyenMai km) {
        if (km == null || !km.kiemTraHieuLuc()) {
            throw new IllegalArgumentException("Khuyến mãi không hợp lệ hoặc đã hết hiệu lực.");
        }
        for (CTHoaDon ct : dsSanPham) {
            double donGiaGiam = ct.getDonGia() * (1 - km.getGiaTriGiam() / 100);
            ct.setDonGia(donGiaGiam);
            ct.capNhatSoLuong(ct.getSoLuong()); // Cập nhật lại tổng tiền
        }
    }

    public String layThongTinHoaDon() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Hóa đơn: " + maHD + ", Ngày tạo: " + sdf.format(ngayTao) + ", Tổng tiền: " + tinhTongTien();
    }

    // Getters and Setters
    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng.");
        }
        this.maHD = maHD;
    }

    public ArrayList<CTHoaDon> getDsSanPham() { 
    	return dsSanPham; 
    	}
    public void setDsSanPham(ArrayList<CTHoaDon> dsSanPham) {
        if (dsSanPham == null) {
            throw new IllegalArgumentException("Danh sách sản phẩm không được rỗng.");
        }
        this.dsSanPham = dsSanPham;
    }

    public Date getNgayTao() { 
    	return ngayTao; 
    	}
    public void setNgayTao(Date ngayTao) {
        if (ngayTao == null) {
            throw new IllegalArgumentException("Ngày tạo không được rỗng.");
        }
        this.ngayTao = ngayTao;
    }

}