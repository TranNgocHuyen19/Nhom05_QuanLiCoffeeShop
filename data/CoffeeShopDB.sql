create database CoffeeShop
go

use CoffeeShop
go
----------------------------------------------------------------------Create table------------------------------------------------------------------------------

CREATE TABLE [dbo].[Category](
	[CategoryID] [nvarchar](10) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Description] [nvarchar](100) NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Employee](
	[EmployeeID] [nvarchar](10) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[Salary] [money] NOT NULL,
	[Shift] [nvarchar](10) NOT NULL,
	[Username] [nvarchar](15) NOT NULL,
	[PhoneNumber] [nvarchar](20) NOT NULL,
	[Address] [nvarchar](50) NULL,
	[Password] [nvarchar](10) NOT NULL,
	[Role] [nvarchar](10) NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Payment](
	[PaymentID] [nvarchar](20) NOT NULL,
	[PaymentDate] [datetime] NOT NULL,
	[Type] [nvarchar](15) NOT NULL,
 CONSTRAINT [PK_Payment] PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[Customer](
	[CustomerID] [nvarchar](10) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[PhoneNumber] [nvarchar](20) NOT NULL,
	[RewardPoint] [int] NULL,
 CONSTRAINT [PK_Customer] PRIMARY KEY CLUSTERED 
(
	[CustomerID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE TABLE [dbo].[Beverage](
	[BeverageID] [nvarchar](10) NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	[PurchasePrice] [money] NOT NULL,
	[ModifiedDate] [date] NOT NULL,
	[CategoryID] [nvarchar](10) NOT NULL,
	[Discontinued] [bit] NOT NULL,
	[Tax] [float] NOT NULL,
	[Image] [nvarchar](100) NULL,
 CONSTRAINT [PK_Beverage] PRIMARY KEY CLUSTERED 
(
	[BeverageID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Beverage]  WITH CHECK ADD  CONSTRAINT [FK_Beverage_Category] FOREIGN KEY([CategoryID])
REFERENCES [dbo].[Category] ([CategoryID])
ON UPDATE CASCADE
GO

ALTER TABLE [dbo].[Beverage] CHECK CONSTRAINT [FK_Beverage_Category]
GO

CREATE TABLE [dbo].[Order](
	[OrderID] [nvarchar](20) NOT NULL,
	[OrderDate] [datetime] NULL,
	[EmpID] [nvarchar](10) NOT NULL,
	[PaymentID] [nvarchar](20) NOT NULL,
	[CustomerID] [nvarchar](10) NOT NULL,
	[TotalDue] [money] NOT NULL,
	[Discount] [money] NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK__Order__Customer] FOREIGN KEY([CustomerID])
REFERENCES [dbo].[Customer] ([CustomerID])
GO

ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK__Order__Customer]
GO

ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Employee] FOREIGN KEY([EmpID])
REFERENCES [dbo].[Employee] ([EmployeeID])
GO

ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Employee]
GO

ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_Order_Payment] FOREIGN KEY([PaymentID])
REFERENCES [dbo].[Payment] ([PaymentID])
GO

ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_Order_Payment]
GO


CREATE TABLE [dbo].[OrderDetail](
	[OrderID] [nvarchar](20) NOT NULL,
	[BeverageID] [nvarchar](10) NOT NULL,
	[UnitPrice] [money] NOT NULL,
	[OrderQty] [int] NOT NULL,
	[Description] [nvarchar](100) NULL,
 CONSTRAINT [PK_OrderDetail] PRIMARY KEY CLUSTERED 
(
	[OrderID] ASC,
	[BeverageID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Beverage] FOREIGN KEY([BeverageID])
REFERENCES [dbo].[Beverage] ([BeverageID])
GO

ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Beverage]
GO

ALTER TABLE [dbo].[OrderDetail]  WITH CHECK ADD  CONSTRAINT [FK_OrderDetail_Order] FOREIGN KEY([OrderID])
REFERENCES [dbo].[Order] ([OrderID])
GO

ALTER TABLE [dbo].[OrderDetail] CHECK CONSTRAINT [FK_OrderDetail_Order]
GO


----------------------------------------------------------------------Category------------------------------------------------------------------------------
insert into category values
('C001', N'Thức uống Espresso', '...'),
('C002', N'Thức uống với Cappuccino', '...'),
('C003', N'Kem trộn Cappuccino', '...'),
('C004', N'Trà pha', '...')
go
----------------------------------------------------------------------Customer------------------------------------------------------------------------------
INSERT INTO customer
VALUES
('Cust0000', 'DEFAULT_CUSTOMER', '0', 0),
('Cust0001', 'John Doe', '0000000001', 0),
('Cust0002', 'Jane Smith', '0000000002', 0),
('Cust0003', 'Alice Johnson', '0000000003', 0),
('Cust0004', 'David', '0000000004', 0),
('Cust0005', 'White', '0000000005', 0),
('Cust0006', 'Daisy', '0000000006', 0),
('Cust0007', 'Emma', '0000000007', 0),
('Cust0008', 'Michael', '0000000008', 0),
('Cust0009', 'Sophia', '0000000009', 0),
('Cust0010', 'James', '0000000010', 0),
('Cust0011', 'Olivia', '0000000011', 0),
('Cust0012', 'William', '0000000012', 0),
('Cust0013', 'James', '0000000013', 0),
('Cust0014', 'Olivia', '0000000014', 0),
('Cust0015', 'Will', '0000000015', 0),
('Cust0016', 'James Meth', '0000000016', 0),
('Cust0017', 'David', '0000000017', 0);
go
----------------------------------------------------------------------Employee------------------------------------------------------------------------------
INSERT INTO [dbo].[Employee] 
    ([EmployeeID], [Name], [Salary], [Shift], [Username], [PhoneNumber], [Address], [Password], [Role])
VALUES
    ('Emp0000', 'admin', 0.00, 1, 'admin', '0', 'admin', 1, 'Quản Lí'),
    ('Emp0001', 'Trần Ngọc Huyền', 30000.00, 1, 'ngochuyen', '123', 'IUH', 1, 'Nhân Viên'),
    ('Emp0002', 'Nghiêm Chí Thiện', 22000.00, 3, 'chithien', '789', 'XYZ', 1, 'Nhân Viên'),
    ('Emp0003', 'Đào Thị Mỹ Linh', 20000.00, 2, 'mylinh', '987', 'DEF', 1, 'Nhân Viên'),
    ('Emp0004', 'Dương Hoàng Huy', 25000.00, 2, 'hoanghuy', '456', 'ABC', 1, 'Nhân Viên');
go
----------------------------------------------------------------------Beverage------------------------------------------------------------------------------
INSERT INTO Beverage (BeverageID, Name, PurchasePrice, ModifiedDate, CategoryID, Discontinued, Tax, Image)
VALUES 
('B001', 'Hazelnut', 70000.00, '2024-04-20', 'C001', 0, 0, 'espresso_01.jpg'),
('B002', 'Ristretto Bianco', 40000.00, '2024-04-20', 'C001', 0, 0, 'espresso_02.jpg'),
('B003', 'Dolce Latte', 30000.00, '2024-04-20', 'C001', 0, 0, 'espresso_03.jpg'),
('B004', 'Americano', 70000.00, '2024-04-20', 'C001', 0, 0, 'espresso_04.jpg'),
('B005', 'Café Latte', 50000.00, '2024-04-20', 'C001', 0, 0, 'espresso_05.jpg'),
('B006', 'Café Mocha', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_06.jpg'),
('B007', 'Flavored Latte', 70000.00, '2024-04-20', 'C001', 0, 0, 'espresso_07.jpg'),
('B008', 'Skinny Latte', 50000.00, '2024-04-20', 'C001', 0, 0, 'espresso_08.jpg'),
('B009', 'Cappuccino', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_09.jpg'),
('B010', 'Macchiato', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_10.jpg'),
('B011', 'Espresso', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_11.jpg'),
('B012', 'Espresso Panna', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_12.jpg'),
('B013', 'Esp-Macchiato', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_13.jpg'),
('B014', 'Iced Americano', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_14.jpg'),
('B015', 'Iced Caffè Latte', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_15.jpg'),
('B016', 'Iced Mocha', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_16.jpg'),
('B017', 'Iced Macchiato', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_17.jpg'),
('B018', 'Iced Latte', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_18.jpg'),
('B019', 'Iced Skinny Latte', 60000.00, '2024-04-20', 'C001', 0, 0, 'espresso_19.jpg'),
('B021', 'Coffee Blended', 30000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_01.jpg'),
('B022', 'Espresso Blended', 40000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_02.jpg'),
('B023', 'Caramel Blended', 30000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_03.jpg'),
('B024', 'Mocha Blended', 30000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_04.jpg'),
('B025', 'Mocha Light', 40000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_05.jpg'),
('B026', 'Java Chip', 30000.00, '2024-04-20', 'C002', 0, 0, 'frappuccino_06.jpg'),
('B027', 'Vanilla Cream', 30000.00, '2024-04-20', 'C003', 0, 0, 'kemTronFrappuccino_01.jpg'),
('B028', 'Caramel', 40000.00, '2024-04-20', 'C003', 0, 0, 'kemTronFrappuccino_02.jpg'),
('B029', 'Chocolate', 30000.00, '2024-04-20', 'C003', 0, 0, 'kemTronFrappuccino_03.jpg'),
('B030', 'Mango', 30000.00, '2024-04-20', 'C003', 0, 0, 'kemTronFrappuccino_04.jpg'),
('B031', 'Raspberry', 40000.00, '2024-04-20', 'C003', 0, 0, 'kemTronFrappuccino_05.jpg'),
('B032', 'Chai Tea', 70000.00, '2024-04-20', 'C004', 0, 0, 'traPha_01.jpg'),
('B033', 'Chai Latte', 40000.00, '2024-04-20', 'C004', 0, 0, 'traPha_02.jpg'),
('B034', 'Iced Chai', 30000.00, '2024-04-20', 'C004', 0, 0, 'traPha_03.jpg'),
('B035', 'Chamomile Blend', 70000.00, '2024-04-20', 'C004', 0, 0, 'traPha_04.jpg'),
('B036', 'English Breakfast', 50000.00, '2024-04-20', 'C004', 0, 0, 'traPha_05.jpg'),
('B037', 'Mint Blend', 60000.00, '2024-04-20', 'C004', 0, 0, 'traPha_06.jpg'),
('B038', 'Spearmint Green', 70000.00, '2024-04-20', 'C004', 0, 0, 'traPha_07.jpg'),
('B039', 'Earl Grey', 50000.00, '2024-04-20', 'C004', 0, 0, 'traPha_08.jpg'),
('B040', 'Iced Shaken', 60000.00, '2024-04-20', 'C004', 0, 0, 'traPha_09.jpg'),
('B041', 'Black Shaken', 60000.00, '2024-04-20', 'C004', 0, 0, 'traPha_10.jpg')
go
----------------------------------------------------------------------Payment------------------------------------------------------------------------------
----Nam 2020
-- Tháng 1: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0101200001', '2020-01-01', 'Ngân Hàng');

-- Tháng 2: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0102200001', '2020-02-01', 'Tiền Mặt');

-- Tháng 3: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0103200001', '2020-03-01', 'Ví Điện Tử');

-- Tháng 4: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0104200001', '2020-04-01', 'Ngân Hàng');

-- Tháng 5: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0105200001', '2020-05-01', 'Tiền Mặt');

-- Tháng 6: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0106200001', '2020-06-01', 'Ví Điện Tử');

-- Tháng 7: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0107200001', '2020-07-01', 'Ngân Hàng');

-- Tháng 8: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0108200001', '2020-08-01', 'Tiền Mặt');

-- Tháng 9: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0109200001', '2020-09-01', 'Ví Điện Tử');

-- Tháng 10: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0110200001', '2020-10-01', 'Ngân Hàng');

-- Tháng 11: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0111200001', '2020-11-01', 'Tiền Mặt');

-- Tháng 12: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0112200001', '2020-12-01', 'Ví Điện Tử');

----Nam 2021
-- Tháng 1: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0101210001', '2021-01-01', 'Ngân Hàng');

-- Tháng 2: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0102210001', '2021-02-01', 'Tiền Mặt');

-- Tháng 3: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0103210001', '2021-03-01', 'Ví Điện Tử');

-- Tháng 4: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0104210001', '2021-04-01', 'Ngân Hàng');

-- Tháng 5: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0105210001', '2021-05-01', 'Tiền Mặt');

-- Tháng 6: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0106210001', '2021-06-01', 'Ví Điện Tử');

-- Tháng 7: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0107210001', '2021-07-01', 'Ngân Hàng');

-- Tháng 8: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0108210001', '2021-08-01', 'Tiền Mặt');

-- Tháng 9: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0109210001', '2021-09-01', 'Ví Điện Tử');

-- Tháng 10: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0110210001', '2021-10-01', 'Ngân Hàng');

-- Tháng 11: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0111210001', '2021-11-01', 'Tiền Mặt');

-- Tháng 12: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0112210001', '2021-12-01', 'Ví Điện Tử');


----Nam 2022
-- Tháng 1: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0101220001', '2022-01-01', 'Ngân Hàng');

-- Tháng 2: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0102220001', '2022-02-01', 'Tiền Mặt');

-- Tháng 3: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0103220001', '2022-03-01', 'Ví Điện Tử');

-- Tháng 4: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0104220001', '2022-04-01', 'Ngân Hàng');

-- Tháng 5: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0105220001', '2022-05-01', 'Tiền Mặt');

-- Tháng 6: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0106220001', '2022-06-01', 'Ví Điện Tử');

-- Tháng 7: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0107220001', '2022-07-01', 'Ngân Hàng');

-- Tháng 8: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0108220001', '2022-08-01', 'Tiền Mặt');

-- Tháng 9: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0109220001', '2022-09-01', 'Ví Điện Tử');

-- Tháng 10: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0110220001', '2022-10-01', 'Ngân Hàng');

-- Tháng 11: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0111220001', '2022-11-01', 'Tiền Mặt');

-- Tháng 12: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0112220001', '2022-12-01', 'Ví Điện Tử');


----Nam 2023
-- Tháng 1: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0101230001', '2023-01-01', 'Ngân Hàng');

-- Tháng 2: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0102230001', '2023-02-01', 'Tiền Mặt');

-- Tháng 3: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0103230001', '2023-03-01', 'Ví Điện Tử');

-- Tháng 4: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0104230001', '2023-04-01', 'Ngân Hàng');

-- Tháng 5: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0105230001', '2023-05-01', 'Tiền Mặt');

-- Tháng 6: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0106230001', '2023-06-01', 'Ví Điện Tử');

-- Tháng 7: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0107230001', '2023-07-01', 'Ngân Hàng');

-- Tháng 8: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0108230001', '2023-08-01', 'Tiền Mặt');

-- Tháng 9: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0109230001', '2023-09-01', 'Ví Điện Tử');

-- Tháng 10: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0110230001', '2023-10-01', 'Ngân Hàng');

-- Tháng 11: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0111230001', '2023-11-01', 'Tiền Mặt');

-- Tháng 12: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0112230001', '2023-12-01', 'Ví Điện Tử');

----Nam 2024
-- Tháng 1: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0101240001', '2024-01-01', 'Ngân Hàng');

-- Tháng 2: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0102240001', '2024-02-01', 'Tiền Mặt');

-- Tháng 3: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0103240001', '2024-03-01', 'Ví Điện Tử');

-- Tháng 4: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0104240001', '2024-04-01', 'Ngân Hàng');

-- Tháng 5: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0105240001', '2024-05-01', 'Tiền Mặt');

-- Tháng 6: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0106240001', '2024-06-01', 'Ví Điện Tử');

-- Tháng 7: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0107240001', '2024-07-01', 'Ngân Hàng');

-- Tháng 8: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0108240001', '2024-08-01', 'Tiền Mặt');

-- Tháng 9: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0109240001', '2024-09-01', 'Ví Điện Tử');

-- Tháng 10: Thanh toán bằng Ngân Hàng
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0110240001', '2024-10-01', 'Ngân Hàng');

-- Tháng 11: Thanh toán bằng Tiền Mặt
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0111240001', '2024-11-01', 'Tiền Mặt');

-- Tháng 12: Thanh toán bằng Ví Điện Tử
INSERT INTO [dbo].[Payment] ([PaymentID], [PaymentDate], [Type])
VALUES ('P0112240001', '2024-12-01', 'Ví Điện Tử');
go
----------------------------------------------------------------------Order------------------------------------------------------------------------------
---- Năm 2020
-- Tháng 1
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0101200001', '2020-01-01', 'Emp0003', 'P0101200001', 'Cust0008', 100000, 0);

-- Tháng 2
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0102200001', '2020-02-01', 'Emp0002', 'P0102200001', 'Cust0005', 200000, 10.0);

-- Tháng 3
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0103200001', '2020-03-01', 'Emp0004', 'P0103200001', 'Cust0001', 300000, 15.0);

-- Tháng 4
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0104200001', '2020-04-01', 'Emp0004', 'P0104200001', 'Cust0002', 400000, 20.0);

-- Tháng 5
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0105200001', '2020-05-01', 'Emp0001', 'P0105200001', 'Cust0007', 500000, 25.0);

-- Tháng 6
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0106200001', '2020-06-01', 'Emp0003', 'P0106200001', 'Cust0001', 250000, 30.0);

-- Tháng 7
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0107200001', '2020-07-01', 'Emp0001', 'P0107200001', 'Cust0003', 450000, 35.0);

-- Tháng 8
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0108200001', '2020-08-01', 'Emp0002', 'P0108200001', 'Cust0006', 100000, 40.0);

-- Tháng 9
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0109200001', '2020-09-01', 'Emp0001', 'P0109200001', 'Cust0004', 150000, 45.0);

-- Tháng 10
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0110200001', '2020-10-01', 'Emp0002', 'P0110200001', 'Cust0001', 300000, 50.0);

-- Tháng 11
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0111200001', '2020-11-01', 'Emp0003', 'P0111200001', 'Cust0003', 200000, 55.0);

-- Tháng 12
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0112200001', '2020-12-01', 'Emp0003', 'P0112200001', 'Cust0002', 50000, 60.0);

----Năm 2021
-- Tháng 1
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0101210001', '2021-01-01', 'Emp0001', 'P0101210001', 'Cust0001', 120000, 5);

-- Tháng 2
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0102210001', '2021-02-01', 'Emp0002', 'P0102210001', 'Cust0001', 180000, 10);

-- Tháng 3
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0103210001', '2021-03-01', 'Emp0003', 'P0103210001', 'Cust0002', 340000, 15);

-- Tháng 4
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0104210001', '2021-04-01', 'Emp0004', 'P0104210001', 'Cust0003', 280000, 20);

-- Tháng 5
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0105210001', '2021-05-01', 'Emp0001', 'P0105210001', 'Cust0004', 470000, 25);

-- Tháng 6
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0106210001', '2021-06-01', 'Emp0002', 'P0106210001', 'Cust0005', 220000, 30);

-- Tháng 7
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0107210001', '2021-07-01', 'Emp0003', 'P0107210001', 'Cust0006', 150000, 35);

-- Tháng 8
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0108210001', '2021-08-01', 'Emp0002', 'P0108210001', 'Cust0007', 50000, 40);

-- Tháng 9
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0109210001', '2021-09-01', 'Emp0003', 'P0109210001', 'Cust0008', 390000, 45);

-- Tháng 10
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0110210001', '2021-10-01', 'Emp0004', 'P0110210001', 'Cust0009', 260000, 50);

-- Tháng 11
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0111210001', '2021-11-01', 'Emp0002', 'P0111210001', 'Cust0004', 340000, 55);

-- Tháng 12
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0112210001', '2021-12-01', 'Emp0003', 'P0112210001', 'Cust0001', 430000, 60);

----Năm 2022
-- Tháng 1
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0101220001', '2022-01-01', 'Emp0001', 'P0101220001', 'Cust0001', 120000, 5);

-- Tháng 2
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0102220001', '2022-02-01', 'Emp0002', 'P0102220001', 'Cust0002', 180000, 10);

-- Tháng 3
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0103220001', '2022-03-01', 'Emp0003', 'P0103220001', 'Cust0003', 340000, 15);

-- Tháng 4
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0104220001', '2022-04-01', 'Emp0004', 'P0104220001', 'Cust0004', 280000, 20);

-- Tháng 5
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0105220001', '2022-05-01', 'Emp0001', 'P0105220001', 'Cust0005', 470000, 25);

-- Tháng 6
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0106220001', '2022-06-01', 'Emp0002', 'P0106220001', 'Cust0006', 220000, 30);

-- Tháng 7
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0107220001', '2022-07-01', 'Emp0003', 'P0107220001', 'Cust0007', 150000, 35);

-- Tháng 8
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0108220001', '2022-08-01', 'Emp0004', 'P0108220001', 'Cust0008', 50000, 40);

-- Tháng 9
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0109220001', '2022-09-01', 'Emp0001', 'P0109220001', 'Cust0009', 390000, 45);

-- Tháng 10
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0110220001', '2022-10-01', 'Emp0002', 'P0110220001', 'Cust0010', 260000, 50);

-- Tháng 11
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0111220001', '2022-11-01', 'Emp0003', 'P0111220001', 'Cust0011', 340000, 55);

-- Tháng 12
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0112220001', '2022-12-01', 'Emp0004', 'P0112220001', 'Cust0012', 430000, 60);

----Năm 2023
-- Tháng 1
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0101230001', '2023-01-01', 'Emp0001', 'P0101230001', 'Cust0013', 180000, 10);

-- Tháng 2
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0102230001', '2023-02-01', 'Emp0002', 'P0102230001', 'Cust0014', 250000, 15);

-- Tháng 3
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0103230001', '2023-03-01', 'Emp0003', 'P0103230001', 'Cust0015', 320000, 20);

-- Tháng 4
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0104230001', '2023-04-01', 'Emp0004', 'P0104230001', 'Cust0016', 400000, 25);

-- Tháng 5
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0105230001', '2023-05-01', 'Emp0001', 'P0105230001', 'Cust0017', 500000, 30);

-- Tháng 6
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0106230001', '2023-06-01', 'Emp0002', 'P0106230001', 'Cust0001', 220000, 35);

-- Tháng 7
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0107230001', '2023-07-01', 'Emp0003', 'P0107230001', 'Cust0002', 100000, 40);

-- Tháng 8
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0108230001', '2023-08-01', 'Emp0004', 'P0108230001', 'Cust0003', 50000, 45);

-- Tháng 9
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0109230001', '2023-09-01', 'Emp0001', 'P0109230001', 'Cust0004', 390000, 50);

-- Tháng 10
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0110230001', '2023-10-01', 'Emp0002', 'P0110230001', 'Cust0005', 150000, 55);

-- Tháng 11
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0111230001', '2023-11-01', 'Emp0003', 'P0111230001', 'Cust0006', 340000, 60);

-- Tháng 12
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0112230001', '2023-12-01', 'Emp0004', 'P0112230001', 'Cust0007', 470000, 65);

----Năm 2024
-- Tháng 1
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0101240001', '2024-01-01', 'Emp0001', 'P0101240001', 'Cust0001', 300000, 10);

-- Tháng 2
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0102240001', '2024-02-01', 'Emp0002', 'P0102240001', 'Cust0002', 200000, 15);

-- Tháng 3
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0103240001', '2024-03-01', 'Emp0003', 'P0103240001', 'Cust0003', 150000, 20);

-- Tháng 4
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0104240001', '2024-04-01', 'Emp0004', 'P0104240001', 'Cust0004', 100000, 25);

-- Tháng 5
INSERT INTO [Order] ([OrderID], [OrderDate], [EmpID], [PaymentID], [CustomerID], [TotalDue], [Discount])
VALUES ('O0105240001', '2024-05-01', 'Emp0001', 'P0105240001', 'Cust0005', 250000, 30);
go

update [order]
set Discount = 0
go

----------------------------------------------------------------------OrderDetail------------------------------------------------------------------------------
----Năm 2020
-- Tháng 1
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0101200001', 'B001', 10000, 10, 'Cà phê đen');

-- Tháng 2
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0102200001', 'B002', 20000, 10, 'Trà xanh');

-- Tháng 3
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0103200001', 'B003', 30000, 10, 'Sinh tố dâu');

-- Tháng 4
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0104200001', 'B004', 40000, 10, 'Sinh tố xoài');

-- Tháng 5
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0105200001', 'B005', 50000, 10, 'Nước ép cam');

-- Tháng 6
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0106200001', 'B006', 50000, 5, 'Cà phê sữa');

-- Tháng 7
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0107200001', 'B007', 45000, 10, 'Nước ép táo');

-- Tháng 8
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0108200001', 'B008', 10000, 10, 'Cà phê đá');

-- Tháng 9
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0109200001', 'B009', 15000, 10, 'Trà sữa trân châu');

-- Tháng 10
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0110200001', 'B010', 30000, 10, 'Sinh tố xoài');

-- Tháng 11
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0111200001', 'B011', 20000, 10, 'Trà bạc hà');

-- Tháng 12
INSERT INTO [dbo].[OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0112200001', 'B012', 5000, 10, 'Cà phê hòa tan');

----Năm 2021
-- Tháng 1
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0101210001', 'B001', 12000, 10, 'Cà phê đen');

-- Tháng 2
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0102210001', 'B002', 18000, 10, 'Trà xanh');

-- Tháng 3
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0103210001', 'B003', 34000, 10, 'Sinh tố dâu');

-- Tháng 4
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0104210001', 'B004', 28000, 10, 'Sinh tố xoài');

-- Tháng 5
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0105210001', 'B005', 47000, 10, 'Nước ép cam');

-- Tháng 6
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0106210001', 'B006', 22000, 10, 'Cà phê sữa');

-- Tháng 7
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0107210001', 'B007', 15000, 10, 'Nước ép táo');

-- Tháng 8
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0108210001', 'B008', 5000, 10, 'Cà phê đá');

-- Tháng 9
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0109210001', 'B009', 39000, 10, 'Trà sữa trân châu');

-- Tháng 10
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0110210001', 'B010', 26000, 10, 'Sinh tố xoài');

-- Tháng 11
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0111210001', 'B011', 34000, 10, 'Trà bạc hà');

-- Tháng 12
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0112210001', 'B012', 43000, 10, 'Cà phê hòa tan');

--Năm 2022
-- Tháng 1
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0101220001', 'B001', 12000, 10, 'Cà phê đen');

-- Tháng 2
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0102220001', 'B002', 18000, 10, 'Trà xanh');

-- Tháng 3
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0103220001', 'B003', 34000, 10, 'Sinh tố dâu');

-- Tháng 4
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0104220001', 'B004', 28000, 10, 'Sinh tố xoài');

-- Tháng 5
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0105220001', 'B005', 47000, 10, 'Nước ép cam');

-- Tháng 6
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0106220001', 'B006', 22000, 10, 'Cà phê sữa');

-- Tháng 7
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0107220001', 'B007', 15000, 10, 'Nước ép táo');

-- Tháng 8
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0108220001', 'B008', 5000, 10, 'Cà phê đá');

-- Tháng 9
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0109220001', 'B009', 39000, 10, 'Trà sữa trân châu');

-- Tháng 10
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0110220001', 'B010', 26000, 10, 'Sinh tố xoài');

-- Tháng 11
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0111220001', 'B011', 34000, 10, 'Trà bạc hà');

-- Tháng 12
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0112220001', 'B012', 43000, 10, 'Cà phê hòa tan');

----Năm 2023
-- Tháng 1
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0101230001', 'B001', 18000, 10, 'Cà phê đen');

-- Tháng 2
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0102230001', 'B002', 25000, 10, 'Trà xanh');

-- Tháng 3
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0103230001', 'B003', 32000, 10, 'Sinh tố dâu');

-- Tháng 4
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0104230001', 'B004', 40000, 10, 'Sinh tố xoài');

-- Tháng 5
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0105230001', 'B005', 50000, 10, 'Nước ép cam');

-- Tháng 6
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0106230001', 'B006', 22000, 10, 'Cà phê sữa');

-- Tháng 7
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0107230001', 'B007', 10000, 10, 'Nước ép táo');

-- Tháng 8
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0108230001', 'B008', 5000, 10, 'Cà phê đá');

-- Tháng 9
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0109230001', 'B009', 39000, 10, 'Trà sữa trân châu');

-- Tháng 10
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0110230001', 'B010', 15000, 10, 'Sinh tố xoài');

-- Tháng 11
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0111230001', 'B011', 34000, 10, 'Trà bạc hà');

-- Tháng 12
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0112230001', 'B012', 47000, 10, 'Cà phê hòa tan');

----Năm 2024
-- Tháng 1
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0101240001', 'B001', 30000, 10, 'Cà phê đen');

-- Tháng 2
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0102240001', 'B002', 20000, 10, 'Trà xanh');

-- Tháng 3
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0103240001', 'B003', 15000, 10, 'Sinh tố dâu');

-- Tháng 4
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0104240001', 'B004', 10000, 10, 'Sinh tố xoài');

-- Tháng 5
INSERT INTO [OrderDetail] ([OrderID], [BeverageID], [UnitPrice], [OrderQty], [Description])
VALUES ('O0105240001', 'B005', 25000, 10, 'Nước ép cam');
go

update OrderDetail
set [Description] = '...'
