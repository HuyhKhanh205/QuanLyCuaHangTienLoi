package dao;

import connectDB.ConnectDB;
import entity.BaoCao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaoCao_Dao {
	private ArrayList<BaoCao> dsBaoCao;
	private BaoCao bc;
    public void save(BaoCao bc) {
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "INSERT INTO BaoCao (IdBaoCao, LoaiBaoCao, TenBaoCao, NoiDung) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, bc.getIdBaoCao());
            stmt.setString(2, bc.getLoaiBaoCao());
            stmt.setString(3, bc.getTenBaoCao());
            stmt.setString(4, bc.getNoiDung());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lưu báo cáo: " + e.getMessage());
        }
    }

    public ArrayList<BaoCao> findAll() {
        dsBaoCao = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()) {
            String sql = "SELECT IdBaoCao, LoaiBaoCao, TenBaoCao, NoiDung FROM BaoCao";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                	bc = new BaoCao(
                    rs.getString("IdBaoCao"),
                    rs.getString("LoaiBaoCao"),
                    rs.getString("TenBaoCao")
                );
                bc.setNoiDung(rs.getString("NoiDung"));
                dsBaoCao.add(bc);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi tải danh sách báo cáo: " + e.getMessage());
        }
        return dsBaoCao;
    }
}