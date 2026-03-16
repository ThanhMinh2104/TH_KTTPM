# CMS Project - React + Node.js + Docker

## Kiến trúc
```
cms-project/
├── docker-compose.yml     ← File chính để chạy tất cả
├── backend/               ← Node.js + Express API
│   ├── Dockerfile
│   ├── package.json
│   └── src/index.js
└── frontend/              ← React App + Nginx
    ├── Dockerfile
    ├── package.json
    └── src/
        ├── index.js
        └── App.js
```

## 3 chức năng cơ bản
1. **Xem danh sách bài viết** - GET /api/posts
2. **Tạo bài viết mới** - POST /api/posts
3. **Xóa bài viết** - DELETE /api/posts/:id

## Cách chạy

### Yêu cầu: Cài Docker Desktop trước
- Download: https://www.docker.com/products/docker-desktop/

### Chạy project (1 lệnh duy nhất)
```bash
# Vào thư mục project
cd cms-project

# Build và chạy tất cả
docker compose up --build

# Lần sau không cần --build nếu code không đổi
docker compose up
```

### Truy cập
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:5000
- **Health check**: http://localhost:5000/health

### Dừng project
```bash
docker compose down
```

## API Endpoints
| Method | URL | Mô tả |
|--------|-----|-------|
| GET | /health | Kiểm tra server |
| GET | /api/posts | Lấy tất cả bài viết |
| GET | /api/posts/:id | Lấy 1 bài viết |
| POST | /api/posts | Tạo bài viết mới |
| DELETE | /api/posts/:id | Xóa bài viết |
