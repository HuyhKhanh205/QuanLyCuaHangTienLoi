package entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import connectDB.ConnectDB;

public class BaoCao {
    private String idBaoCao;
    private String loaiBaoCao;
    private String tenBaoCao;
    private String noiDung;
    private NhanVien nhanVien; 

    public BaoCao(String idBaoCao, String loaiBaoCao, String tenBaoCao, NhanVien nhanVien) {
        setIdBaoCao(idBaoCao);
        setLoaiBaoCao(loaiBaoCao);
        setTenBaoCao(tenBaoCao);
        setNhanVien(nhanVien);
        this.noiDung = "";
    }

    public void taoBaoCaoDoanhThu(Date tuNgay, Date denNgay) {
        if (tuNgay == null || denNgay == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được rỗng.");
        }
        if (denNgay.before(tuNgay)) {
            throw new IllegalArgumentException("Ngày kết thúc không được trước ngày bắt đầu.");
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder noiDungBuilder = new StringBuilder();
        noiDungBuilder.append("Báo cáo doanh thu từ ").append(sdf.format(tuNgay))
                      .append(" đến ").append(sdf.format(denNgay)).append("\n");

        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT hd.MaHD, hd.NgayTao, SUM(ct.TongTien) as DoanhThu " +
                         "FROM HoaDon hd JOIN CTHoaDon ct ON hd.MaHD = ct.MaHD " +
                         "WHERE hd.NgayTao BETWEEN ? AND ? " +
                         "GROUP BY hd.MaHD, hd.NgayTao";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(tuNgay.getTime()));
            stmt.setDate(2, new java.sql.Date(denNgay.getTime()));
            ResultSet rs = stmt.executeQuery();

            double tongDoanhThu = 0;
            noiDungBuilder.append("Chi tiết:\n");
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                Date ngayTao = rs.getDate("NgayTao");
                double doanhThu = rs.getDouble("DoanhThu");
                tongDoanhThu += doanhThu;
                noiDungBuilder.append("Hóa đơn: ").append(maHD)
                              .append(", Ngày: ").append(sdf.format(ngayTao))
                              .append(", Doanh thu: ").append(doanhThu)
                              .append("\n");
            }
            noiDungBuilder.append("Tổng doanh thu: ").append(tongDoanhThu).append("\n");
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tạo báo cáo doanh thu: " + e.getMessage());
        }

        noiDung = noiDungBuilder.toString();
    }

    public void taoBaoCaoTonKho() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder noiDungBuilder = new StringBuilder();
        noiDungBuilder.append("Báo cáo tồn kho tại thời điểm ").append(sdf.format(new Date())).append("\n");

        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT sp.MaSP, sp.TenSP, sp.SoLuongTon, sp.HSD, dm.TenDanhMuc " +
                         "FROM SanPham sp JOIN DanhMucSP dm ON sp.MaDanhMuc = dm.MaDanhMuc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            noiDungBuilder.append("Chi tiết:\n");
            while (rs.next()) {
                String maSP = rs.getString("MaSP");
                String tenSP = rs.getString("TenSP");
                int soLuongTon = rs.getInt("SoLuongTon");
                Date hsd = rs.getDate("HSD");
                String tenDanhMuc = rs.getString("TenDanhMuc");
                noiDungBuilder.append("Sản phẩm: ").append(maSP)
                              .append(", Tên: ").append(tenSP)
                              .append(", Số lượng tồn: ").append(soLuongTon)
                              .append(", HSD: ").append(sdf.format(hsd))
                              .append(", Danh mục: ").append(tenDanhMuc)
                              .append("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tạo báo cáo tồn kho: " + e.getMessage());
        }

        noiDung = noiDungBuilder.toString();
    }

    public void xuatBaoCao(String dinhDang) {
        if (dinhDang == null || dinhDang.trim().isEmpty()) {
            throw new IllegalArgumentException("Định dạng báo cáo không được rỗng.");
        }
        noiDung += "\nĐịnh dạng xuất: " + dinhDang;
    }

    public String layNoiDungBaoCao() {
        return noiDung;
    }

    @Override
    public String toString() {
        return "BaoCao{" +
               "idBaoCao='" + idBaoCao + '\'' +
               ", loaiBaoCao='" + loaiBaoCao + '\'' +
               ", tenBaoCao='" + tenBaoCao + '\'' +
               ", nhanVien=" + (nhanVien != null ? nhanVien.getTenNhanVien() : "null") +
               ", noiDung='" + noiDung + '\'' +
               '}';
    }

    public String getIdBaoCao() {
        return idBaoCao;
    }

    public void setIdBaoCao(String idBaoCao) {
        if (idBaoCao == null || idBaoCao.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã báo cáo không được rỗng.");
        }
        this.idBaoCao = idBaoCao;
    }

    public String getLoaiBaoCao() {
        return loaiBaoCao;
    }

    public void setLoaiBaoCao(String loaiBaoCao) {
        if (loaiBaoCao == null || loaiBaoCao.trim().isEmpty()) {
            throw new IllegalArgumentException("Loại báo cáo không được rỗng.");
        }
        this.loaiBaoCao = loaiBaoCao;
    }

    public String getTenBaoCao() {
        return tenBaoCao;
    }

    public void setTenBaoCao(String tenBaoCao) {
        if (tenBaoCao == null || tenBaoCao.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên báo cáo không được rỗng.");
        }
        this.tenBaoCao = tenBaoCao;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        if (noiDung == null) {
            throw new IllegalArgumentException("Nội dung báo cáo không được rỗng.");
        }
        this.noiDung = noiDung;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được rỗng.");
        }
        this.nhanVien = nhanVien;
    }
}