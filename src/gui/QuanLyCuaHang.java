package gui;

import dao.*;
import entity.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLyCuaHang extends JFrame {
    private DSSanPham dsSanPham;
    private ArrayList<NhaCungCap> dsNhaCungCap;
    private ArrayList<DanhMucSP> dsDanhMuc;
    private ArrayList<KhuyenMai> dsKhuyenMai;
    private ArrayList<HoaDon> dsHoaDon;
    private ArrayList<KhachHang> dsKhachHang;
    private ArrayList<NhanVien> dsNhanVien;

    private JTable sanPhamTable, nhaCungCapTable, danhMucTable, khachHangTable, nhanVienTable, khuyenMaiTable, hoaDonTable;
    private DefaultTableModel sanPhamTableModel, nhaCungCapTableModel, danhMucTableModel, khachHangTableModel, nhanVienTableModel, khuyenMaiTableModel, hoaDonTableModel;

    private JPanel currentPanel;
    private JPanel mainPanel;

    private JTextField maSPField, tenSPField, soLuongField, hsdField, giaField;
    private JComboBox<String> nhaCungCapComboBox, danhMucComboBox;
    private JTextField maNCCFieldForm, tenNCCFieldForm;
    private JTextField maDanhMucField, tenDanhMucField;
    private JTextField maKHField, tenKHField, sdtKHField, emailKHField, diemTichLuyField;
    private JTextField maNVField, tenNVField, chucVuField, sdtNVField;
    private JTextField idKMField, moTaKMField, giaTriGiamField, ngayBatDauKMField, ngayKetThucKMField;
    private JTextField maHDField, ngayTaoHDField;

    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();
    private DanhMucSP_Dao danhMucDao = new DanhMucSP_Dao();
    private KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();
    private KhuyenMai_Dao khuyenMaiDao = new KhuyenMai_Dao();
    private HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private DSSanPham_Dao dsSanPhamDao = new DSSanPham_Dao();
    private BaoCao_Dao baoCaoDao = new BaoCao_Dao();

    public QuanLyCuaHang() {
        dsSanPham = new DSSanPham();
        dsSanPham.setDsSanPham(sanPhamDao.findAll());
        dsNhaCungCap = nhaCungCapDao.findAll();
        dsDanhMuc = danhMucDao.findAll();
        dsKhuyenMai = khuyenMaiDao.findAll();
        dsHoaDon = hoaDonDao.findAll();
        dsKhachHang = khachHangDao.findAll();
        dsNhanVien = nhanVienDao.findAll();
        initUI();
    }

    private void initUI() {
        setTitle("Quản Lý Cửa Hàng");
        setSize(1100, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Quản Lý Sản Phẩm
        JMenu sanPhamMenu = new JMenu("Quản Lý Sản Phẩm");
        JMenuItem spNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        JMenuItem spNhapKho = new JMenuItem("Nhập Kho");
        JMenuItem spXuatKho = new JMenuItem("Xuất Kho");
        JMenuItem spTheoDanhMuc = new JMenuItem("Xem Theo Danh Mục");
        sanPhamMenu.add(spNhapTrucTiep);
        sanPhamMenu.add(spNhapKho);
        sanPhamMenu.add(spXuatKho);
        sanPhamMenu.add(spTheoDanhMuc);

        // Quản Lý Nhà Cung Cấp
        JMenu nhaCungCapMenu = new JMenu("Quản Lý Nhà Cung Cấp");
        JMenuItem nccNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        nhaCungCapMenu.add(nccNhapTrucTiep);

        // Quản Lý Danh Mục
        JMenu danhMucMenu = new JMenu("Quản Lý Danh Mục");
        JMenuItem dmNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        danhMucMenu.add(dmNhapTrucTiep);

        // Quản Lý Khách Hàng
        JMenu khachHangMenu = new JMenu("Quản Lý Khách Hàng");
        JMenuItem khNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        JMenuItem khCongDiem = new JMenuItem("Cộng Điểm Tích Lũy");
        JMenuItem khSuDungDiem = new JMenuItem("Sử Dụng Điểm Tích Lũy");
        khachHangMenu.add(khNhapTrucTiep);
        khachHangMenu.add(khCongDiem);
        khachHangMenu.add(khSuDungDiem);

        // Quản Lý Nhân Viên
        JMenu nhanVienMenu = new JMenu("Quản Lý Nhân Viên");
        JMenuItem nvNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        nhanVienMenu.add(nvNhapTrucTiep);

        // Quản Lý Khuyến Mãi
        JMenu khuyenMaiMenu = new JMenu("Quản Lý Khuyến Mãi");
        JMenuItem kmNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        khuyenMaiMenu.add(kmNhapTrucTiep);

        // Quản Lý Hóa Đơn
        JMenu hoaDonMenu = new JMenu("Quản Lý Hóa Đơn");
        JMenuItem hdNhapTrucTiep = new JMenuItem("Nhập Trực Tiếp");
        JMenuItem hdThanhToan = new JMenuItem("Thanh Toán");
        JMenuItem hdApDungKM = new JMenuItem("Áp Dụng Khuyến Mãi");
        hoaDonMenu.add(hdNhapTrucTiep);
        hoaDonMenu.add(hdThanhToan);
        hoaDonMenu.add(hdApDungKM);

        // Báo Cáo
        JMenu baoCaoMenu = new JMenu("Báo Cáo");
        JMenuItem baoCaoTongQuan = new JMenuItem("Xem Báo Cáo Tổng Quan");
        JMenuItem baoCaoDoanhThu = new JMenuItem("Báo Cáo Doanh Thu");
        JMenuItem baoCaoTonKho = new JMenuItem("Báo Cáo Tồn Kho");
        baoCaoMenu.add(baoCaoTongQuan);
        baoCaoMenu.add(baoCaoDoanhThu);
        baoCaoMenu.add(baoCaoTonKho);

        menuBar.add(sanPhamMenu);
        menuBar.add(nhaCungCapMenu);
        menuBar.add(danhMucMenu);
        menuBar.add(khachHangMenu);
        menuBar.add(nhanVienMenu);
        menuBar.add(khuyenMaiMenu);
        menuBar.add(hoaDonMenu);
        menuBar.add(baoCaoMenu);
        setJMenuBar(menuBar);
        
        JLabel background= new JLabel(new ImageIcon("D:\\Java\\IMG\\AnhoLe_PC.png"));
        add(background);
        background.setLayout(new FlowLayout());
        

        mainPanel = new JPanel(new BorderLayout());

        // Initialize tables
        String[] spColumns = {"Mã SP", "Tên SP", "Số Lượng", "HSD", "Nhà Cung Cấp", "Giá", "Loại SP"};
        sanPhamTableModel = new DefaultTableModel(spColumns, 0);
        sanPhamTable = new JTable(sanPhamTableModel);
        loadSanPhamTableData();

        String[] nccColumns = {"Mã NCC", "Tên NCC"};
        nhaCungCapTableModel = new DefaultTableModel(nccColumns, 0);
        nhaCungCapTable = new JTable(nhaCungCapTableModel);
        loadNhaCungCapTableData();

        String[] dmColumns = {"Mã Danh Mục", "Tên Danh Mục"};
        danhMucTableModel = new DefaultTableModel(dmColumns, 0);
        danhMucTable = new JTable(danhMucTableModel);
        loadDanhMucTableData();

        String[] khColumns = {"Mã KH", "Tên KH", "Số Điện Thoại", "Email", "Điểm Tích Lũy"};
        khachHangTableModel = new DefaultTableModel(khColumns, 0);
        khachHangTable = new JTable(khachHangTableModel);
        loadKhachHangTableData();

        String[] nvColumns = {"Mã NV", "Tên NV", "Chức Vụ", "Số Điện Thoại"};
        nhanVienTableModel = new DefaultTableModel(nvColumns, 0);
        nhanVienTable = new JTable(nhanVienTableModel);
        loadNhanVienTableData();

        String[] kmColumns = {"ID KM", "Mô Tả", "Giá Trị Giảm", "Ngày Bắt Đầu", "Ngày Kết Thúc"};
        khuyenMaiTableModel = new DefaultTableModel(kmColumns, 0);
        khuyenMaiTable = new JTable(khuyenMaiTableModel);
        loadKhuyenMaiTableData();

        String[] hdColumns = {"Mã HD", "Ngày Tạo", "Tổng Tiền"};
        hoaDonTableModel = new DefaultTableModel(hdColumns, 0);
        hoaDonTable = new JTable(hoaDonTableModel);
        loadHoaDonTableData();

        currentPanel = createSanPhamPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);

        // Action listeners
        spNhapTrucTiep.addActionListener(e -> showSanPhamPanel());
        spNhapKho.addActionListener(e -> nhapKho());
        spXuatKho.addActionListener(e -> xuatKho());
        spTheoDanhMuc.addActionListener(e -> xemTheoDanhMuc());
        nccNhapTrucTiep.addActionListener(e -> showNhaCungCapPanel());
        dmNhapTrucTiep.addActionListener(e -> showDanhMucPanel());
        khNhapTrucTiep.addActionListener(e -> showKhachHangPanel());
        khCongDiem.addActionListener(e -> congDiemTichLuy());
        khSuDungDiem.addActionListener(e -> suDungDiemTichLuy());
        nvNhapTrucTiep.addActionListener(e -> showNhanVienPanel());
        kmNhapTrucTiep.addActionListener(e -> showKhuyenMaiPanel());
        hdNhapTrucTiep.addActionListener(e -> showHoaDonPanel());
        hdApDungKM.addActionListener(e -> apDungKhuyenMai());
        baoCaoTongQuan.addActionListener(e -> showBaoCaoPanel());
        baoCaoDoanhThu.addActionListener(e -> taoBaoCaoDoanhThu());
        baoCaoTonKho.addActionListener(e -> taoBaoCaoTonKho());

        background.add(mainPanel);
    }

    // Panels
    private JPanel createSanPhamPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(sanPhamTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã SP:"));
        maSPField = new JTextField(15);
        formPanel.add(maSPField);

        formPanel.add(new JLabel("Tên SP:"));
        tenSPField = new JTextField(15);
        formPanel.add(tenSPField);

        formPanel.add(new JLabel("Số Lượng:"));
        soLuongField = new JTextField(15);
        formPanel.add(soLuongField);

        formPanel.add(new JLabel("HSD (dd/MM/yyyy):"));
        hsdField = new JTextField(15);
        formPanel.add(hsdField);

        formPanel.add(new JLabel("Nhà Cung Cấp:"));
        nhaCungCapComboBox = new JComboBox<>();
        updateNhaCungCapComboBox();
        formPanel.add(nhaCungCapComboBox);

        formPanel.add(new JLabel("Giá:"));
        giaField = new JTextField(15);
        formPanel.add(giaField);

        formPanel.add(new JLabel("Loại SP:"));
        danhMucComboBox = new JComboBox<>();
        updateDanhMucComboBox();
        formPanel.add(danhMucComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập Nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themSanPham());
        updateButton.addActionListener(e -> capNhatSanPham());
        deleteButton.addActionListener(e -> xoaSanPham());
        clearButton.addActionListener(e -> clearSanPhamForm());

        sanPhamTable.getSelectionModel().addListSelectionListener(e -> selectSanPham());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNhaCungCapPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(nhaCungCapTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã NCC:"));
        maNCCFieldForm = new JTextField(15);
        formPanel.add(maNCCFieldForm);

        formPanel.add(new JLabel("Tên NCC:"));
        tenNCCFieldForm = new JTextField(15);
        formPanel.add(tenNCCFieldForm);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themNhaCungCap());
        deleteButton.addActionListener(e -> xoaNhaCungCap());
        clearButton.addActionListener(e -> clearNhaCungCapForm());

        nhaCungCapTable.getSelectionModel().addListSelectionListener(e -> selectNhaCungCap());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDanhMucPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(danhMucTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã Danh Mục:"));
        maDanhMucField = new JTextField(15);
        formPanel.add(maDanhMucField);

        formPanel.add(new JLabel("Tên Danh Mục:"));
        tenDanhMucField = new JTextField(15);
        formPanel.add(tenDanhMucField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themDanhMuc());
        deleteButton.addActionListener(e -> xoaDanhMuc());
        clearButton.addActionListener(e -> clearDanhMucForm());

        danhMucTable.getSelectionModel().addListSelectionListener(e -> selectDanhMuc());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createKhachHangPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(khachHangTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã KH:"));
        maKHField = new JTextField(15);
        formPanel.add(maKHField);

        formPanel.add(new JLabel("Tên KH:"));
        tenKHField = new JTextField(15);
        formPanel.add(tenKHField);

        formPanel.add(new JLabel("Số Điện Thoại:"));
        sdtKHField = new JTextField(15);
        formPanel.add(sdtKHField);

        formPanel.add(new JLabel("Email:"));
        emailKHField = new JTextField(15);
        formPanel.add(emailKHField);

        formPanel.add(new JLabel("Điểm Tích Lũy:"));
        diemTichLuyField = new JTextField(15);
        formPanel.add(diemTichLuyField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập Nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themKhachHang());
        updateButton.addActionListener(e -> capNhatKhachHang());
        deleteButton.addActionListener(e -> xoaKhachHang());
        clearButton.addActionListener(e -> clearKhachHangForm());

        khachHangTable.getSelectionModel().addListSelectionListener(e -> selectKhachHang());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNhanVienPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(nhanVienTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã NV:"));
        maNVField = new JTextField(15);
        formPanel.add(maNVField);

        formPanel.add(new JLabel("Tên NV:"));
        tenNVField = new JTextField(15);
        formPanel.add(tenNVField);

        formPanel.add(new JLabel("Chức Vụ:"));
        chucVuField = new JTextField(15);
        formPanel.add(chucVuField);

        formPanel.add(new JLabel("Số Điện Thoại:"));
        sdtNVField = new JTextField(15);
        formPanel.add(sdtNVField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập Nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themNhanVien());
        updateButton.addActionListener(e -> capNhatNhanVien());
        deleteButton.addActionListener(e -> xoaNhanVien());
        clearButton.addActionListener(e -> clearNhanVienForm());

        nhanVienTable.getSelectionModel().addListSelectionListener(e -> selectNhanVien());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createKhuyenMaiPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(khuyenMaiTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("ID KM:"));
        idKMField = new JTextField(15);
        formPanel.add(idKMField);

        formPanel.add(new JLabel("Mô Tả:"));
        moTaKMField = new JTextField(15);
        formPanel.add(moTaKMField);

        formPanel.add(new JLabel("Giá Trị Giảm:"));
        giaTriGiamField = new JTextField(15);
        formPanel.add(giaTriGiamField);

        formPanel.add(new JLabel("Ngày Bắt Đầu (dd/MM/yyyy):"));
        ngayBatDauKMField = new JTextField(15);
        formPanel.add(ngayBatDauKMField);

        formPanel.add(new JLabel("Ngày Kết Thúc (dd/MM/yyyy):"));
        ngayKetThucKMField = new JTextField(15);
        formPanel.add(ngayKetThucKMField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton updateButton = new JButton("Cập Nhật");
        JButton deleteButton = new JButton("Xóa");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themKhuyenMai());
        updateButton.addActionListener(e -> capNhatKhuyenMai());
        deleteButton.addActionListener(e -> xoaKhuyenMai());
        clearButton.addActionListener(e -> clearKhuyenMaiForm());

        khuyenMaiTable.getSelectionModel().addListSelectionListener(e -> selectKhuyenMai());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHoaDonPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(hoaDonTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã HD:"));
        maHDField = new JTextField(15);
        formPanel.add(maHDField);

        formPanel.add(new JLabel("Ngày Tạo (dd/MM/yyyy):"));
        ngayTaoHDField = new JTextField(15);
        formPanel.add(ngayTaoHDField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Thêm");
        JButton clearButton = new JButton("Xóa Form");

        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themHoaDon());
        clearButton.addActionListener(e -> clearHoaDonForm());

        hoaDonTable.getSelectionModel().addListSelectionListener(e -> selectHoaDon());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBaoCaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea baoCaoTextArea = new JTextArea();
        baoCaoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(baoCaoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        ArrayList<BaoCao> dsBaoCao = baoCaoDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (BaoCao bc : dsBaoCao) {
            sb.append("ID: ").append(bc.getIdBaoCao())
              .append(", Loại: ").append(bc.getLoaiBaoCao())
              .append(", Tên: ").append(bc.getTenBaoCao())
              .append("\nNội dung: ").append(bc.getNoiDung())
              .append("\n----------------------------------------\n");
        }
        baoCaoTextArea.setText(sb.toString());

        return panel;
    }

    // Load table data
    private void loadSanPhamTableData() {
        sanPhamTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (SanPham sp : dsSanPham.getDsSanPham()) {
            sanPhamTableModel.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getSoLuongTon(),
                sdf.format(sp.getHSD()),
                sp.getNhaCungCap().getTenncc(),
                sp.getGia(),
                sp.getLoai().getTenDanhMuc() 
            });
        }
    }

    private void loadNhaCungCapTableData() {
        nhaCungCapTableModel.setRowCount(0);
        for (NhaCungCap ncc : dsNhaCungCap) {
            nhaCungCapTableModel.addRow(new Object[]{ncc.getMancc(), ncc.getTenncc()});
        }
    }

    private void loadDanhMucTableData() {
        danhMucTableModel.setRowCount(0);
        for (DanhMucSP dm : dsDanhMuc) {
            danhMucTableModel.addRow(new Object[]{dm.getMaDanhMuc(), dm.getTenDanhMuc()});
        }
    }

    private void loadKhachHangTableData() {
        khachHangTableModel.setRowCount(0);
        for (KhachHang kh : dsKhachHang) {
            khachHangTableModel.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSdt(),
                kh.getEmail(),
                kh.getDiemTichLuy()
            });
        }
    }

    private void loadNhanVienTableData() {
        nhanVienTableModel.setRowCount(0);
        for (NhanVien nv : dsNhanVien) {
            nhanVienTableModel.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNhanVien(),
                nv.getChucVu(),
                nv.getSdt()
            });
        }
    }

    private void loadKhuyenMaiTableData() {
        khuyenMaiTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (KhuyenMai km : dsKhuyenMai) {
            khuyenMaiTableModel.addRow(new Object[]{
                km.getIdKM(),
                km.getMoTa(),
                km.getGiaTriGiam(),
                sdf.format(km.getNgayBatDau()),
                sdf.format(km.getNgayKetThuc())
            });
        }
    }

    private void loadHoaDonTableData() {
        hoaDonTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (HoaDon hd : dsHoaDon) {
            double tongTien = hd.tinhTongTien();
            hoaDonTableModel.addRow(new Object[]{
                hd.getMaHD(),
                sdf.format(hd.getNgayTao()),
                tongTien
            });
        }
    }

    // Update combo boxes
    private void updateNhaCungCapComboBox() {
        nhaCungCapComboBox.removeAllItems();
        for (NhaCungCap ncc : dsNhaCungCap) {
            nhaCungCapComboBox.addItem(ncc.getMancc() + " - " + ncc.getTenncc());
        }
    }

    private void updateDanhMucComboBox() {
        danhMucComboBox.removeAllItems();
        for (DanhMucSP dm : dsDanhMuc) {
            danhMucComboBox.addItem(dm.getMaDanhMuc() + " - " + dm.getTenDanhMuc());
        }
    }

    // Form actions
    private void themSanPham() {
        try {
            String maSP = maSPField.getText();
            String tenSP = tenSPField.getText();
            int soLuong = Integer.parseInt(soLuongField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date hsd = sdf.parse(hsdField.getText());
            String nccStr = (String) nhaCungCapComboBox.getSelectedItem();
            String maNCC = nccStr.split(" - ")[0];
            double gia = Double.parseDouble(giaField.getText());
            String dmStr = (String) danhMucComboBox.getSelectedItem();
            String maDanhMuc = dmStr.split(" - ")[0];

            NhaCungCap ncc = nhaCungCapDao.findByMaNCC(maNCC);
            DanhMucSP dm = danhMucDao.findByMaDanhMuc(maDanhMuc);
            SanPham sp = new SanPham(maSP, tenSP, soLuong, hsd, ncc, gia, dm);
            dsSanPhamDao.themSanPham(sp);
            dsSanPham.getDsSanPham().add(sp);
            loadSanPhamTableData();
            clearSanPhamForm();
            JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void capNhatSanPham() {
        try {
            String maSP = maSPField.getText();
            String tenSP = tenSPField.getText();
            int soLuong = Integer.parseInt(soLuongField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date hsd = sdf.parse(hsdField.getText());
            String nccStr = (String) nhaCungCapComboBox.getSelectedItem();
            String maNCC = nccStr.split(" - ")[0];
            double gia = Double.parseDouble(giaField.getText());
            String dmStr = (String) danhMucComboBox.getSelectedItem();
            String maDanhMuc = dmStr.split(" - ")[0];

            NhaCungCap ncc = nhaCungCapDao.findByMaNCC(maNCC);
            DanhMucSP dm = danhMucDao.findByMaDanhMuc(maDanhMuc);
            SanPham sp = new SanPham(maSP, tenSP, soLuong, hsd, ncc, gia, dm);
            dsSanPhamDao.capNhatSanPham(sp);
            dsSanPham.setDsSanPham(sanPhamDao.findAll());
            loadSanPhamTableData();
            clearSanPhamForm();
            JOptionPane.showMessageDialog(this, "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaSanPham() {
        int selectedRow = sanPhamTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maSP = (String) sanPhamTableModel.getValueAt(selectedRow, 0);
            dsSanPhamDao.xoaSanPham(maSP);
            dsSanPham.setDsSanPham(sanPhamDao.findAll());
            loadSanPhamTableData();
            clearSanPhamForm();
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
        }
    }

    private void clearSanPhamForm() {
        maSPField.setText("");
        tenSPField.setText("");
        soLuongField.setText("");
        hsdField.setText("");
        giaField.setText("");
        nhaCungCapComboBox.setSelectedIndex(0);
        danhMucComboBox.setSelectedIndex(0);
    }

    private void selectSanPham() {
        int selectedRow = sanPhamTable.getSelectedRow();
        if (selectedRow >= 0) {
            maSPField.setText((String) sanPhamTableModel.getValueAt(selectedRow, 0));
            tenSPField.setText((String) sanPhamTableModel.getValueAt(selectedRow, 1));
            soLuongField.setText(sanPhamTableModel.getValueAt(selectedRow, 2).toString());
            hsdField.setText((String) sanPhamTableModel.getValueAt(selectedRow, 3));
            giaField.setText(sanPhamTableModel.getValueAt(selectedRow, 5).toString());
            String ncc = (String) sanPhamTableModel.getValueAt(selectedRow, 4);
            String dm = (String) sanPhamTableModel.getValueAt(selectedRow, 6);
            for (int i = 0; i < nhaCungCapComboBox.getItemCount(); i++) {
                if (nhaCungCapComboBox.getItemAt(i).contains(ncc)) {
                    nhaCungCapComboBox.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 0; i < danhMucComboBox.getItemCount(); i++) {
                if (danhMucComboBox.getItemAt(i).contains(dm)) {
                    danhMucComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void themNhaCungCap() {
        try {
            String maNCC = maNCCFieldForm.getText();
            String tenNCC = tenNCCFieldForm.getText();
            NhaCungCap ncc = new NhaCungCap(maNCC, tenNCC);
            nhaCungCapDao.themNhaCungCap(ncc);
            dsNhaCungCap.add(ncc);
            loadNhaCungCapTableData();
            updateNhaCungCapComboBox();
            clearNhaCungCapForm();
            JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaNhaCungCap() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNCC = (String) nhaCungCapTableModel.getValueAt(selectedRow, 0);
            nhaCungCapDao.xoaNhaCungCap(maNCC);
            dsNhaCungCap.removeIf(ncc -> ncc.getMancc().equals(maNCC));
            loadNhaCungCapTableData();
            updateNhaCungCapComboBox();
            clearNhaCungCapForm();
            JOptionPane.showMessageDialog(this, "Xóa nhà cung cấp thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp để xóa!");
        }
    }

    private void clearNhaCungCapForm() {
        maNCCFieldForm.setText("");
        tenNCCFieldForm.setText("");
    }

    private void selectNhaCungCap() {
        int selectedRow = nhaCungCapTable.getSelectedRow();
        if (selectedRow >= 0) {
            maNCCFieldForm.setText((String) nhaCungCapTableModel.getValueAt(selectedRow, 0));
            tenNCCFieldForm.setText((String) nhaCungCapTableModel.getValueAt(selectedRow, 1));
        }
    }

    private void themDanhMuc() {
        try {
            String maDanhMuc = maDanhMucField.getText();
            String tenDanhMuc = tenDanhMucField.getText();
            DanhMucSP dm = new DanhMucSP(maDanhMuc, tenDanhMuc);
            danhMucDao.themDanhMuc(dm);
            dsDanhMuc.add(dm);
            loadDanhMucTableData();
            updateDanhMucComboBox();
            clearDanhMucForm();
            JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaDanhMuc() {
        int selectedRow = danhMucTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maDanhMuc = (String) danhMucTableModel.getValueAt(selectedRow, 0);
            danhMucDao.xoaDanhMuc(maDanhMuc);
            dsDanhMuc.removeIf(dm -> dm.getMaDanhMuc().equals(maDanhMuc));
            loadDanhMucTableData();
            updateDanhMucComboBox();
            clearDanhMucForm();
            JOptionPane.showMessageDialog(this, "Xóa danh mục thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục để xóa!");
        }
    }

    private void clearDanhMucForm() {
        maDanhMucField.setText("");
        tenDanhMucField.setText("");
    }

    private void selectDanhMuc() {
        int selectedRow = danhMucTable.getSelectedRow();
        if (selectedRow >= 0) {
            maDanhMucField.setText((String) danhMucTableModel.getValueAt(selectedRow, 0));
            tenDanhMucField.setText((String) danhMucTableModel.getValueAt(selectedRow, 1));
        }
    }

    private void themKhachHang() {
        try {
            String maKH = maKHField.getText();
            String tenKH = tenKHField.getText();
            String sdt = sdtKHField.getText();
            String email = emailKHField.getText();
            int diemTichLuy = Integer.parseInt(diemTichLuyField.getText());
            KhachHang kh = new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
            khachHangDao.themKhachHang(kh);
            dsKhachHang.add(kh);
            loadKhachHangTableData();
            clearKhachHangForm();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void capNhatKhachHang() {
        try {
            String maKH = maKHField.getText();
            String tenKH = tenKHField.getText();
            String sdt = sdtKHField.getText();
            String email = emailKHField.getText();
            int diemTichLuy = Integer.parseInt(diemTichLuyField.getText());
            KhachHang kh = new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
            khachHangDao.capNhatKhachHang(kh);
            dsKhachHang = khachHangDao.findAll();
            loadKhachHangTableData();
            clearKhachHangForm();
            JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaKhachHang() {
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maKH = (String) khachHangTableModel.getValueAt(selectedRow, 0);
            khachHangDao.xoaKhachHang(maKH);
            dsKhachHang.removeIf(kh -> kh.getMaKH().equals(maKH));
            loadKhachHangTableData();
            clearKhachHangForm();
            JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
        }
    }

    private void clearKhachHangForm() {
        maKHField.setText("");
        tenKHField.setText("");
        sdtKHField.setText("");
        emailKHField.setText("");
        diemTichLuyField.setText("");
    }

    private void selectKhachHang() {
        int selectedRow = khachHangTable.getSelectedRow();
        if (selectedRow >= 0) {
            maKHField.setText((String) khachHangTableModel.getValueAt(selectedRow, 0));
            tenKHField.setText((String) khachHangTableModel.getValueAt(selectedRow, 1));
            sdtKHField.setText((String) khachHangTableModel.getValueAt(selectedRow, 2));
            emailKHField.setText((String) khachHangTableModel.getValueAt(selectedRow, 3));
            diemTichLuyField.setText(khachHangTableModel.getValueAt(selectedRow, 4).toString());
        }
    }

    private void themNhanVien() {
        try {
            String maNV = maNVField.getText();
            String tenNV = tenNVField.getText();
            String chucVu = chucVuField.getText();
            String sdt = sdtNVField.getText();
            NhanVien nv = new NhanVien(maNV, tenNV, chucVu, sdt);
            nhanVienDao.themNhanVien(nv);
            dsNhanVien.add(nv);
            loadNhanVienTableData();
            clearNhanVienForm();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void capNhatNhanVien() {
        try {
            String maNV = maNVField.getText();
            String tenNV = tenNVField.getText();
            String chucVu = chucVuField.getText();
            String sdt = sdtNVField.getText();
            NhanVien nv = new NhanVien(maNV, tenNV, chucVu, sdt);
            nhanVienDao.capNhatNhanVien(nv);
            dsNhanVien = nhanVienDao.findAll();
            loadNhanVienTableData();
            clearNhanVienForm();
            JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maNV = (String) nhanVienTableModel.getValueAt(selectedRow, 0);
            nhanVienDao.xoaNhanVien(maNV);
            dsNhanVien.removeIf(nv -> nv.getMaNV().equals(maNV));
            loadNhanVienTableData();
            clearNhanVienForm();
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa!");
        }
    }

    private void clearNhanVienForm() {
        maNVField.setText("");
        tenNVField.setText("");
        chucVuField.setText("");
        sdtNVField.setText("");
    }

    private void selectNhanVien() {
        int selectedRow = nhanVienTable.getSelectedRow();
        if (selectedRow >= 0) {
            maNVField.setText((String) nhanVienTableModel.getValueAt(selectedRow, 0));
            tenNVField.setText((String) nhanVienTableModel.getValueAt(selectedRow, 1));
            chucVuField.setText((String) nhanVienTableModel.getValueAt(selectedRow, 2));
            sdtNVField.setText((String) nhanVienTableModel.getValueAt(selectedRow, 3));
        }
    }

    private void themKhuyenMai() {
        try {
            String idKM = idKMField.getText();
            String moTa = moTaKMField.getText();
            double giaTriGiam = Double.parseDouble(giaTriGiamField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayBatDau = sdf.parse(ngayBatDauKMField.getText());
            Date ngayKetThuc = sdf.parse(ngayKetThucKMField.getText());
            KhuyenMai km = new KhuyenMai(idKM, moTa, giaTriGiam, ngayBatDau, ngayKetThuc);
            khuyenMaiDao.themKhuyenMai(km);
            dsKhuyenMai.add(km);
            loadKhuyenMaiTableData();
            clearKhuyenMaiForm();
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void capNhatKhuyenMai() {
        try {
            String idKM = idKMField.getText();
            String moTa = moTaKMField.getText();
            double giaTriGiam = Double.parseDouble(giaTriGiamField.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayBatDau = sdf.parse(ngayBatDauKMField.getText());
            Date ngayKetThuc = sdf.parse(ngayKetThucKMField.getText());
            KhuyenMai km = new KhuyenMai(idKM, moTa, giaTriGiam, ngayBatDau, ngayKetThuc);
            khuyenMaiDao.capNhatKhuyenMai(km);
            dsKhuyenMai = khuyenMaiDao.findAll();
            loadKhuyenMaiTableData();
            clearKhuyenMaiForm();
            JOptionPane.showMessageDialog(this, "Cập nhật khuyến mãi thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow >= 0) {
            String idKM = (String) khuyenMaiTableModel.getValueAt(selectedRow, 0);
            khuyenMaiDao.xoaKhuyenMai(idKM);
            dsKhuyenMai.removeIf(km -> km.getIdKM().equals(idKM));
            loadKhuyenMaiTableData();
            clearKhuyenMaiForm();
            JOptionPane.showMessageDialog(this, "Xóa khuyến mãi thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi để xóa!");
        }
    }

    private void clearKhuyenMaiForm() {
        idKMField.setText("");
        moTaKMField.setText("");
        giaTriGiamField.setText("");
        ngayBatDauKMField.setText("");
        ngayKetThucKMField.setText("");
    }

    private void selectKhuyenMai() {
        int selectedRow = khuyenMaiTable.getSelectedRow();
        if (selectedRow >= 0) {
            idKMField.setText((String) khuyenMaiTableModel.getValueAt(selectedRow, 0));
            moTaKMField.setText((String) khuyenMaiTableModel.getValueAt(selectedRow, 1));
            giaTriGiamField.setText(khuyenMaiTableModel.getValueAt(selectedRow, 2).toString());
            ngayBatDauKMField.setText((String) khuyenMaiTableModel.getValueAt(selectedRow, 3));
            ngayKetThucKMField.setText((String) khuyenMaiTableModel.getValueAt(selectedRow, 4));
        }
    }

    private void themHoaDon() {
        try {
            String maHD = maHDField.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date ngayTao = sdf.parse(ngayTaoHDField.getText());
            HoaDon hd = new HoaDon(maHD, ngayTao);
            hoaDonDao.save(hd);
            dsHoaDon.add(hd);
            loadHoaDonTableData();
            clearHoaDonForm();
            JOptionPane.showMessageDialog(this, "Thêm hóa đơn thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void clearHoaDonForm() {
        maHDField.setText("");
        ngayTaoHDField.setText("");
    }

    private void selectHoaDon() {
        int selectedRow = hoaDonTable.getSelectedRow();
        if (selectedRow >= 0) {
            maHDField.setText((String) hoaDonTableModel.getValueAt(selectedRow, 0));
            ngayTaoHDField.setText((String) hoaDonTableModel.getValueAt(selectedRow, 1));
        }
    }

    private void nhapKho() {
        JTextField maSPField = new JTextField(15);
        JTextField soLuongField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã SP:"));
        panel.add(maSPField);
        panel.add(new JLabel("Số Lượng Nhập:"));
        panel.add(soLuongField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Nhập Kho", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maSP = maSPField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                dsSanPhamDao.nhapKho(maSP, soLuong);
                dsSanPham.setDsSanPham(sanPhamDao.findAll());
                loadSanPhamTableData();
                JOptionPane.showMessageDialog(this, "Nhập kho thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void xuatKho() {
        JTextField maSPField = new JTextField(15);
        JTextField soLuongField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã SP:"));
        panel.add(maSPField);
        panel.add(new JLabel("Số Lượng Xuất:"));
        panel.add(soLuongField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Xuất Kho", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maSP = maSPField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());
                dsSanPhamDao.xuatKho(maSP, soLuong);
                dsSanPham.setDsSanPham(sanPhamDao.findAll());
                loadSanPhamTableData();
                JOptionPane.showMessageDialog(this, "Xuất kho thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void xemTheoDanhMuc() {
        JComboBox<String> danhMucCombo = new JComboBox<>();
        for (DanhMucSP dm : dsDanhMuc) {
            danhMucCombo.addItem(dm.getMaDanhMuc() + " - " + dm.getTenDanhMuc());
        }

        int result = JOptionPane.showConfirmDialog(this, danhMucCombo, "Chọn Danh Mục", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String dmStr = (String) danhMucCombo.getSelectedItem();
            String maDanhMuc = dmStr.split(" - ")[0];
            dsSanPham.setDsSanPham(dsSanPhamDao.layDanhSachTheoDanhMuc(maDanhMuc));
            loadSanPhamTableData();
        }
    }

    private void congDiemTichLuy() {
        JTextField maKHField = new JTextField(15);
        JTextField diemField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã KH:"));
        panel.add(maKHField);
        panel.add(new JLabel("Điểm Cộng:"));
        panel.add(diemField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Cộng Điểm Tích Lũy", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maKH = maKHField.getText();
                int diem = Integer.parseInt(diemField.getText());
                KhachHang kh = khachHangDao.findByMaKH(maKH);
                if (kh != null) {
                    kh.congDiemTichLuy(diem);
                    khachHangDao.capNhatKhachHang(kh);
                    dsKhachHang = khachHangDao.findAll();
                    loadKhachHangTableData();
                    JOptionPane.showMessageDialog(this, "Cộng điểm tích lũy thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void suDungDiemTichLuy() {
        JTextField maKHField = new JTextField(15);
        JTextField diemField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã KH:"));
        panel.add(maKHField);
        panel.add(new JLabel("Điểm Sử Dụng:"));
        panel.add(diemField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Sử Dụng Điểm Tích Lũy", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maKH = maKHField.getText();
                int diem = Integer.parseInt(diemField.getText());
                KhachHang kh = khachHangDao.findByMaKH(maKH);
                if (kh != null) {
                    kh.suDungDiemTichLuy(diem);
                    khachHangDao.capNhatKhachHang(kh);
                    dsKhachHang = khachHangDao.findAll();
                    loadKhachHangTableData();
                    JOptionPane.showMessageDialog(this, "Sử dụng điểm tích lũy thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }


    private void apDungKhuyenMai() {
        JTextField maHDField = new JTextField(15);
        JTextField idKMField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Mã HD:"));
        panel.add(maHDField);
        panel.add(new JLabel("ID Khuyến Mãi:"));
        panel.add(idKMField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Áp Dụng Khuyến Mãi", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String maHD = maHDField.getText();
                String idKM = idKMField.getText();
                HoaDon hd = hoaDonDao.findByMaHD(maHD);
                KhuyenMai km = khuyenMaiDao.timKhuyenMai(idKM);
                if (hd != null && km != null) {
                    km.apDungKhuyenMai(hd);
                    // Không cần lưu lại vì apDungKhuyenMai không thay đổi dữ liệu trong DB
                    JOptionPane.showMessageDialog(this, "Áp dụng khuyến mãi thành công!");
                } else {
                    JOptionPane.showMessageDialog(this, "Hóa đơn hoặc khuyến mãi không tồn tại!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void taoBaoCaoDoanhThu() {
        JTextField tuNgayField = new JTextField(15);
        JTextField denNgayField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Từ Ngày (dd/MM/yyyy):"));
        panel.add(tuNgayField);
        panel.add(new JLabel("Đến Ngày (dd/MM/yyyy):"));
        panel.add(denNgayField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Báo Cáo Doanh Thu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date tuNgay = sdf.parse(tuNgayField.getText());
                Date denNgay = sdf.parse(denNgayField.getText());
                BaoCao bc = new BaoCao("BC" + System.currentTimeMillis(), "DoanhThu", "Báo Cáo Doanh Thu");
                bc.taoBaoCaoDoanhThu(tuNgay, denNgay);
                baoCaoDao.save(bc);
                JOptionPane.showMessageDialog(this, "Tạo báo cáo doanh thu thành công!");
                showBaoCaoPanel();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void taoBaoCaoTonKho() {
        int result = JOptionPane.showConfirmDialog(this, "Tạo báo cáo tồn kho?", "Báo Cáo Tồn Kho", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                BaoCao bc = new BaoCao("BC" + System.currentTimeMillis(), "TonKho", "Báo Cáo Tồn Kho");
                bc.taoBaoCaoTonKho();
                baoCaoDao.save(bc);
                JOptionPane.showMessageDialog(this, "Tạo báo cáo tồn kho thành công!");
                showBaoCaoPanel();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private void showSanPhamPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createSanPhamPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showNhaCungCapPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createNhaCungCapPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showDanhMucPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createDanhMucPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showKhachHangPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createKhachHangPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showNhanVienPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createNhanVienPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showKhuyenMaiPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createKhuyenMaiPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showHoaDonPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createHoaDonPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showBaoCaoPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createBaoCaoPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuanLyCuaHang().setVisible(true));
    }
}