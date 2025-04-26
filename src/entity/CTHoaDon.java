package entity;

import dao.KhuyenMai_Dao;

public class CTHoaDon {
    private HoaDon hoaDon;
    private SanPham sanPham;
    private int soLuong;
    private double donGia;
    private double tongTien;
    private KhachHang khachHang;
    private String idKM; 

    public CTHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, double donGia, KhachHang khachHang) {
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.khachHang = khachHang;
        this.idKM = null;
    }
    public CTHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, double donGia) {
        this(hoaDon, sanPham, soLuong, donGia, null);
    }
    public void capNhatSoLuong(int soLuong) {
        setSoLuong(soLuong);
        this.tongTien = tinhTongTien();
    }

    public double tinhTongTien() {
        double baseTotal = soLuong * donGia; 
        double finalTotal = baseTotal;

        if (idKM != null) {
            KhuyenMai_Dao khuyenMaiDao = new KhuyenMai_Dao();
            KhuyenMai km = khuyenMaiDao.timKhuyenMai(idKM);
            if (km != null && km.kiemTraHieuLuc()) {
                if (km.getGiaTriGiam() < 1) {
                    finalTotal = baseTotal * (1 - km.getGiaTriGiam());
                } else { 
                    finalTotal = baseTotal - km.getGiaTriGiam();
                }
            }
        }

        if (finalTotal < 0) finalTotal = 0;

        this.tongTien = finalTotal;
        return finalTotal;
    }

    public String layThongTinCTHoaDon() {
        return "CTHD: " + hoaDon.layThongTinHoaDon() + ", SP: " + sanPham.getMaSP() + ", SL: " + soLuong;
    }

    @Override
    public String toString() {
        return hoaDon.layThongTinHoaDon() + "," + sanPham.getMaSP() + "," + soLuong + "," + donGia + "," + tongTien + "," + (idKM != null ? idKM : "Không có KM");
    }

    public HoaDon getHoaDon() { 
    	return hoaDon; 
    	}
    public void setHoaDon(HoaDon hoaDon) {
        if (hoaDon == null) {
            throw new IllegalArgumentException("Hóa đơn không được rỗng.");
        }
        this.hoaDon = hoaDon;
    }

    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("Sản phẩm không được rỗng.");
        }
        this.sanPham = sanPham;
    }

    public int getSoLuong() { 
    	return soLuong; 
    	}
    public void setSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
        this.soLuong = soLuong;
    }

    public double getDonGia() { 
    	return donGia; 
    	}
    public void setDonGia(double donGia) {
        if (donGia < 0) {
            throw new IllegalArgumentException("Đơn giá không được âm.");
        }
        this.donGia = donGia;
    }

    public double getTongTien() { 
    	return tongTien; 
    	}
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public String getIdKM() {
        return idKM;
    }

    public void setIdKM(String idKM) {
        this.idKM = idKM;
    }
}