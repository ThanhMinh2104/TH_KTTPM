# ============================================================
# STAGE 1: "builder" - Môi trường BUILD (chỉ cần lúc tạo image)
# ============================================================
# Dùng image đầy đủ để cài dependencies
FROM node:20 AS builder

WORKDIR /app

# Copy file khai báo dependencies
COPY package*.json ./

# Cài TẤT CẢ dependencies (kể cả devDependencies)
# nodemon, typescript compiler,... chỉ cần ở stage này
RUN npm install

# Copy source code
COPY . .

# (Nếu dùng TypeScript thì build ở đây: RUN npm run build)


# ============================================================
# STAGE 2: "production" - Image CUỐI CÙNG (chạy container)
# ============================================================
# Dùng image nhỏ hơn (alpine = chỉ ~5MB so với ~900MB của node:20)
FROM node:20-alpine AS production

WORKDIR /app

# Chỉ copy package.json để cài production deps
COPY package*.json ./

# Chỉ cài dependencies cần thiết để CHẠY (không có devDependencies)
RUN npm install --only=production

# Copy code từ stage builder (không copy node_modules thừa)
COPY --from=builder /app/index.js .

# Port ứng dụng lắng nghe
EXPOSE 3000

# Lệnh khởi động
CMD ["node", "index.js"]

# ============================================================
# KẾT QUẢ:
# - Stage builder:    ~1.2GB (node:20 + all deps)  ← BỊ XÓA
# - Stage production: ~150MB (node:20-alpine + prod deps only) ← GIỮ LẠI
# ============================================================
