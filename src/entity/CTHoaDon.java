package entity;

public class CTHoaDon {
    private HoaDon hoaDon;
    private SanPham sanPham;
    private int soLuong;
    private double donGia;
    private double tongTien;

    public CTHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuong, double donGia) {
        setHoaDon(hoaDon);
        setSanPham(sanPham);
        setSoLuong(soLuong);
        setDonGia(donGia);
        this.tongTien = tinhTongTien();
    }

    public void capNhatSoLuong(int soLuong) {
        setSoLuong(soLuong);
        this.tongTien = tinhTongTien();
    }

    public double tinhTongTien() {
        return soLuong * donGia;
    }

    public String layThongTinCTHoaDon() {
        return "CTHD: " + hoaDon.layThongTinHoaDon() + ", SP: " + sanPham.getMaSP() + ", SL: " + soLuong;
    }

    @Override
    public String toString() {
        return hoaDon.layThongTinHoaDon() + "," + sanPham.getMaSP() + "," + soLuong + "," + donGia + "," + tongTien;
    }

    // Getters and Setters (giữ nguyên)
    public HoaDon getHoaDon() { return hoaDon; }
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

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0.");
        }
        this.soLuong = soLuong;
    }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) {
        if (donGia < 0) {
            throw new IllegalArgumentException("Đơn giá không được âm.");
        }
        this.donGia = donGia;
    }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}