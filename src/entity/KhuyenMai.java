package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KhuyenMai {
    private String idKM;
    private String moTa;
    private double giaTriGiam;
    private Date ngayBatDau;
    private Date ngayKetThuc;

    public KhuyenMai(String idKM, String moTa, double giaTriGiam, Date ngayBatDau, Date ngayKetThuc) {
        setIdKM(idKM);
        setMoTa(moTa);
        setGiaTriGiam(giaTriGiam);
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
    }

    public boolean kiemTraHieuLuc() {
        Date now = new Date();
        return now.after(ngayBatDau) && now.before(ngayKetThuc);
    }

    public void apDungKhuyenMai(HoaDon hd) {
        if (kiemTraHieuLuc() && hd != null) {
            hd.apDungKhuyenMai(this);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "KhuyenMai{" +
               "idKM='" + idKM + '\'' +
               ", moTa='" + moTa + '\'' +
               ", giaTriGiam=" + giaTriGiam +
               ", ngayBatDau=" + sdf.format(ngayBatDau) +
               ", ngayKetThuc=" + sdf.format(ngayKetThuc) +
               '}';
    }

    // Getters and Setters
    public String getIdKM() { return idKM; }
    public void setIdKM(String idKM) {
        if (idKM == null || idKM.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mãi không được rỗng.");
        }
        this.idKM = idKM;
    }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) {
        if (moTa == null || moTa.trim().isEmpty()) {
            throw new IllegalArgumentException("Mô tả không được rỗng.");
        }
        this.moTa = moTa;
    }

    public double getGiaTriGiam() { return giaTriGiam; }
    public void setGiaTriGiam(double giaTriGiam) {
        if (giaTriGiam < 0) {
            throw new IllegalArgumentException("Giá trị giảm không được âm.");
        }
        this.giaTriGiam = giaTriGiam;
    }

    public Date getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(Date ngayBatDau) {
        if (ngayBatDau == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không được rỗng.");
        }
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(Date ngayKetThuc) {
        if (ngayKetThuc == null || ngayKetThuc.before(ngayBatDau)) {
            throw new IllegalArgumentException("Ngày kết thúc không hợp lệ.");
        }
        this.ngayKetThuc = ngayKetThuc;
    }
}