package entity;

public class NhaCungCap {
    private String mancc;
    private String tenncc;

    public NhaCungCap(String mancc, String tenncc) {
        setMancc(mancc);
        setTenncc(tenncc);
    }

    @Override
    public String toString() {
        return mancc + "," + tenncc;
    }

    // Getters and Setters (giữ nguyên)
    public String getMancc() { return mancc; }
    public void setMancc(String mancc) {
        if (mancc == null || mancc.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhà cung cấp không được rỗng.");
        }
        this.mancc = mancc;
    }

    public String getTenncc() { return tenncc; }
    public void setTenncc(String tenncc) {
        if (tenncc == null || tenncc.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhà cung cấp không được rỗng.");
        }
        this.tenncc = tenncc;
    }
}