package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SanPham {
    private String maSP;
    private String tenSP;
    private int soLuongTon;
    private Date HSD;
    private NhaCungCap nhaCungCap;
    private double gia;
    private DanhMucSP loai;

    public SanPham(String maSP, String tenSP, int soLuongTon, Date HSD, NhaCungCap nhaCungCap, double gia, DanhMucSP loai) {
        setMaSP(maSP);
        setTenSP(tenSP);
        setSoLuongTon(soLuongTon);
        setHSD(HSD);
        setNhaCungCap(nhaCungCap);
        setGia(gia);
        setLoai(loai);
    }

    public void capNhatSoLuong(int soLuong) {
        this.soLuongTon += soLuong;
        if (this.soLuongTon < 0) {
            this.soLuongTon = 0;
        }
    }

    public double tinhGiaSauGiam(double giamGia) {
        if (giamGia < 0 || giamGia > 100) {
            throw new IllegalArgumentException("Giảm giá phải từ 0 đến 100.");
        }
        return gia - (gia * giamGia / 100);
    }

    public boolean kiemTraHSD() {
        return new Date().before(HSD);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return maSP + "," + tenSP + "," + soLuongTon + "," + sdf.format(HSD) + "," + nhaCungCap.getMancc() + "," + gia + "," + loai.getMaDanhMuc();
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được rỗng.");
        }
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        if (tenSP == null || tenSP.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng.");
        }
        this.tenSP = tenSP;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        if (soLuongTon < 0) {
            throw new IllegalArgumentException("Số lượng tồn không được âm.");
        }
        this.soLuongTon = soLuongTon;
    }

    public Date getHSD() {
        return HSD;
    }

    public void setHSD(Date HSD) {
        if (HSD == null) {
            throw new IllegalArgumentException("Hạn sử dụng không được rỗng.");
        }
        this.HSD = HSD;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        if (nhaCungCap == null) {
            throw new IllegalArgumentException("Nhà cung cấp không được rỗng.");
        }
        this.nhaCungCap = nhaCungCap;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        if (gia < 0) {
            throw new IllegalArgumentException("Giá không được âm.");
        }
        this.gia = gia;
    }

    public DanhMucSP getLoai() {
        return loai;
    }

    public void setLoai(DanhMucSP loai) {
        if (loai == null) {
            throw new IllegalArgumentException("Danh mục không được rỗng.");
        }
        this.loai = loai;
    }
}