-- Tạo cơ sở dữ liệu
CREATE DATABASE quanlycuahangtienloi;
GO

USE quanlycuahangtienloi;
GO

-- Bảng DanhMucSP
CREATE TABLE DanhMucSP (
    MaDanhMuc VARCHAR(20) PRIMARY KEY,
    TenDanhMuc NVARCHAR(100) NOT NULL,
    CONSTRAINT CHK_DanhMucSP_MaDanhMuc CHECK (MaDanhMuc <> ''),
    CONSTRAINT CHK_DanhMucSP_TenDanhMuc CHECK (TenDanhMuc <> '')
);
GO

-- Bảng NhaCungCap
CREATE TABLE NhaCungCap (
    MaNCC VARCHAR(20) PRIMARY KEY,
    TenNCC NVARCHAR(100) NOT NULL,
    CONSTRAINT CHK_NhaCungCap_MaNCC CHECK (MaNCC <> ''),
    CONSTRAINT CHK_NhaCungCap_TenNCC CHECK (TenNCC <> '')
);
GO

-- Bảng SanPham
CREATE TABLE SanPham (
    MaSP VARCHAR(20) PRIMARY KEY,
    TenSP NVARCHAR(100) NOT NULL,
    SoLuongTon INT NOT NULL CHECK (SoLuongTon >= 0),
    HSD DATE NOT NULL,
    MaNCC VARCHAR(20) NOT NULL,
    Gia FLOAT NOT NULL CHECK (Gia >= 0),
    MaDanhMuc VARCHAR(20) NOT NULL,
    CONSTRAINT FK_SanPham_NhaCungCap FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    CONSTRAINT FK_SanPham_DanhMucSP FOREIGN KEY (MaDanhMuc) REFERENCES DanhMucSP(MaDanhMuc),
    CONSTRAINT CHK_SanPham_MaSP CHECK (MaSP <> ''),
    CONSTRAINT CHK_SanPham_TenSP CHECK (TenSP <> '')
);
GO

-- Bảng KhachHang
CREATE TABLE KhachHang (
    MaKH VARCHAR(20) PRIMARY KEY,
    TenKH NVARCHAR(100) NOT NULL,
    Sdt VARCHAR(11) NOT NULL CHECK (Sdt LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR Sdt LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    Email VARCHAR(100) NOT NULL CHECK (Email LIKE '%@%.%'),
    DiemTichLuy INT NOT NULL CHECK (DiemTichLuy >= 0),
    CONSTRAINT CHK_KhachHang_MaKH CHECK (MaKH <> ''),
    CONSTRAINT CHK_KhachHang_TenKH CHECK (TenKH <> '')
);
GO

-- Bảng NhanVien
CREATE TABLE NhanVien (
    MaNV VARCHAR(20) PRIMARY KEY,
    TenNhanVien NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(50) NOT NULL,
    Sdt VARCHAR(11) NOT NULL CHECK (Sdt LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR Sdt LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    CONSTRAINT CHK_NhanVien_MaNV CHECK (MaNV <> ''),
    CONSTRAINT CHK_NhanVien_TenNhanVien CHECK (TenNhanVien <> ''),
    CONSTRAINT CHK_NhanVien_ChucVu CHECK (ChucVu <> '')
);
GO

-- Bảng KhuyenMai
CREATE TABLE KhuyenMai (
    IdKM VARCHAR(20) PRIMARY KEY,
    MoTa NVARCHAR(200) NOT NULL,
    GiaTriGiam FLOAT NOT NULL CHECK (GiaTriGiam >= 0),
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    CONSTRAINT CHK_KhuyenMai_IdKM CHECK (IdKM <> ''),
    CONSTRAINT CHK_KhuyenMai_MoTa CHECK (MoTa <> ''),
    CONSTRAINT CHK_KhuyenMai_Ngay CHECK (NgayKetThuc >= NgayBatDau)
);
GO

-- Bảng HoaDon
CREATE TABLE HoaDon (
    MaHD VARCHAR(20) PRIMARY KEY,
    NgayTao DATE NOT NULL,
    MaKH VARCHAR(20) NULL,
    MaNV VARCHAR(20) NULL,
    CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT CHK_HoaDon_MaHD CHECK (MaHD <> '')
);
GO

-- Bảng CTHoaDon
CREATE TABLE CTHoaDon (
    MaHD VARCHAR(20),
    MaSP VARCHAR(20),
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    DonGia FLOAT NOT NULL CHECK (DonGia >= 0),
    TongTien FLOAT NOT NULL CHECK (TongTien >= 0),
    PRIMARY KEY (MaHD, MaSP),
    CONSTRAINT FK_CTHoaDon_HoaDon FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    CONSTRAINT FK_CTHoaDon_SanPham FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);
GO

-- Bảng BaoCao
CREATE TABLE BaoCao (
    IdBaoCao VARCHAR(20) PRIMARY KEY,
    LoaiBaoCao NVARCHAR(50) NOT NULL,
    TenBaoCao NVARCHAR(100) NOT NULL,
    NoiDung NVARCHAR(MAX) NOT NULL,
    CONSTRAINT CHK_BaoCao_IdBaoCao CHECK (IdBaoCao <> ''),
    CONSTRAINT CHK_BaoCao_LoaiBaoCao CHECK (LoaiBaoCao <> ''),
    CONSTRAINT CHK_BaoCao_TenBaoCao CHECK (TenBaoCao <> '')
);
GO

-- Chèn dữ liệu vào bảng DanhMucSP
INSERT INTO DanhMucSP (MaDanhMuc, TenDanhMuc) VALUES
('DM001', N'Thực phẩm'),
('DM002', N'Đồ uống'),
('DM003', N'Đồ gia dụng');
GO

-- Chèn dữ liệu vào bảng NhaCungCap
INSERT INTO NhaCungCap (MaNCC, TenNCC) VALUES
('NCC001', N'Công ty Vinamilk'),
('NCC002', N'Công ty PepsiCo'),
('NCC003', N'Công ty Unilever');
GO

-- Chèn dữ liệu vào bảng SanPham
INSERT INTO SanPham (MaSP, TenSP, SoLuongTon, HSD, MaNCC, Gia, MaDanhMuc) VALUES
('SP001', N'Sữa tươi Vinamilk', 100, '2025-12-31', 'NCC001', 25000, 'DM001'),
('SP002', N'Pepsi lon', 200, '2025-06-30', 'NCC002', 12000, 'DM002'),
('SP003', N'Nước rửa chén Sunlight', 50, '2026-01-15', 'NCC003', 45000, 'DM003'),
('SP004', N'Bánh quy Cosy', 150, '2025-09-30', 'NCC001', 30000, 'DM001');
GO

-- Chèn dữ liệu vào bảng KhachHang
INSERT INTO KhachHang (MaKH, TenKH, Sdt, Email, DiemTichLuy) VALUES
('KH001', N'Nguyễn Văn An', '0901234567', 'an.nguyen@gmail.com', 100),
('KH002', N'Trần Thị Bình', '0912345678', 'binh.tran@gmail.com', 50),
('KH003', N'Lê Minh Châu', '0923456789', 'chau.le@gmail.com', 0);
GO

-- Chèn dữ liệu vào bảng NhanVien
INSERT INTO NhanVien (MaNV, TenNhanVien, ChucVu, Sdt) VALUES
('NV001', N'Phạm Quốc Đạt', N'Quản lý', '0931234567'),
('NV002', N'Hoàng Thị Mai', N'Nhân viên bán hàng', '0942345678'),
('NV003', N'Vũ Văn Hùng', N'Nhân viên kho', '0953456789');
GO

-- Chèn dữ liệu vào bảng KhuyenMai
INSERT INTO KhuyenMai (IdKM, MoTa, GiaTriGiam, NgayBatDau, NgayKetThuc) VALUES
('KM001', N'Giảm giá 10% cho thực phẩm', 10, '2025-04-01', '2025-04-30'),
('KM002', N'Giảm giá 5000đ cho đồ uống', 5000, '2025-04-15', '2025-05-15'),
('KM003', N'Giảm giá 15% cho hóa đơn trên 100000', 15, '2025-04-20', '2025-05-20');
GO

-- Chèn dữ liệu vào bảng HoaDon
INSERT INTO HoaDon (MaHD, NgayTao, MaKH, MaNV) VALUES
('HD001', '2025-04-20', 'KH001', 'NV002'),
('HD002', '2025-04-21', 'KH002', 'NV002'),
('HD003', '2025-04-22', NULL, 'NV003');
GO

-- Chèn dữ liệu vào bảng CTHoaDon
INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia, TongTien) VALUES
('HD001', 'SP001', 5, 25000, 125000),  -- 5 hộp sữa Vinamilk
('HD001', 'SP002', 10, 12000, 120000), -- 10 lon Pepsi
('HD002', 'SP003', 2, 45000, 90000),   -- 2 chai Sunlight
('HD003', 'SP004', 3, 30000, 90000);   -- 3 gói bánh Cosy
GO

-- Chèn dữ liệu vào bảng BaoCao
INSERT INTO BaoCao (IdBaoCao, LoaiBaoCao, TenBaoCao, NoiDung) VALUES
('BC001', N'Doanh thu', N'Báo cáo doanh thu tháng 4/2025', N'Báo cáo doanh thu từ 01/04/2025 đến 30/04/2025\nChi tiết:\nHóa đơn: HD002, Ngày: 21/04/2025, Doanh thu: 90000\nTổng doanh thu: 90000'),
('BC002', N'Tồn kho', N'Báo cáo tồn kho ngày 22/04/2025', N'Báo cáo tồn kho tại thời điểm 22/04/2025 10:00:00\nChi tiết:\nSản phẩm: SP001, Tên: Sữa tươi Vinamilk, Số lượng tồn: 95, HSD: 31/12/2025, Danh mục: Thực phẩm\nSản phẩm: SP002, Tên: Pepsi lon, Số lượng tồn: 190, HSD: 30/06/2025, Danh mục: Đồ uống\n...');
GO