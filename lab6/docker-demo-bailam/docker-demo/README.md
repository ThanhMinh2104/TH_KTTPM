# 🐳 Docker Multi-Stage Build Demo

## 📁 Cấu trúc project

```
docker-demo/
├── docker-compose.yml       # Orchestrate tất cả services
├── init.sql                 # Script khởi tạo database (tự động chạy)
└── app/
    ├── Dockerfile           # Multi-stage build
    ├── package.json
    └── index.js             # Node.js + Express API
```

---

## 🚀 Cách chạy

### Yêu cầu
- Docker Desktop đã cài và đang chạy
- Không cần cài Node.js hay PostgreSQL trên máy!

### Bước 1: Mở Terminal / PowerShell tại thư mục `docker-demo`

```bash
cd đường-dẫn-tới/docker-demo
```

### Bước 2: Build và chạy

```bash
docker compose up --build
```

> Lần đầu sẽ mất 2-3 phút để pull image và build. Lần sau nhanh hơn nhờ cache.

### Bước 3: Test API

Mở browser hoặc dùng `curl`:

```bash
# Trang chủ
curl http://localhost:3000

# Health check (kiểm tra database)
curl http://localhost:3000/health

# Lấy danh sách users (data đã được insert sẵn)
curl http://localhost:3000/users

# Thêm user mới
curl -X POST http://localhost:3000/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Nguyen Van D", "email": "vand@example.com"}'
```

### Bước 4: Dừng

```bash
docker compose down          # Dừng nhưng giữ data
docker compose down -v       # Dừng và xóa sạch data
```

---

## 🧠 Giải thích chi tiết

### Flow từ ghi chú của bạn:

```
pull image postgres --> run container --> insert data --> image (data ready)
```

| Bước | Trong project này |
|------|-------------------|
| pull image postgres | Docker tự pull `postgres:16-alpine` từ Docker Hub |
| run container | `docker compose up` tạo container `demo_postgres` |
| insert data | `init.sql` tự chạy khi container postgres khởi tạo lần đầu |
| image (data ready) | App kết nối được, dữ liệu users đã có sẵn |

---

### Multi-Stage Build hoạt động như thế nào?

```
Dockerfile có 2 STAGE:

┌─────────────────────────────────────┐
│  STAGE 1: "builder"  (~1.2GB)       │
│  FROM node:20                       │
│  - npm install (ALL deps)           │
│  - devDependencies: nodemon, ...    │
│  - Chỉ tồn tại lúc BUILD           │
│  ❌ KHÔNG có trong image cuối       │
└────────────────┬────────────────────┘
                 │ COPY --from=builder /app/index.js
                 ▼
┌─────────────────────────────────────┐
│  STAGE 2: "production"  (~150MB)    │
│  FROM node:20-alpine                │
│  - npm install --only=production    │
│  - Chỉ express + pg                │
│  ✅ Đây là image cuối cùng         │
└─────────────────────────────────────┘

Kết quả: Image nhỏ hơn 8x, bảo mật hơn, deploy nhanh hơn!
```

### Tại sao quan trọng?

- **Không cần cài môi trường**: Mỗi máy chạy giống nhau
- **Image nhỏ hơn**: Không có code/tool thừa trong production
- **Bảo mật hơn**: Attack surface nhỏ hơn (alpine thiếu nhiều tool không cần thiết)
- **CI/CD nhanh hơn**: Image nhỏ → push/pull nhanh hơn

---

## 🔍 Các lệnh Docker hữu ích

```bash
# Xem các container đang chạy
docker ps

# Xem logs của app
docker logs demo_app

# Vào trong container postgres
docker exec -it demo_postgres psql -U postgres -d mydb

# Xem kích thước image
docker images | grep demo
```
