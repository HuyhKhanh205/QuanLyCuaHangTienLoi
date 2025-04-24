-- Tạo database
CREATE DATABASE quanlycuahangtienloi;
GO

-- Sử dụng database
USE quanlycuahangtienloi;
GO

-- Tạo bảng NhaCungCap
CREATE TABLE NhaCungCap (
    MaNCC NVARCHAR(50) PRIMARY KEY,
    TenNCC NVARCHAR(255) NOT NULL
);

-- Tạo bảng DanhMucSP
CREATE TABLE DanhMucSP (
    MaDanhMuc NVARCHAR(50) PRIMARY KEY,
    TenDanhMuc NVARCHAR(255) NOT NULL
);

-- Tạo bảng SanPham
CREATE TABLE SanPham (
    MaSP NVARCHAR(50) PRIMARY KEY,
    TenSP NVARCHAR(255) NOT NULL,
    SoLuongTon INT NOT NULL CHECK (SoLuongTon >= 0),
    HSD DATE NOT NULL,
    Gia FLOAT NOT NULL CHECK (Gia >= 0),
    MaNCC NVARCHAR(50),
    MaDanhMuc NVARCHAR(50),
    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC),
    FOREIGN KEY (MaDanhMuc) REFERENCES DanhMucSP(MaDanhMuc)
);

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKH NVARCHAR(50) PRIMARY KEY,
    TenKH NVARCHAR(255) NOT NULL,
    SDT NVARCHAR(11) NOT NULL CHECK (SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
    Email NVARCHAR(255) NOT NULL CHECK (Email LIKE '%_@__%.__%'),
    DiemTichLuy INT NOT NULL CHECK (DiemTichLuy >= 0)
);

-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNV NVARCHAR(50) PRIMARY KEY,
    TenNhanVien NVARCHAR(255) NOT NULL,
    ChucVu NVARCHAR(100) NOT NULL,
    SDT NVARCHAR(11) NOT NULL CHECK (SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]' OR SDT LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);

-- Tạo bảng KhuyenMai
CREATE TABLE KhuyenMai (
    IDKM NVARCHAR(50) PRIMARY KEY,
    MoTa NVARCHAR(255) NOT NULL,
    GiaTriGiam FLOAT NOT NULL CHECK (GiaTriGiam >= 0),
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    CHECK (NgayKetThuc >= NgayBatDau)
);

-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHD NVARCHAR(50) PRIMARY KEY,
    NgayTao DATE NOT NULL,
    MaKH NVARCHAR(50) NULL,
    MaNV NVARCHAR(50) NULL,
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
);

-- Tạo bảng CTHoaDon
CREATE TABLE CTHoaDon (
    MaHD NVARCHAR(50),
    MaSP NVARCHAR(50),
    SoLuong INT NOT NULL CHECK (SoLuong > 0),
    DonGia FLOAT NOT NULL CHECK (DonGia >= 0),
    TongTien FLOAT NOT NULL CHECK (TongTien >= 0),
    MaKH NVARCHAR(50) NULL,
    PRIMARY KEY (MaHD, MaSP),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
);

-- Tạo bảng BaoCao
CREATE TABLE BaoCao (
    IDBaoCao NVARCHAR(50) PRIMARY KEY,
    LoaiBaoCao NVARCHAR(100) NOT NULL,
    TenBaoCao NVARCHAR(255) NOT NULL,
    NoiDung NVARCHAR(MAX) NOT NULL
);
GO
-- Chèn dữ liệu vào NhaCungCap
INSERT INTO NhaCungCap (MaNCC, TenNCC) VALUES
('NCC001', N'Công ty TNHH Vinamilk'),
('NCC002', N'Công ty CP Bánh kẹo Hải Hà'),
('NCC003', N'Công ty TNHH Unilever Việt Nam');

-- Chèn dữ liệu vào DanhMucSP
INSERT INTO DanhMucSP (MaDanhMuc, TenDanhMuc) VALUES
('DM001', N'Sữa và sản phẩm từ sữa'),
('DM002', N'Bánh kẹo'),
('DM003', N'Đồ gia dụng');

-- Chèn dữ liệu vào SanPham
INSERT INTO SanPham (MaSP, TenSP, SoLuongTon, HSD, Gia, MaNCC, MaDanhMuc) VALUES
('SP001', N'Sữa tươi Vinamilk 1L', 100, '2025-12-31', 30000, 'NCC001', 'DM001'),
('SP002', N'Bánh quy Hải Hà 200g', 50, '2025-06-30', 25000, 'NCC002', 'DM002'),
('SP003', N'Sữa chua Vinamilk', 200, '2025-05-31', 10000, 'NCC001', 'DM001'),
('SP004', N'Kẹo dẻo Haribo 100g', 80, '2025-09-30', 35000, 'NCC002', 'DM002'),
('SP005', N'Nước rửa chén Sunlight 1L', 30, '2026-01-31', 45000, 'NCC003', 'DM003');

-- Chèn dữ liệu vào KhachHang
INSERT INTO KhachHang (MaKH, TenKH, SDT, Email, DiemTichLuy) VALUES
('KH001', N'Nguyễn Văn An', '0901234567', 'an.nguyen@gmail.com', 100),
('KH002', N'Trần Thị Bình', '0912345678', 'binh.tran@gmail.com', 50),
('KH003', N'Lê Minh Châu', '0923456789', 'chau.le@gmail.com', 200);

-- Chèn dữ liệu vào NhanVien
INSERT INTO NhanVien (MaNV, TenNhanVien, ChucVu, SDT) VALUES
('NV001', N'Phạm Quốc Hùng', N'Quản lý', '0931234567'),
('NV002', N'Hoàng Thị Mai', N'Nhân viên bán hàng', '0942345678');

-- Chèn dữ liệu vào KhuyenMai
INSERT INTO KhuyenMai (IDKM, MoTa, GiaTriGiam, NgayBatDau, NgayKetThuc) VALUES
('KM001', N'Giảm 10% cho sữa', 0.1, '2025-04-01', '2025-04-30'),
('KM002', N'Giảm 5000đ cho bánh kẹo', 5000, '2025-04-15', '2025-05-15');

-- Chèn dữ liệu vào HoaDon
INSERT INTO HoaDon (MaHD, NgayTao, MaKH, MaNV) VALUES
('HD001', '2025-04-20', 'KH001', 'NV002'),
('HD002', '2025-04-22', 'KH002', 'NV002');

-- Chèn dữ liệu vào CTHoaDon
INSERT INTO CTHoaDon (MaHD, MaSP, SoLuong, DonGia, TongTien, MaKH) VALUES
('HD001', 'SP001', 2, 30000, 60000, 'KH001'),
('HD001', 'SP002', 1, 25000, 25000, 'KH001'),
('HD002', 'SP003', 3, 10000, 30000, 'KH002'),
('HD002', 'SP004', 2, 35000, 70000, 'KH002');

-- Chèn dữ liệu vào BaoCao
INSERT INTO BaoCao (IDBaoCao, LoaiBaoCao, TenBaoCao, NoiDung) VALUES
('BC001', N'DoanhThu', N'Báo cáo doanh thu tháng 4/2025', N'Báo cáo doanh thu từ 01/04/2025 đến 30/04/2025\nChi tiết:\nHóa đơn: HD001, Ngày: 20/04/2025, Doanh thu: 85000\nHóa đơn: HD002, Ngày: 22/04/2025, Doanh thu: 100000\nTổng doanh thu: 185000'),
('BC002', N'TonKho', N'Báo cáo tồn kho ngày 24/04/2025', N'Báo cáo tồn kho tại thời điểm 24/04/2025 10:00:00\nChi tiết:\nSản phẩm: SP001, Tên: Sữa tươi Vinamilk 1L, Số lượng tồn: 100, HSD: 31/12/2025, Danh mục: Sữa và sản phẩm từ sữa\nSản phẩm: SP002, Tên: Bánh quy Hải Hà 200g, Số lượng tồn: 50, HSD: 30/06/2025, Danh mục: Bánh kẹo');
