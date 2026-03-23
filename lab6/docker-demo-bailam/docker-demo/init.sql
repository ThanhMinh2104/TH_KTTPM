-- File này tự động chạy khi PostgreSQL container khởi tạo lần đầu
-- Đây là bước "insert data" trong flow: pull image --> run container --> insert data --> image (data ready)

-- Tạo bảng users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Seed dữ liệu mẫu (data ready!)
INSERT INTO users (name, email) VALUES
    ('Nguyen Van A', 'vana@example.com'),
    ('Tran Thi B',   'thib@example.com'),
    ('Le Van C',     'vanc@example.com');

-- Log để biết đã chạy thành công
DO $$
BEGIN
    RAISE NOTICE '✅ Database initialized! Inserted 3 sample users.';
END $$;
