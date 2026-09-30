-- ============================================
-- Task 2 - MySQL
-- Retail Stock Management Database
-- ============================================

CREATE DATABASE IF NOT EXISTS retail_db;
USE retail_db;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    product_id   VARCHAR(20) PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    price        DECIMAL(10,2) NOT NULL,
    quantity     INT NOT NULL
);

-- ============================================
-- Insert at least 10 sample products
-- ============================================
INSERT INTO products (product_id, name, price, quantity) VALUES
('P001', 'Rice 5kg',        350.00, 25),
('P002', 'Wheat Flour 5kg', 220.00, 8),
('P003', 'Sugar 1kg',       45.00,  0),
('P004', 'Cooking Oil 1L',  180.00, 15),
('P005', 'Salt 1kg',        20.00,  50),
('P006', 'Tea Powder 250g', 90.00,  5),
('P007', 'Milk 1L',         55.00,  30),
('P008', 'Toothpaste',      60.00,  12),
('P009', 'Soap Bar',        35.00,  9),
('P010', 'Shampoo 200ml',   150.00, 20);

-- ============================================
-- CRUD Queries
-- ============================================

-- 1. ADD a new product
INSERT INTO products (product_id, name, price, quantity)
VALUES ('P011', 'Biscuits Pack', 30.00, 40);

-- 2. SEARCH a product by ID
SELECT * FROM products WHERE product_id = 'P001';

-- 3. SEARCH a product by name (partial match)
SELECT * FROM products WHERE name LIKE '%Rice%';

-- 4. UPDATE product quantity (e.g. after a purchase)
UPDATE products
SET quantity = quantity - 5
WHERE product_id = 'P001';

-- 5. UPDATE product price
UPDATE products
SET price = 200.00
WHERE product_id = 'P002';

-- 6. DELETE a product
DELETE FROM products WHERE product_id = 'P011';

-- 7. Display LOW STOCK products (quantity < 10, matches Task 1 rule)
SELECT * FROM products WHERE quantity < 10 AND quantity > 0;

-- 8. Display OUT OF STOCK products
SELECT * FROM products WHERE quantity = 0;

-- 9. Display ALL products
SELECT * FROM products;
