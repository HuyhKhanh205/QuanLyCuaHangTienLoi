package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dao.HoaDon_Dao;

public class HoaDon {
    private String maHD;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private ArrayList<CTHoaDon> dsSanPham;
    private Date ngayTao;
    private int diemSuDung;

    public HoaDon(String maHD, KhachHang khachHang, Date ngayTao) {
        setMaHD(maHD);
        setKhachHang(khachHang);
        setNgayTao(ngayTao);
        this.dsSanPham = new ArrayList<>();
    }
    
    public HoaDon(String maHD, KhachHang khachHang, NhanVien nhanVien) {
        setMaHD(maHD);
        setKhachHang(khachHang);
        setNhanVien(nhanVien);
        this.ngayTao = new Date();
        this.dsSanPham = new ArrayList<>();
    }
    public HoaDon(String maHD) {
        setMaHD(maHD);
        this.ngayTao = new Date();
        this.dsSanPham = new ArrayList<>();
    }

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
            tong += ct.getTongTien();
        }
        if (tong < 0) tong = 0;
        return tong;
    }

    public int getDiemSuDung() {
        return diemSuDung;
    }

    public void setDiemSuDung(int diemSuDung) {
        if (diemSuDung < 0) {
            throw new IllegalArgumentException("Điểm sử dụng không được âm.");
        }
        this.diemSuDung = diemSuDung;
    }

    public String layThongTinHoaDon() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Hóa đơn: " + maHD + ", Khách hàng: " + (khachHang != null ? khachHang.getTenKH() : "Không xác định") + ", Ngày tạo: " + sdf.format(ngayTao) + ", Tổng tiền: " + tinhTongTien();
    }

    public String getMaHD() { 
    	return maHD; 
    }
    
    public void setMaHD(String maHD) {
        if (maHD == null || maHD.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng.");
        }
        this.maHD = maHD;
    }

    public KhachHang getKhachHang() { 
    	return khachHang; 
    }
    
    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
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
    
    public NhanVien getNhanVien() {
    	return nhanVien; 
    }
    
    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
}