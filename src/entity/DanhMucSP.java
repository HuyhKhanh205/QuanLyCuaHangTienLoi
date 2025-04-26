package entity;

public class DanhMucSP {
    private String maDanhMuc;
    private String tenDanhMuc;

    public DanhMucSP(String maDanhMuc, String tenDanhMuc) {
        setMaDanhMuc(maDanhMuc);
        setTenDanhMuc(tenDanhMuc);
    }

    @Override
    public String toString() {
        return "DanhMucSP{" +
               "maDanhMuc='" + maDanhMuc + '\'' +
               ", tenDanhMuc='" + tenDanhMuc + '\'' +
               '}';
    }

    public String getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(String maDanhMuc) {
        if (maDanhMuc == null || maDanhMuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã danh mục không được rỗng.");
        }
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) {
        if (tenDanhMuc == null || tenDanhMuc.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên danh mục không được rỗng.");
        }
        this.tenDanhMuc = tenDanhMuc;
    }
}