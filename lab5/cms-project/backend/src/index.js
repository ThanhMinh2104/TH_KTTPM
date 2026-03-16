const express = require('express');
const cors = require('cors');
const { v4: uuidv4 } = require('uuid');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

// In-memory data store (3 chức năng cơ bản nhất)
let posts = [
  {
    id: uuidv4(),
    title: 'Bài viết đầu tiên',
    content: 'Đây là nội dung bài viết đầu tiên của CMS.',
    author: 'Admin',
    createdAt: new Date().toISOString()
  },
  {
    id: uuidv4(),
    title: 'Hướng dẫn sử dụng CMS',
    content: 'CMS này được xây dựng với React + Node.js + Docker.',
    author: 'Admin',
    createdAt: new Date().toISOString()
  }
];

// Health check
app.get('/health', (req, res) => {
  res.json({ status: 'OK', message: 'CMS Backend is running!' });
});

// 1. GET tất cả bài viết
app.get('/api/posts', (req, res) => {
  res.json({ success: true, data: posts, total: posts.length });
});

// 2. GET một bài viết theo ID
app.get('/api/posts/:id', (req, res) => {
  const post = posts.find(p => p.id === req.params.id);
  if (!post) {
    return res.status(404).json({ success: false, message: 'Không tìm thấy bài viết' });
  }
  res.json({ success: true, data: post });
});

// 3. POST tạo bài viết mới
app.post('/api/posts', (req, res) => {
  const { title, content, author } = req.body;
  if (!title || !content) {
    return res.status(400).json({ success: false, message: 'Tiêu đề và nội dung không được để trống' });
  }
  const newPost = {
    id: uuidv4(),
    title,
    content,
    author: author || 'Ẩn danh',
    createdAt: new Date().toISOString()
  };
  posts.unshift(newPost);
  res.status(201).json({ success: true, data: newPost });
});

// 4. DELETE xóa bài viết
app.delete('/api/posts/:id', (req, res) => {
  const index = posts.findIndex(p => p.id === req.params.id);
  if (index === -1) {
    return res.status(404).json({ success: false, message: 'Không tìm thấy bài viết' });
  }
  posts.splice(index, 1);
  res.json({ success: true, message: 'Xóa bài viết thành công' });
});

app.listen(PORT, '0.0.0.0', () => {
  console.log(`✅ CMS Backend đang chạy tại http://0.0.0.0:${PORT}`);
});
