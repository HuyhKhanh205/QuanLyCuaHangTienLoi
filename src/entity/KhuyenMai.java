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
            for (CTHoaDon ct : hd.getDsSanPham()) {
                double donGiaGiam;
                if (giaTriGiam < 1) {
                    // Giảm theo phần trăm (0.X)
                    donGiaGiam = ct.getDonGia() * (1 - giaTriGiam);
                } else {
                    // Giảm trực tiếp
                    donGiaGiam = ct.getDonGia() - giaTriGiam;
                    if (donGiaGiam < 0) donGiaGiam = 0; // Đảm bảo đơn giá không âm
                }
                ct.setDonGia(donGiaGiam);
                ct.capNhatSoLuong(ct.getSoLuong()); // Cập nhật lại tổng tiền
            }
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String giam = giaTriGiam < 1 ? (giaTriGiam * 100 + "%") : (giaTriGiam + "");
        return "KhuyenMai{" +
               "idKM='" + idKM + '\'' +
               ", moTa='" + moTa + '\'' +
               ", giaTriGiam=" + giam +
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