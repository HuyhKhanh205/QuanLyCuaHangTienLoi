package entity;

public class NhanVien {
    private String maNV;
    private String tenNhanVien;
    private String chucVu;
    private String sdt;

    public NhanVien(String maNV, String tenNhanVien, String chucVu, String sdt) {
        setMaNV(maNV);
        setTenNhanVien(tenNhanVien);
        setChucVu(chucVu);
        setSdt(sdt);
    }

    public boolean kiemTraPhanQuyen(String chucVu) {
        return this.chucVu != null && this.chucVu.equals(chucVu);
    }

    @Override
    public String toString() {
        return maNV + "," + tenNhanVien + "," + chucVu + "," + sdt;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được rỗng.");
        }
        this.maNV = maNV;
    }

    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) {
        if (tenNhanVien == null || tenNhanVien.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhân viên không được rỗng.");
        }
        this.tenNhanVien = tenNhanVien;
    }

    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) {
        if (chucVu == null || chucVu.trim().isEmpty()) {
            throw new IllegalArgumentException("Chức vụ không được rỗng.");
        }
        this.chucVu = chucVu;
    }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) {
        if (sdt == null || !sdt.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ.");
        }
        this.sdt = sdt;
    }
}