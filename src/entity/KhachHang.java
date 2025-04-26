package entity;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String sdt;
    private String email;
    private int diemTichLuy;

    public KhachHang(String maKH, String tenKH, String sdt, String email, int diemTichLuy) {
        setMaKH(maKH);
        setTenKH(tenKH);
        setSdt(sdt);
        setEmail(email);
        setDiemTichLuy(diemTichLuy);
    }

    public void congDiemTichLuy(int diem) {
        int newDiem = this.diemTichLuy + diem;
        if (newDiem < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không thể âm.");
        }
        this.diemTichLuy = newDiem;
    }

    @Override
    public String toString() {
        return maKH + "," + tenKH + "," + sdt + "," + email + "," + diemTichLuy;
    }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được rỗng.");
        }
        this.maKH = maKH;
    }

    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) {
        if (tenKH == null || tenKH.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được rỗng.");
        }
        this.tenKH = tenKH;
    }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) {
        if (sdt == null || !sdt.matches("\\d{10,11}")) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ.");
        }
        this.sdt = sdt;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Email không hợp lệ.");
        }
        this.email = email;
    }

    public int getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(int diemTichLuy) {
        if (diemTichLuy < 0) {
            throw new IllegalArgumentException("Điểm tích lũy không được âm.");
        }
        this.diemTichLuy = diemTichLuy;
    }
}