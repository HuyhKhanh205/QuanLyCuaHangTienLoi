package entity;

import java.util.ArrayList;

public class DSSanPham {
    private ArrayList<SanPham> dsSanPham;

    public DSSanPham() {
        dsSanPham = new ArrayList<>();
    }

    public ArrayList<SanPham> getDsSanPham() {
        return dsSanPham;
    }

    public void setDsSanPham(ArrayList<SanPham> dsSanPham) {
        this.dsSanPham = dsSanPham;
    }
}