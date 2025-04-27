package gui;

import dao.*;
import entity.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyCuaHang extends JFrame {
	private JMenuBar menuBar;
	private JMenu banHangMenu,quanLyMenu,baoCaoMenu;
	private JMenuItem sanPhamItem,nhaCungCapItem,danhMucItem,nhanVienItem,khuyenMaiItem,hoaDonItem,khachHangItem;
	private JMenuItem nhapKhoItem,xuatKhoItem,congDiemItem,xemChiTietItem;
	private JPopupMenu contextMenu;
	
    private DSSanPham dsSanPham;
    private ArrayList<NhaCungCap> dsNhaCungCap;
    private ArrayList<DanhMucSP> dsDanhMuc;
    private ArrayList<KhuyenMai> dsKhuyenMai;
    private ArrayList<HoaDon> dsHoaDon;
    private ArrayList<KhachHang> dsKhachHang;
    private ArrayList<NhanVien> dsNhanVien;
    private ArrayList<CTHoaDon> tempCTHoaDons;
    private ArrayList<BaoCao> dsBaoCao;
    
    private JTextArea baoCaoTextArea,textArea;
    private JScrollPane scrollPane;
    private JTable sanPhamTable, nhaCungCapTable, danhMucTable, khachHangTable, nhanVienTable, khuyenMaiTable,hoaDonTable
    ,banHangTable;
    private DefaultTableModel sanPhamTableModel, nhaCungCapTableModel, danhMucTableModel, khachHangTableModel, 
    nhanVienTableModel, khuyenMaiTableModel,hoaDonTableModel,banHangTableModel;

    private JPanel currentPanel,mainPanel,panel,formPanel,buttonPanel,khPanel,southPanel;
    
    private JComboBox<String> nhaCungCapComboBox, danhMucComboBox,khachHangComboBox,sanPhamComboBox,danhMucCombo;
    private JTextField maSPField, tenSPField, soLuongField, hsdField, giaField,diemSuDungField;
    private JTextField maNCCFieldForm, tenNCCFieldForm,maDanhMucField, tenDanhMucField,tuNgayField,denNgayField;
    private JTextField maKHField, tenKHField, sdtKHField, emailKHField, diemTichLuyField;
    private JTextField maNVField, tenNVField, chucVuField, sdtNVField,tongTienField,sdtField,emailField,diemField;
    private JTextField idKMField, moTaKMField, giaTriGiamField, ngayBatDauKMField, ngayKetThucKMField;
    private JButton addButton,updateButton,deleteButton,xemTheoDanhMucButton,xemTatCaButton,doanhThuButton,tonKhoButton
	,themSPButton,xoaSPButton,taoHoaDonButton,themKhachHangButton,clearButton,suDungDiemButton,apDungKMButton;

    private SanPham_Dao sanPhamDao = new SanPham_Dao();
    private NhaCungCap_Dao nhaCungCapDao = new NhaCungCap_Dao();
    private DanhMucSP_Dao danhMucDao = new DanhMucSP_Dao();
    private KhachHang_Dao khachHangDao = new KhachHang_Dao();
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();
    private KhuyenMai_Dao khuyenMaiDao = new KhuyenMai_Dao();
    private HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private DSSanPham_Dao dsSanPhamDao = new DSSanPham_Dao();
    private BaoCao_Dao baoCaoDao = new BaoCao_Dao();
	
	private KhachHang kh;
	private HoaDon hd;
	private SanPham sp;
	private CTHoaDon ct;
	private NhaCungCap ncc;
	private DanhMucSP dm;
	private NhanVien nv;
	private KhuyenMai km;
	private BaoCao bc;
	private NhanVien loggedInNhanVien;
	
	private boolean isKhuyenMaiApplied = false;
	private boolean isDiemUsed = false;
	private int diemSuDungTemp = 0;
	private double SumMoney = 0;
	private JLabel tongTienLabel;
	
	public QuanLyCuaHang(NhanVien nv) {
	    this.loggedInNhanVien = nv;
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
    	setTitle("Quản Lý Cửa Hàng - " + "(" + loggedInNhanVien.getChucVu() + " - " + loggedInNhanVien.getTenNhanVien() + ")");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(0,30));
        menuBar.setBackground(new Color(135,216,250));

        // Menu Bán Hàng
        banHangMenu = new JMenu("Bán Hàng");
        banHangMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showBanHangPanel();
            }
        });

        // Menu Quản Lý
        quanLyMenu = new JMenu("Quản Lý");

        // Các mục con của Quản Lý
        sanPhamItem = new JMenuItem("Sản Phẩm");
        sanPhamItem.addActionListener(e -> showSanPhamPanel());

        nhaCungCapItem = new JMenuItem("Nhà Cung Cấp");
        nhaCungCapItem.addActionListener(e -> showNhaCungCapPanel());

        danhMucItem = new JMenuItem("Danh Mục");
        danhMucItem.addActionListener(e -> showDanhMucPanel());

        khachHangItem = new JMenuItem("Khách Hàng");
        khachHangItem.addActionListener(e -> showKhachHangPanel());

        nhanVienItem = new JMenuItem("Nhân Viên");
        nhanVienItem.addActionListener(e -> showNhanVienPanel());

        khuyenMaiItem = new JMenuItem("Khuyến Mãi");
        khuyenMaiItem.addActionListener(e -> showKhuyenMaiPanel());

        hoaDonItem = new JMenuItem("Hóa Đơn");
        hoaDonItem.addActionListener(e -> showHoaDonPanel());
        
        sanPhamItem.setBorder(new EmptyBorder(10,10,10,10));
        sanPhamItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        nhaCungCapItem.setBorder(new EmptyBorder(10,10,10,10));
        nhaCungCapItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        danhMucItem.setBorder(new EmptyBorder(10,10,10,10));
        danhMucItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        khachHangItem.setBorder(new EmptyBorder(10,10,10,10));
        khachHangItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        nhanVienItem.setBorder(new EmptyBorder(10,10,10,10));
        nhanVienItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        khuyenMaiItem.setBorder(new EmptyBorder(10,10,10,10));
        khuyenMaiItem.setFont(new Font("Time new Roman",Font.PLAIN,14));
        hoaDonItem.setBorder(new EmptyBorder(10,10,10,10));
        hoaDonItem.setFont(new Font("Time new Roman",Font.PLAIN,14));

        // Menu Báo Cáo
        baoCaoMenu = new JMenu("Báo Cáo");
        baoCaoMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showBaoCaoPanel();
            }
        });


        // Thêm các mục con vào menu Quản Lý
        quanLyMenu.add(sanPhamItem);
        quanLyMenu.add(nhaCungCapItem);
        quanLyMenu.add(danhMucItem);
        quanLyMenu.add(khachHangItem);
        quanLyMenu.add(nhanVienItem);
        quanLyMenu.add(khuyenMaiItem);
        quanLyMenu.add(hoaDonItem);

        // Thêm các menu vào menuBar
        menuBar.add(banHangMenu);
        menuBar.add(quanLyMenu);
        menuBar.add(baoCaoMenu);
        banHangMenu.setBorder(new EmptyBorder(0,20,0,20));
        quanLyMenu.setBorder(new EmptyBorder(0,20,0,20));
        baoCaoMenu.setBorder(new EmptyBorder(0,20,0,20));
        banHangMenu.setFont(new Font("Roboto",Font.BOLD|Font.ITALIC,16));
        quanLyMenu.setFont(new Font("Roboto",Font.BOLD|Font.ITALIC,16));
        baoCaoMenu.setFont(new Font("Roboto",Font.BOLD|Font.ITALIC,16));
        banHangMenu.setForeground(new Color(230,230,230));
        quanLyMenu.setForeground(new Color(230,230,230));
        baoCaoMenu.setForeground(new Color(230,230,230));
        

      
        setJMenuBar(menuBar);

        mainPanel = new JPanel(new BorderLayout());

        // Tạo tables
        String[] spColumns = {"Mã SP", "Tên SP", "Số Lượng", "HSD", "Nhà Cung Cấp", "Giá", "Loại SP"};
        sanPhamTableModel = new DefaultTableModel(spColumns, 0);
        sanPhamTable = new JTable(sanPhamTableModel);
        JScrollPane scrollPane = new JScrollPane(sanPhamTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sanPhamTable.setGridColor(new Color(250,250,250));
        sanPhamTable.getTableHeader().setBackground(new Color(230,190,200));
        sanPhamTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        sanPhamTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        sanPhamTable.setRowHeight(30);
        loadSanPhamTableData();

        String[] nccColumns = {"Mã NCC", "Tên NCC"};
        nhaCungCapTableModel = new DefaultTableModel(nccColumns, 0);
        nhaCungCapTable = new JTable(nhaCungCapTableModel);
        JScrollPane scrollPane2 = new JScrollPane(nhaCungCapTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nhaCungCapTable.setGridColor(new Color(250,250,250));
        nhaCungCapTable.getTableHeader().setBackground(new Color(230,190,200));
        nhaCungCapTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        nhaCungCapTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        nhaCungCapTable.setRowHeight(30);
        loadNhaCungCapTableData();

        String[] dmColumns = {"Mã Danh Mục", "Tên Danh Mục"};
        danhMucTableModel = new DefaultTableModel(dmColumns, 0);
        danhMucTable = new JTable(danhMucTableModel);
        JScrollPane scrollPane3 = new JScrollPane(danhMucTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        danhMucTable.setGridColor(new Color(250,250,250));
        danhMucTable.getTableHeader().setBackground(new Color(230,190,200));
        danhMucTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        danhMucTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        danhMucTable.setRowHeight(30);
        loadDanhMucTableData();

        String[] khColumns = {"Mã KH", "Tên KH", "Số Điện Thoại", "Email", "Điểm Tích Lũy"};
        khachHangTableModel = new DefaultTableModel(khColumns, 0);
        khachHangTable = new JTable(khachHangTableModel);
        JScrollPane scrollPane4 = new JScrollPane(khachHangTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        khachHangTable.setGridColor(new Color(250,250,250));
        khachHangTable.getTableHeader().setBackground(new Color(230,190,200));
        khachHangTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        khachHangTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        khachHangTable.setRowHeight(30);
        loadKhachHangTableData();

        String[] nvColumns = {"Mã NV", "Tên NV", "Chức Vụ", "Số Điện Thoại"};
        nhanVienTableModel = new DefaultTableModel(nvColumns, 0);
        nhanVienTable = new JTable(nhanVienTableModel);
        JScrollPane scrollPane5 = new JScrollPane(nhanVienTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        nhanVienTable.setGridColor(new Color(250,250,250));
        nhanVienTable.getTableHeader().setBackground(new Color(230,190,200));
        nhanVienTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        nhanVienTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        nhanVienTable.setRowHeight(30);
        loadNhanVienTableData();

        String[] kmColumns = {"ID KM", "Mô Tả", "Giá Trị Giảm", "Ngày Bắt Đầu", "Ngày Kết Thúc"};
        khuyenMaiTableModel = new DefaultTableModel(kmColumns, 0);
        khuyenMaiTable = new JTable(khuyenMaiTableModel);
        JScrollPane scrollPane6 = new JScrollPane(khuyenMaiTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        khuyenMaiTable.setGridColor(new Color(250,250,250));
        khuyenMaiTable.getTableHeader().setBackground(new Color(230,190,200));
        khuyenMaiTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        khuyenMaiTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        khuyenMaiTable.setRowHeight(30);
        loadKhuyenMaiTableData();

        String[] hdColumns = {"Mã HD", "Ngày Tạo", "Khách Hàng", "Nhân Viên", "Tổng Tiền"};
        hoaDonTableModel = new DefaultTableModel(hdColumns, 0);
        hoaDonTable = new JTable(hoaDonTableModel);
        JScrollPane scrollPane7 = new JScrollPane(hoaDonTable,
        	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        hoaDonTable.setGridColor(new Color(250,250,250));
        hoaDonTable.getTableHeader().setBackground(new Color(230,190,200));
        hoaDonTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        hoaDonTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        hoaDonTable.setRowHeight(30);
        loadHoaDonTableData();

        currentPanel = createSanPhamPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    // Cập nhật panel Bán Hàng
    private JPanel createBanHangPanel() {
        panel = new JPanel(new BorderLayout());

        String[] columns = {"Mã SP", "Tên SP", "Số Lượng", "Đơn Giá", "Thành Tiền"};
        banHangTableModel = new DefaultTableModel(columns, 0);
        banHangTable = new JTable(banHangTableModel);
        banHangTable.setGridColor(new Color(250,250,250));
        banHangTable.getTableHeader().setBackground(new Color(230,190,200));
        banHangTable.getTableHeader().setFont(new Font("Arial",Font.BOLD,13));
        banHangTable.getTableHeader().setBorder(new EmptyBorder(20,20,20,20));
        banHangTable.setRowHeight(30);
        scrollPane = new JScrollPane(banHangTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        tongTienLabel = new JLabel("Tổng tiền: 0 VNĐ");
        tongTienLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tongTienLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        
        JPanel tableWithTotalPanel = new JPanel(new BorderLayout());
        tableWithTotalPanel.add(scrollPane, BorderLayout.CENTER);
        tableWithTotalPanel.add(tongTienLabel, BorderLayout.SOUTH);

        panel.add(tableWithTotalPanel, BorderLayout.CENTER);
        
        formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Khách Hàng:"));
        khachHangComboBox = new JComboBox<>();
        updateKhachHangComboBox(khachHangComboBox);
        formPanel.add(khachHangComboBox);

        formPanel.add(new JLabel("Sản Phẩm:"));
        sanPhamComboBox = new JComboBox<>();
        for (SanPham sp : dsSanPham.getDsSanPham()) {
            sanPhamComboBox.addItem(sp.getMaSP() + " - " + sp.getTenSP());
        }
        formPanel.add(sanPhamComboBox);

        formPanel.add(new JLabel("Số Lượng:"));
        soLuongField = new JTextField(10);
        formPanel.add(soLuongField);

        formPanel.add(new JLabel("Thành Tiền:"));
        tongTienField = new JTextField(10);
        tongTienField.setEditable(false);
        formPanel.add(tongTienField);
        
        formPanel.add(new JLabel("Điểm Tích Lũy:"));
        diemTichLuyField = new JTextField(10);
        diemTichLuyField.setEditable(false);
        formPanel.add(diemTichLuyField);
        
        formPanel.add(new JLabel("Khuyến Mãi:"));
        JComboBox<String> khuyenMaiComboBox = new JComboBox<>();
        khuyenMaiComboBox.addItem("Không chọn khuyến mãi");
        for (KhuyenMai km : dsKhuyenMai) {
            if (km.kiemTraHieuLuc()) {
                khuyenMaiComboBox.addItem(km.getIdKM() + " - " + km.getMoTa());
            }
        }
        formPanel.add(khuyenMaiComboBox);

        khachHangComboBox.addActionListener(e -> {
            String khStr = (String) khachHangComboBox.getSelectedItem();
            if (khStr != null) {
                String maKH = khStr.split(" - ")[0];
                kh = khachHangDao.findByMaKH(maKH);
                if (kh != null) {
                    diemTichLuyField.setText(String.valueOf(kh.getDiemTichLuy()));
                } else {
                    diemTichLuyField.setText("0");
                }
            }
        });
        
        banHangTableModel.addTableModelListener(e -> {
            double tongTien = 0;
            for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
                double thanhTien = (double) banHangTableModel.getValueAt(i, 4);
                tongTien += thanhTien;
            }
            tongTienField.setText(String.valueOf(tongTien));
            boolean coSanPham = banHangTableModel.getRowCount() > 0;
            suDungDiemButton.setEnabled(coSanPham && !isDiemUsed);
            apDungKMButton.setEnabled(coSanPham && !isKhuyenMaiApplied);
        });

        buttonPanel = new JPanel(new FlowLayout());
        
        themSPButton = new JButton("Thêm Sản Phẩm");
        themSPButton.setBackground(new Color(135,230,250));
        themSPButton.setBorder(new EmptyBorder(10,10,10,10));
        
        xoaSPButton = new JButton("Xóa Sản Phẩm");
        xoaSPButton.setBackground(new Color(135,230,250));
        xoaSPButton.setBorder(new EmptyBorder(10,10,10,10));
        
        taoHoaDonButton = new JButton("Tạo Hóa Đơn");
        taoHoaDonButton.setBackground(new Color(135,230,250));
        taoHoaDonButton.setBorder(new EmptyBorder(10,10,10,10));
        
        themKhachHangButton = new JButton("Thêm Khách Hàng");
        themKhachHangButton.setBackground(new Color(135,230,250));
        themKhachHangButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));
        
        suDungDiemButton = new JButton("Sử Dụng Điểm");
        suDungDiemButton.setBackground(new Color(135,230,250));
        suDungDiemButton.setBorder(new EmptyBorder(10,10,10,10));
        
        apDungKMButton = new JButton("Áp Dụng Khuyến Mãi");
        apDungKMButton.setBackground(new Color(135,230,250));
        apDungKMButton.setBorder(new EmptyBorder(10,10,10,10));
                 
        suDungDiemButton.setEnabled(false);
        apDungKMButton.setEnabled(false);
        sanPhamComboBox.addActionListener(e -> {
            soLuongField.setText("1"); 
            updateTongTien(); 
        });
        soLuongField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                updateTongTien(); 
            }
        });

        themSPButton.addActionListener(e -> {
            try {
                String spStr = (String) sanPhamComboBox.getSelectedItem();
                String maSP = spStr.split(" - ")[0];
                int soLuong = Integer.parseInt(soLuongField.getText());
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                    return;
                }
                sp = sanPhamDao.findByMaSP(maSP);
                if (sp == null) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm không tồn tại!");
                    return;
                }
                if (!dsSanPhamDao.kiemTraTonKho(maSP, soLuong)) {
                    JOptionPane.showMessageDialog(this, "Số lượng tồn kho không đủ!");
                    return;
                }

                double donGia = sp.getGia();
                double thanhTien = donGia * soLuong;

                banHangTableModel.addRow(new Object[]{
                    maSP, sp.getTenSP(), soLuong, donGia, thanhTien
                });
                soLuongField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
            capNhatTongTienBanHang();
        });

        xoaSPButton.addActionListener(e -> {
            int selectedRow = banHangTable.getSelectedRow();
            if (selectedRow >= 0) {
                banHangTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            }
            capNhatTongTienBanHang();
        });

        themKhachHangButton.addActionListener(e -> {
            maKHField = new JTextField(10);
            tenKHField = new JTextField(10);
            sdtField = new JTextField(10);
            emailField = new JTextField(10);
            diemTichLuyField = new JTextField(10);

            khPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            khPanel.add(new JLabel("Mã KH:"));
            khPanel.add(maKHField);
            khPanel.add(new JLabel("Tên KH:"));
            khPanel.add(tenKHField);
            khPanel.add(new JLabel("SĐT:"));
            khPanel.add(sdtField);
            khPanel.add(new JLabel("Email:"));
            khPanel.add(emailField);
            khPanel.add(new JLabel("Điểm Tích Lũy:"));
            khPanel.add(diemTichLuyField);

            int result = JOptionPane.showConfirmDialog(this, khPanel, "Thêm Khách Hàng", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    if (banHangTableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được chọn!");
                        return;
                    }

                    String khachHangStr = (String) khachHangComboBox.getSelectedItem();
                    KhachHang kh = null;

                    if (khachHangStr != null && !khachHangStr.equalsIgnoreCase("Khách vãng lai")) {
                        String maKH = khachHangStr.split(" - ")[0];
                        kh = khachHangDao.findByMaKH(maKH);
                    }

                    String maHD = HoaDon.taoMaHoaDon(hoaDonDao);
                    hd = new HoaDon(maHD, kh, loggedInNhanVien);

                    String idKM = null;
                    if (isKhuyenMaiApplied) {
                        String kmStr = (String) khuyenMaiComboBox.getSelectedItem();
                        if (!kmStr.equalsIgnoreCase("Không chọn khuyến mãi")) {
                            idKM = kmStr.split(" - ")[0];
                        }
                    }

                    double tongTienTruocGiam = 0;
                    tempCTHoaDons = new ArrayList<>();
                    for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
                        String maSP = (String) banHangTableModel.getValueAt(i, 0);
                        int soLuong = (int) banHangTableModel.getValueAt(i, 2);
                        double donGia = (double) banHangTableModel.getValueAt(i, 3);

                        sp = sanPhamDao.findByMaSP(maSP);
                        if (sp == null) {
                            throw new RuntimeException("Sản phẩm không tồn tại: " + maSP);
                        }

                        ct = new CTHoaDon(hd, sp, soLuong, donGia, kh);
                        ct.setIdKM(idKM);
                        double thanhTien = ct.tinhTongTien();
                        ct.setTongTien(thanhTien);
                        tempCTHoaDons.add(ct);
                        tongTienTruocGiam += thanhTien;

                        dsSanPhamDao.xuatKho(maSP, soLuong);
                    }

                    if (diemSuDungTemp > 0) {
                        double giamGiaDiem = diemSuDungTemp * 1000;
                        if (giamGiaDiem > tongTienTruocGiam) {
                            giamGiaDiem = tongTienTruocGiam;
                        }
                        double conLai = giamGiaDiem;
                        for (int i = 0; i < tempCTHoaDons.size(); i++) {
                            ct = tempCTHoaDons.get(i);
                            double thanhTien = ct.getTongTien();
                            double tiLe = thanhTien / tongTienTruocGiam;
                            double giam = giamGiaDiem * tiLe;
                            if (i == tempCTHoaDons.size() - 1) {
                                giam = conLai;
                            }
                            double newTongTien = thanhTien - giam;
                            if (newTongTien < 0) newTongTien = 0;
                            ct.setTongTien(newTongTien);
                            conLai -= giam;
                        }
                    }

                    for (CTHoaDon ct : tempCTHoaDons) {
                        hd.getDsSanPham().add(ct);
                    }

                    hd.setDiemSuDung(diemSuDungTemp);
                    if (kh != null) {
                        int diemCongThem = (int) (hd.tinhTongTien() / 10000);
                        kh.congDiemTichLuy(diemCongThem);
                        khachHangDao.capNhatKhachHang(kh);
                    }

                    dsKhachHang = khachHangDao.findAll();
                    loadKhachHangTableData();
                    updateKhachHangComboBox(khachHangComboBox);

                    hoaDonDao.save(hd);
                    dsHoaDon.add(hd);
                    dsSanPham.setDsSanPham(sanPhamDao.findAll());
                    loadSanPhamTableData();
                    loadHoaDonTableData();

                    banHangTableModel.setRowCount(0);
                    isKhuyenMaiApplied = false;
                    diemSuDungTemp = 0;
                    isDiemUsed = false;
                    suDungDiemButton.setEnabled(false);
                    apDungKMButton.setEnabled(false);
                    apDungKMButton.setEnabled(true);

                    JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công! Mã hóa đơn: " + maHD);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + ex.getMessage());
                }
            }
        });

        taoHoaDonButton.addActionListener(e -> {
            try {
                if (banHangTableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Chưa có sản phẩm nào được chọn!");
                    return;
                }

                String khachHangStr = (String) khachHangComboBox.getSelectedItem();
                KhachHang kh = null;

                if (khachHangStr != null && !khachHangStr.equalsIgnoreCase("Khách vãng lai")) {
                    String maKH = khachHangStr.split(" - ")[0];
                    kh = khachHangDao.findByMaKH(maKH);
                }

                String maHD = HoaDon.taoMaHoaDon(hoaDonDao);
                hd = new HoaDon(maHD, kh, loggedInNhanVien);

                String idKM = null;
                if (isKhuyenMaiApplied) {
                    String kmStr = (String) khuyenMaiComboBox.getSelectedItem();
                    if (!kmStr.equalsIgnoreCase("Không chọn khuyến mãi")) {
                        idKM = kmStr.split(" - ")[0];
                    }
                }

                double tongTienTruocGiam = 0;
                tempCTHoaDons = new ArrayList<>();
                for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
                    String maSP = (String) banHangTableModel.getValueAt(i, 0);
                    int soLuong = (int) banHangTableModel.getValueAt(i, 2);
                    double donGia = (double) banHangTableModel.getValueAt(i, 3);

                    sp = sanPhamDao.findByMaSP(maSP);
                    if (sp == null) {
                        throw new RuntimeException("Sản phẩm không tồn tại: " + maSP);
                    }

                    ct = new CTHoaDon(hd, sp, soLuong, donGia, kh);
                    ct.setIdKM(idKM);
                    double thanhTien = ct.tinhTongTien();
                    ct.setTongTien(thanhTien);
                    tempCTHoaDons.add(ct);
                    tongTienTruocGiam += thanhTien;

                    dsSanPhamDao.xuatKho(maSP, soLuong);
                }

                if (diemSuDungTemp > 0) {
                    double giamGiaDiem = diemSuDungTemp * 1000;
                    if (giamGiaDiem > tongTienTruocGiam) {
                        giamGiaDiem = tongTienTruocGiam;
                    }
                    double conLai = giamGiaDiem;
                    for (int i = 0; i < tempCTHoaDons.size(); i++) {
                        ct = tempCTHoaDons.get(i);
                        double thanhTien = ct.getTongTien();
                        double tiLe = thanhTien / tongTienTruocGiam;
                        double giam = giamGiaDiem * tiLe;
                        if (i == tempCTHoaDons.size() - 1) {
                            giam = conLai;
                        }
                        double newTongTien = thanhTien - giam;
                        if (newTongTien < 0) newTongTien = 0;
                        ct.setTongTien(newTongTien);
                        conLai -= giam;
                    }
                }

                for (CTHoaDon ct : tempCTHoaDons) {
                    hd.getDsSanPham().add(ct);
                }

                hd.setDiemSuDung(diemSuDungTemp);

                if (kh != null) {
                    int diemCongThem = (int) (hd.tinhTongTien() / 10000);
                    kh.congDiemTichLuy(diemCongThem);
                    khachHangDao.capNhatKhachHang(kh);
                }
                
                dsKhachHang = khachHangDao.findAll();
                loadKhachHangTableData();
                updateKhachHangComboBox(khachHangComboBox);

                hoaDonDao.save(hd);
                dsHoaDon.add(hd);
                dsSanPham.setDsSanPham(sanPhamDao.findAll());
                loadSanPhamTableData();
                loadHoaDonTableData();

                banHangTableModel.setRowCount(0);
                isKhuyenMaiApplied = false;
                diemSuDungTemp = 0;
                isDiemUsed = false;
                suDungDiemButton.setEnabled(false);
                apDungKMButton.setEnabled(false);
                tongTienLabel.setText("Tổng tiền: 0 VNĐ");
                SumMoney = 0;

                JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công! Mã hóa đơn: " + maHD);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
        
        suDungDiemButton.addActionListener(e -> {
            try {
                String khStr = (String) khachHangComboBox.getSelectedItem();
                if (khStr == null) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
                    return;
                }
                String maKH = khStr.split(" - ")[0];
                kh = khachHangDao.findByMaKH(maKH);
                if (kh == null) {
                    JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!");
                    return;
                }

                int diem = kh.getDiemTichLuy();
                if (diem <= 0) {
                    JOptionPane.showMessageDialog(this, "Khách hàng không có điểm tích lũy!");
                    return;
                }

                diemSuDungField = new JTextField(10);
                panel = new JPanel();
                panel.add(new JLabel("Số điểm muốn sử dụng (1 điểm = 1000 VNĐ):"));
                panel.add(diemSuDungField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Sử Dụng Điểm", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    int diemSuDung;
                    try {
                        diemSuDung = Integer.parseInt(diemSuDungField.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Số điểm không hợp lệ!");
                        return;
                    }
                    if (diemSuDung <= 0) {
                        JOptionPane.showMessageDialog(this, "Số điểm sử dụng phải lớn hơn 0!");
                        return;
                    }
                    if (diemSuDung > diem) {
                        JOptionPane.showMessageDialog(this, "Số điểm sử dụng vượt quá điểm tích lũy!");
                        return;
                    }

                    double giamGia = diemSuDung * 1000;
                    double tongTien = Double.parseDouble(tongTienField.getText());
                    if(SumMoney < tongTien && SumMoney == 0) {
                    	SumMoney = tongTien;
                    }
                    SumMoney -= giamGia;
                    if (SumMoney < 0) SumMoney = 0;
                    tongTienLabel.setText("Tổng tiền: " + String.format("%,.0f VNĐ", SumMoney));

                    diemSuDungTemp = diemSuDung; 

                    kh.congDiemTichLuy(-diemSuDung); 
                    khachHangDao.capNhatKhachHang(kh);
                    dsKhachHang = khachHangDao.findAll();
                    loadKhachHangTableData();
                    diemTichLuyField.setText(String.valueOf(kh.getDiemTichLuy()));
                    isDiemUsed = true;
                    suDungDiemButton.setEnabled(false); 
                    JOptionPane.showMessageDialog(this, "Sử dụng điểm thành công!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
        

        apDungKMButton.addActionListener(e -> {
            try {
                if (isKhuyenMaiApplied) {
                    JOptionPane.showMessageDialog(this, "Khuyến mãi đã được áp dụng!");
                    return;
                }

                String kmStr = (String) khuyenMaiComboBox.getSelectedItem();
                if (kmStr.equals("Không chọn khuyến mãi")) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn khuyến mãi!");
                    return;
                }

                String idKM = kmStr.split(" - ")[0];
                km = khuyenMaiDao.timKhuyenMai(idKM);
                if (km == null || !km.kiemTraHieuLuc()) {
                    JOptionPane.showMessageDialog(this, "Khuyến mãi không hợp lệ hoặc đã hết hiệu lực!");
                    return;
                }

                double tongTienGoc = 0;
                for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
                    int soLuong = (int) banHangTableModel.getValueAt(i, 2);
                    double donGia = (double) banHangTableModel.getValueAt(i, 3);
                    tongTienGoc += soLuong * donGia;
                }

                double tongTien = Double.parseDouble(tongTienField.getText());
                if(SumMoney < tongTien && SumMoney == 0) {
                	SumMoney = tongTien;
                }
                double giamGia;
                if (km.getGiaTriGiam() < 1) {
                    giamGia = tongTienGoc * km.getGiaTriGiam(); 
                } else {
                    giamGia = km.getGiaTriGiam();
                }
                SumMoney -= giamGia;
                if (SumMoney < 0) SumMoney = 0;
                tongTienLabel.setText("Tổng tiền: " + String.format("%,.0f VNĐ", SumMoney));

                for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
                    String maSP = (String) banHangTableModel.getValueAt(i, 0);
                    ct = new CTHoaDon(null, sanPhamDao.findByMaSP(maSP), 
                        (int) banHangTableModel.getValueAt(i, 2), 
                        (double) banHangTableModel.getValueAt(i, 3), kh);
                    ct.setIdKM(idKM);
                }

                isKhuyenMaiApplied = true;
                apDungKMButton.setEnabled(false); 
                JOptionPane.showMessageDialog(this, "Áp dụng khuyến mãi thành công!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        });
        
        clearButton.addActionListener(e -> {
            banHangTableModel.setRowCount(0);
            soLuongField.setText("");
            khachHangComboBox.setSelectedIndex(0);
        });
        
        makeComboBoxSearchable(khachHangComboBox);
        makeComboBoxSearchable(sanPhamComboBox);
        buttonPanel.add(themSPButton);
        buttonPanel.add(xoaSPButton);
        buttonPanel.add(themKhachHangButton);
        buttonPanel.add(taoHoaDonButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(suDungDiemButton);
        buttonPanel.add(apDungKMButton);

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateKhachHangComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Khách vãng lai"); 
        for (KhachHang kh : dsKhachHang) {
            comboBox.addItem(kh.getMaKH() + " - " + kh.getTenKH());
        }
    }
    private JPanel createSanPhamPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(sanPhamTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
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

        buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        updateButton = new JButton("Cập Nhật");
        updateButton.setBackground(new Color(135,230,250));
        updateButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));
        
        xemTheoDanhMucButton = new JButton("Xem Theo Danh Mục");
        xemTheoDanhMucButton.setBackground(new Color(135,230,250));
        xemTheoDanhMucButton.setBorder(new EmptyBorder(10,10,10,10));
        
        xemTatCaButton = new JButton("Xem Tất Cả");
        xemTatCaButton.setBackground(new Color(135,230,250));
        xemTatCaButton.setBorder(new EmptyBorder(10,10,10,10));
        

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(xemTheoDanhMucButton);
        buttonPanel.add(xemTatCaButton);

        addButton.addActionListener(e -> themSanPham());
        updateButton.addActionListener(e -> capNhatSanPham());
        deleteButton.addActionListener(e -> xoaSanPham());
        clearButton.addActionListener(e -> clearSanPhamForm());
        xemTheoDanhMucButton.addActionListener(e -> xemTheoDanhMuc());
        xemTatCaButton.addActionListener(e -> {
            dsSanPham.setDsSanPham(sanPhamDao.findAll());
            loadSanPhamTableData();
        });

        sanPhamTable.getSelectionModel().addListSelectionListener(e -> selectSanPham());
        addSanPhamContextMenu();

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNhaCungCapPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(nhaCungCapTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã NCC:"));
        maNCCFieldForm = new JTextField(15);
        formPanel.add(maNCCFieldForm);

        formPanel.add(new JLabel("Tên NCC:"));
        tenNCCFieldForm = new JTextField(15);
        formPanel.add(tenNCCFieldForm);

        buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themNhaCungCap());
        deleteButton.addActionListener(e -> xoaNhaCungCap());
        clearButton.addActionListener(e -> clearNhaCungCapForm());

        nhaCungCapTable.getSelectionModel().addListSelectionListener(e -> selectNhaCungCap());

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDanhMucPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(danhMucTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Mã Danh Mục:"));
        maDanhMucField = new JTextField(15);
        formPanel.add(maDanhMucField);

        formPanel.add(new JLabel("Tên Danh Mục:"));
        tenDanhMucField = new JTextField(15);
        formPanel.add(tenDanhMucField);

        buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themDanhMuc());
        deleteButton.addActionListener(e -> xoaDanhMuc());
        clearButton.addActionListener(e -> clearDanhMucForm());

        danhMucTable.getSelectionModel().addListSelectionListener(e -> selectDanhMuc());

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createKhachHangPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(khachHangTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
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

        buttonPanel = new JPanel(new FlowLayout());
        
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        updateButton = new JButton("Cập Nhật");
        updateButton.setBackground(new Color(135,230,250));
        updateButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themKhachHang());
        updateButton.addActionListener(e -> capNhatKhachHang());
        deleteButton.addActionListener(e -> xoaKhachHang());
        clearButton.addActionListener(e -> clearKhachHangForm());

        khachHangTable.getSelectionModel().addListSelectionListener(e -> selectKhachHang());

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);
        addKhachHangContextMenu();
        return panel;
    }

    private JPanel createNhanVienPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(nhanVienTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
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

        buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        updateButton = new JButton("Cập Nhật");
        updateButton.setBackground(new Color(135,230,250));
        updateButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themNhanVien());
        updateButton.addActionListener(e -> capNhatNhanVien());
        deleteButton.addActionListener(e -> xoaNhanVien());
        clearButton.addActionListener(e -> clearNhanVienForm());

        nhanVienTable.getSelectionModel().addListSelectionListener(e -> selectNhanVien());

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createKhuyenMaiPanel() {
        panel = new JPanel(new BorderLayout());
        scrollPane = new JScrollPane(khuyenMaiTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
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

        buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(135,230,250));
        addButton.setBorder(new EmptyBorder(10,10,10,10));
        
        updateButton = new JButton("Cập Nhật");
        updateButton.setBackground(new Color(135,230,250));
        updateButton.setBorder(new EmptyBorder(10,10,10,10));
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(135,230,250));
        deleteButton.setBorder(new EmptyBorder(10,10,10,10));
        
        clearButton = new JButton("Xóa Form");
        clearButton.setBackground(new Color(135,230,250));
        clearButton.setBorder(new EmptyBorder(10,10,10,10));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.addActionListener(e -> themKhuyenMai());
        updateButton.addActionListener(e -> capNhatKhuyenMai());
        deleteButton.addActionListener(e -> xoaKhuyenMai());
        clearButton.addActionListener(e -> clearKhuyenMaiForm());

        khuyenMaiTable.getSelectionModel().addListSelectionListener(e -> selectKhuyenMai());

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createHoaDonPanel() {
        panel = new JPanel(new BorderLayout());
        addHoaDonContextMenu();
        scrollPane = new JScrollPane(hoaDonTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

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
            String giam = km.getGiaTriGiam() < 1 ? (km.getGiaTriGiam() * 100 + "%") : (km.getGiaTriGiam() + "");
            khuyenMaiTableModel.addRow(new Object[]{
                km.getIdKM(),
                km.getMoTa(),
                giam,
                sdf.format(km.getNgayBatDau()),
                sdf.format(km.getNgayKetThuc())
            });
        }
    }

    private void loadHoaDonTableData() {
        hoaDonTableModel.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (HoaDon hd : dsHoaDon) {
            String khachHang = "Không xác định";
            String nhanVien = "Không xác định"; 
            if (!hd.getDsSanPham().isEmpty() && hd.getDsSanPham().get(0).getKhachHang() != null) {
                khachHang = hd.getDsSanPham().get(0).getKhachHang().getTenKH();
            }
            if (hd.getNhanVien() != null) { 
                nhanVien = hd.getNhanVien().getTenNhanVien();
            }
            hoaDonTableModel.addRow(new Object[]{
                hd.getMaHD(),
                sdf.format(hd.getNgayTao()),
                khachHang,
                nhanVien,
                hd.tinhTongTien()
            });
        }
    }

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

    private void themSanPham() {
        try {
            String maSP = maSPField.getText();
            String tenSP = tenSPField.getText();
            int soLuong = Integer.parseInt(soLuongField.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date hsd = sdf.parse(hsdField.getText());
            Date currentDate = new Date();
            if (!hsd.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Hạn sử dụng phải lớn hơn ngày hiện tại!");
                return;
            }
            String nccStr = (String) nhaCungCapComboBox.getSelectedItem();
            String maNCC = nccStr.split(" - ")[0];
            double gia = Double.parseDouble(giaField.getText());
            if (gia <= 0) {
         
                JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0!");
                
                return;
            }
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
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
        }
    }

    private void capNhatSanPham() {
        try {
            String maSP = maSPField.getText();
            String tenSP = tenSPField.getText();
            int soLuong = Integer.parseInt(soLuongField.getText());
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date hsd = sdf.parse(hsdField.getText());
            Date currentDate = new Date();
            if (!hsd.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Hạn sử dụng phải lớn hơn ngày hiện tại!");
                return;
            }
            String nccStr = (String) nhaCungCapComboBox.getSelectedItem();
            String maNCC = nccStr.split(" - ")[0];
            double gia = Double.parseDouble(giaField.getText());
            if (gia <= 0) {
                JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0!");
                return;
            }
            String dmStr = (String) danhMucComboBox.getSelectedItem();
            String maDanhMuc = dmStr.split(" - ")[0];

            ncc = nhaCungCapDao.findByMaNCC(maNCC);
            dm = danhMucDao.findByMaDanhMuc(maDanhMuc);
            sp = new SanPham(maSP, tenSP, soLuong, hsd, ncc, gia, dm);
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
            ncc = new NhaCungCap(maNCC, tenNCC);
            nhaCungCapDao.themNhaCungCap(ncc);
            dsNhaCungCap.add(ncc);
            loadNhaCungCapTableData();
            updateNhaCungCapComboBox();
            clearNhaCungCapForm();
            JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
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
            dm = new DanhMucSP(maDanhMuc, tenDanhMuc);
            danhMucDao.themDanhMuc(dm);
            dsDanhMuc.add(dm);
            loadDanhMucTableData();
            updateDanhMucComboBox();
            clearDanhMucForm();
            JOptionPane.showMessageDialog(this, "Thêm danh muc thành công!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
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
            kh = new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
            khachHangDao.themKhachHang(kh);
            dsKhachHang.add(kh);
            loadKhachHangTableData();
            clearKhachHangForm();
            JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
        }
    }

    private void capNhatKhachHang() {
        try {
            String maKH = maKHField.getText();
            String tenKH = tenKHField.getText();
            String sdt = sdtKHField.getText();
            String email = emailKHField.getText();
            int diemTichLuy = Integer.parseInt(diemTichLuyField.getText());
            kh = new KhachHang(maKH, tenKH, sdt, email, diemTichLuy);
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
            nv = new NhanVien(maNV, tenNV, chucVu, sdt);
            nhanVienDao.themNhanVien(nv);
            dsNhanVien.add(nv);
            loadNhanVienTableData();
            clearNhanVienForm();
            JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
        }
    }

    private void capNhatNhanVien() {
        try {
            String maNV = maNVField.getText();
            String tenNV = tenNVField.getText();
            String chucVu = chucVuField.getText();
            String sdt = sdtNVField.getText();
            nv = new NhanVien(maNV, tenNV, chucVu, sdt);
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
            km = new KhuyenMai(idKM, moTa, giaTriGiam, ngayBatDau, ngayKetThuc);
            khuyenMaiDao.themKhuyenMai(km);
            dsKhuyenMai.add(km);
            loadKhuyenMaiTableData();
            clearKhuyenMaiForm();
            JOptionPane.showMessageDialog(this, "Thêm khuyến mãi thành công!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng dữ liệu: " + e.getMessage());
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
            km = new KhuyenMai(idKM, moTa, giaTriGiam, ngayBatDau, ngayKetThuc);
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
    
    private void xemTheoDanhMuc() {
        danhMucCombo = new JComboBox<>();
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

    private void taoBaoCaoDoanhThu() {
        tuNgayField = new JTextField(15);
        denNgayField = new JTextField(15);

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
                bc = new BaoCao("BC" + System.currentTimeMillis(), "Doanh Thu", "Báo Cáo Doanh Thu", loggedInNhanVien);
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
                bc = new BaoCao("BC" + System.currentTimeMillis(), "Tồn Kho", "Báo Cáo Tồn Kho", loggedInNhanVien);
                bc.taoBaoCaoTonKho();
                baoCaoDao.save(bc);
                JOptionPane.showMessageDialog(this, "Tạo báo cáo tồn kho thành công!");
                showBaoCaoPanel();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
            }
        }
    }

    private JPanel createBaoCaoPanel() {
        panel = new JPanel(new BorderLayout());
        baoCaoTextArea = new JTextArea();
        baoCaoTextArea.setEditable(false);
        scrollPane = new JScrollPane(baoCaoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        dsBaoCao = baoCaoDao.findAll();
        StringBuilder sb = new StringBuilder();
        for (BaoCao bc : dsBaoCao) {
            sb.append("ID: ").append(bc.getIdBaoCao())
              .append(", Loại: ").append(bc.getLoaiBaoCao())
              .append(", Tên: ").append(bc.getTenBaoCao())
              .append(", Nhân viên: ").append(bc.getNhanVien().getTenNhanVien())
              .append(" (Mã NV: ").append(bc.getNhanVien().getMaNV()).append(")")
              .append("\nNội dung: ").append(bc.getNoiDung())
              .append("\n----------------------------------------\n");
        }
        baoCaoTextArea.setText(sb.toString());

        buttonPanel = new JPanel(new FlowLayout());
        doanhThuButton = new JButton("Báo Cáo Doanh Thu");
        tonKhoButton = new JButton("Báo Cáo Tồn Kho");
        doanhThuButton.setBackground(new Color(135,230,250));
        doanhThuButton.setBorder(new EmptyBorder(10,10,10,10));
        tonKhoButton.setBackground(new Color(135,230,250));
        tonKhoButton.setBorder(new EmptyBorder(10,10,10,10));

        doanhThuButton.addActionListener(e -> taoBaoCaoDoanhThu());
        tonKhoButton.addActionListener(e -> taoBaoCaoTonKho());

        buttonPanel.add(doanhThuButton);
        buttonPanel.add(tonKhoButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    void xemChiTietHoaDon(String maHD) {
        hd = hoaDonDao.findByMaHD(maHD);
        if (hd != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder sb = new StringBuilder();
            sb.append("Mã Hóa Đơn: ").append(hd.getMaHD()).append("\n");
            sb.append("Ngày Tạo: ").append(sdf.format(hd.getNgayTao())).append("\n\n");
            String tenKH = "Không xác định";
            if (!hd.getDsSanPham().isEmpty() && hd.getDsSanPham().get(0).getKhachHang() != null) {
                tenKH = hd.getDsSanPham().get(0).getKhachHang().getTenKH();
            }
            sb.append("Tên Khách Hàng: ").append(tenKH).append("\n\n");
            sb.append("Chi Tiết Sản Phẩm:\n");
            double tongTienTruocGiam = 0;
            int diemSuDung = hd.getDiemSuDung();
            String idKM = null;

            for (CTHoaDon ct : hd.getDsSanPham()) {
                sp = ct.getSanPham();
                int soLuong = ct.getSoLuong();
                double donGia = ct.getDonGia();
                double thanhTien = ct.getSoLuong()*ct.getDonGia(); 
                double thanhTienTruocKM = soLuong * donGia; 
                tongTienTruocGiam += thanhTienTruocKM; 
                if (ct.getIdKM() != null) {
                    idKM = ct.getIdKM();
                }
                sb.append("- Tên SP: ").append(sp.getTenSP())
                  .append(", Số Lượng: ").append(soLuong)
                  .append(", Đơn Giá: ").append(donGia)
                  .append(", Thành Tiền: ").append(thanhTien)
                  .append("\n");
            }

            double giamGiaDiem = diemSuDung * 1000; 
            double giamGiaKM = 0;
            if (idKM != null) {
                km = khuyenMaiDao.timKhuyenMai(idKM);
                if (km != null && km.kiemTraHieuLuc()) {
                    for (CTHoaDon ct : hd.getDsSanPham()) {
                        double baseTotal = ct.getSoLuong() * ct.getDonGia();
                        if (km.getGiaTriGiam() < 1) {
                            giamGiaKM += baseTotal * km.getGiaTriGiam();
                        } else {
                            giamGiaKM += km.getGiaTriGiam();
                        }
                    }
                }
            }

            double tongTienSauGiam = hd.tinhTongTien(); 

            sb.append("\nĐiểm Tích Lũy Sử Dụng: ").append(diemSuDung)
              .append(" (Giảm: ").append(giamGiaDiem).append(" VNĐ)\n");
            sb.append("Khuyến Mãi Áp Dụng: ").append(idKM != null ? idKM : "Không có");
            if (idKM != null) {
                sb.append(" (Giảm: ").append(giamGiaKM).append(" VNĐ)");
            }
            sb.append("\nTổng Tiền Trước Giảm: ").append(tongTienTruocGiam).append(" VNĐ\n");
            sb.append("Tổng Tiền Sau Giảm: ").append(tongTienSauGiam).append(" VNĐ");

            textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Chi Tiết Hóa Đơn", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Hóa đơn không tồn tại!");
        }
    }

    private void showBanHangPanel() {
        mainPanel.remove(currentPanel);
        currentPanel = createBanHangPanel();
        mainPanel.add(currentPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
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

    private void addSanPhamContextMenu() {
        contextMenu = new JPopupMenu();
        nhapKhoItem = new JMenuItem("Nhập Kho");
        xuatKhoItem = new JMenuItem("Xuất Kho");

        nhapKhoItem.addActionListener(e -> {
            int selectedRow = sanPhamTable.getSelectedRow();
            if (selectedRow >= 0) {
                String maSP = (String) sanPhamTableModel.getValueAt(selectedRow, 0);
                soLuongField = new JTextField(10);
                panel = new JPanel();
                panel.add(new JLabel("Số Lượng Nhập:"));
                panel.add(soLuongField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Nhập Kho", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int soLuong = Integer.parseInt(soLuongField.getText());
                        dsSanPhamDao.nhapKho(maSP, soLuong);
                        dsSanPham.setDsSanPham(sanPhamDao.findAll());
                        loadSanPhamTableData();
                        JOptionPane.showMessageDialog(this, "Nhập kho thành công!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            }
        });

        xuatKhoItem.addActionListener(e -> {
            int selectedRow = sanPhamTable.getSelectedRow();
            if (selectedRow >= 0) {
                String maSP = (String) sanPhamTableModel.getValueAt(selectedRow, 0);
                soLuongField = new JTextField(10);
                panel = new JPanel();
                panel.add(new JLabel("Số Lượng Xuất:"));
                panel.add(soLuongField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Xuất Kho", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int soLuong = Integer.parseInt(soLuongField.getText());
                        dsSanPhamDao.xuatKho(maSP, soLuong);
                        dsSanPham.setDsSanPham(sanPhamDao.findAll());
                        loadSanPhamTableData();
                        JOptionPane.showMessageDialog(this, "Xuất kho thành công!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm!");
            }
        });

        contextMenu.add(nhapKhoItem);
        contextMenu.add(xuatKhoItem);

        sanPhamTable.setComponentPopupMenu(contextMenu);
    }

    private void addKhachHangContextMenu() {
        contextMenu = new JPopupMenu();
        congDiemItem = new JMenuItem("Cộng Điểm Tích Lũy");

        congDiemItem.addActionListener(e -> {
            int selectedRow = khachHangTable.getSelectedRow();
            if (selectedRow >= 0) {
                String maKH = (String) khachHangTableModel.getValueAt(selectedRow, 0);
                diemField = new JTextField(10);
                panel = new JPanel();
                panel.add(new JLabel("Điểm Cộng:"));
                panel.add(diemField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Cộng Điểm Tích Lũy", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        int diem = Integer.parseInt(diemField.getText());
                        kh = khachHangDao.findByMaKH(maKH);
                        if (kh != null) {
                            kh.congDiemTichLuy(diem);
                            khachHangDao.capNhatKhachHang(kh);
                            dsKhachHang = khachHangDao.findAll();
                            loadKhachHangTableData();
                            JOptionPane.showMessageDialog(this, "Cộng điểm tích lũy thành công!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Khách hàng không tồn tại!");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng!");
            }
        });

        contextMenu.add(congDiemItem);
        khachHangTable.setComponentPopupMenu(contextMenu);
    }

    private void addHoaDonContextMenu() {
        contextMenu = new JPopupMenu();
        xemChiTietItem = new JMenuItem("Xem Chi Tiết");

        xemChiTietItem.addActionListener(e -> {
            int selectedRow = hoaDonTable.getSelectedRow();
            if (selectedRow >= 0) {
                String maHD = (String) hoaDonTableModel.getValueAt(selectedRow, 0);
                xemChiTietHoaDon(maHD);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn!");
            }
        });

        contextMenu.add(xemChiTietItem);
        hoaDonTable.setComponentPopupMenu(contextMenu);
    }

	private void makeComboBoxSearchable(JComboBox<String> comboBox) {
	    JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();
	    comboBox.setEditable(true);
	    editor.addKeyListener(new java.awt.event.KeyAdapter() {
	        @Override
	        public void keyReleased(java.awt.event.KeyEvent e) {
	            String text = editor.getText();
	            comboBox.removeAllItems();
	            if (comboBox == khachHangComboBox) {
	                comboBox.addItem("Khách vãng lai"); 
	                for (KhachHang kh : dsKhachHang) {
	                    String item = kh.getMaKH() + " - " + kh.getTenKH();
	                    if (item.toLowerCase().contains(text.toLowerCase())) {
	                        comboBox.addItem(item);
	                    }
	                }
	            } else if (comboBox == sanPhamComboBox) {
	                for (SanPham sp : dsSanPham.getDsSanPham()) {
	                    String item = sp.getMaSP() + " - " + sp.getTenSP();
	                    if (item.toLowerCase().contains(text.toLowerCase())) {
	                        comboBox.addItem(item);
	                    }
	                }
	            }
	            editor.setText(text); 
	            comboBox.showPopup();
	        }
	    });
	}
	private void updateTongTien() {
	    try {
	        String spStr = (String) sanPhamComboBox.getSelectedItem();
	        if (spStr == null) {
	            tongTienField.setText("0");
	            return;
	        }
	        String maSP = spStr.split(" - ")[0];
	        SanPham sp = sanPhamDao.findByMaSP(maSP);
	        if (sp == null) {
	            tongTienField.setText("0");
	            return;
	        }
	
	        int soLuong = Integer.parseInt(soLuongField.getText());
	        if (soLuong <= 0) {
	            tongTienField.setText("0");
	            return;
	        }
	        
	        double thanhTien = soLuong * sp.getGia();
	        tongTienField.setText(String.valueOf(thanhTien));
	    } catch (Exception ex) {
	        tongTienField.setText("0"); 
	    }
	}
	private void capNhatTongTienBanHang() {
	    double tongTien = 0;
	    for (int i = 0; i < banHangTableModel.getRowCount(); i++) {
	        Object obj = banHangTableModel.getValueAt(i, 4); 
	        if (obj instanceof Number) {
	            tongTien += ((Number) obj).doubleValue();
	        }
	    }
	    tongTienLabel.setText("Tổng tiền: " + String.format("%,.0f VNĐ", tongTien));
	}

}