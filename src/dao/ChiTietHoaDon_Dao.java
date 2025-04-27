package dao;

import entity.ChiTietHoaDon;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChiTietHoaDon_Dao {
    private List<ChiTietHoaDon> dsChiTietHoaDon;

    public ChiTietHoaDon_Dao() {
        dsChiTietHoaDon = new ArrayList<>();
    }

    public void themChiTietHoaDon(ChiTietHoaDon cthd) {
        dsChiTietHoaDon.add(cthd);
    }

    public List<ChiTietHoaDon> findByMaHD(String maHD) {
        return dsChiTietHoaDon.stream()
                .filter(ct -> ct.getMaHD().equalsIgnoreCase(maHD))
                .collect(Collectors.toList());
    }

    public List<ChiTietHoaDon> getAllChiTietHoaDon() {
        return dsChiTietHoaDon;
    }

    public void clearAll() {
        dsChiTietHoaDon.clear();
    }
}
