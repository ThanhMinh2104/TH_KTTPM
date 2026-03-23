const express = require('express');
const { Pool } = require('pg');

const app = express();
const PORT = 3000;

// Kết nối PostgreSQL
const pool = new Pool({
  host: process.env.DB_HOST || 'localhost',
  port: process.env.DB_PORT || 5432,
  database: process.env.DB_NAME || 'mydb',
  user: process.env.DB_USER || 'postgres',
  password: process.env.DB_PASSWORD || 'password',
});

app.use(express.json());

// Route mặc định
app.get('/', (req, res) => {
  res.json({
    message: '🐳 Docker Multi-Stage Demo đang chạy!',
    endpoints: {
      health: 'GET /health',
      users: 'GET /users',
      addUser: 'POST /users  { "name": "...", "email": "..." }',
    }
  });
});

// Health check
app.get('/health', async (req, res) => {
  try {
    await pool.query('SELECT 1');
    res.json({ status: 'OK', database: 'Connected ✅' });
  } catch (err) {
    res.status(500).json({ status: 'ERROR', database: err.message });
  }
});

// Lấy danh sách users
app.get('/users', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM users ORDER BY id');
    res.json(result.rows);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

// Thêm user mới
app.post('/users', async (req, res) => {
  const { name, email } = req.body;
  try {
    const result = await pool.query(
      'INSERT INTO users (name, email) VALUES ($1, $2) RETURNING *',
      [name, email]
    );
    res.status(201).json(result.rows[0]);
  } catch (err) {
    res.status(500).json({ error: err.message });
  }
});

app.listen(PORT, () => {
  console.log(`✅ Server đang chạy tại http://localhost:${PORT}`);
});
