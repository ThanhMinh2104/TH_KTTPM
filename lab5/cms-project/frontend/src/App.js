import React, { useState, useEffect } from 'react';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:5000';

const styles = {
  root: {
    fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    minHeight: '100vh',
    background: '#0f172a',
    color: '#e2e8f0',
    margin: 0,
    padding: 0,
  },
  header: {
    background: 'linear-gradient(135deg, #1e3a5f 0%, #0f172a 100%)',
    borderBottom: '1px solid #334155',
    padding: '20px 40px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  headerTitle: {
    fontSize: '24px',
    fontWeight: '700',
    color: '#38bdf8',
    margin: 0,
    letterSpacing: '1px',
  },
  headerSub: {
    fontSize: '13px',
    color: '#64748b',
    marginTop: '4px',
  },
  statusBadge: {
    background: '#052e16',
    color: '#4ade80',
    border: '1px solid #166534',
    borderRadius: '20px',
    padding: '6px 14px',
    fontSize: '12px',
    fontWeight: '600',
  },
  main: {
    maxWidth: '900px',
    margin: '0 auto',
    padding: '40px 20px',
  },
  card: {
    background: '#1e293b',
    border: '1px solid #334155',
    borderRadius: '12px',
    padding: '28px',
    marginBottom: '24px',
  },
  cardTitle: {
    fontSize: '16px',
    fontWeight: '700',
    color: '#38bdf8',
    marginBottom: '20px',
    display: 'flex',
    alignItems: 'center',
    gap: '8px',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '14px',
  },
  input: {
    background: '#0f172a',
    border: '1px solid #334155',
    borderRadius: '8px',
    padding: '12px 14px',
    color: '#e2e8f0',
    fontSize: '14px',
    outline: 'none',
    transition: 'border-color 0.2s',
    width: '100%',
    boxSizing: 'border-box',
  },
  textarea: {
    background: '#0f172a',
    border: '1px solid #334155',
    borderRadius: '8px',
    padding: '12px 14px',
    color: '#e2e8f0',
    fontSize: '14px',
    outline: 'none',
    resize: 'vertical',
    minHeight: '100px',
    width: '100%',
    boxSizing: 'border-box',
    fontFamily: 'inherit',
  },
  btnPrimary: {
    background: 'linear-gradient(135deg, #0284c7, #0369a1)',
    color: '#fff',
    border: 'none',
    borderRadius: '8px',
    padding: '12px 24px',
    fontSize: '14px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'opacity 0.2s',
    alignSelf: 'flex-start',
  },
  btnDanger: {
    background: 'transparent',
    color: '#f87171',
    border: '1px solid #f87171',
    borderRadius: '6px',
    padding: '6px 12px',
    fontSize: '12px',
    cursor: 'pointer',
    transition: 'all 0.2s',
  },
  postItem: {
    background: '#0f172a',
    border: '1px solid #1e3a5f',
    borderRadius: '10px',
    padding: '20px',
    marginBottom: '14px',
    transition: 'border-color 0.2s',
  },
  postHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: '10px',
  },
  postTitle: {
    fontSize: '16px',
    fontWeight: '600',
    color: '#bae6fd',
    margin: 0,
  },
  postContent: {
    color: '#94a3b8',
    fontSize: '14px',
    lineHeight: '1.6',
    margin: '8px 0',
  },
  postMeta: {
    fontSize: '12px',
    color: '#475569',
    display: 'flex',
    gap: '16px',
    marginTop: '10px',
  },
  alert: {
    padding: '12px 16px',
    borderRadius: '8px',
    marginBottom: '16px',
    fontSize: '13px',
    fontWeight: '500',
  },
  alertSuccess: {
    background: '#052e16',
    color: '#4ade80',
    border: '1px solid #166534',
  },
  alertError: {
    background: '#450a0a',
    color: '#f87171',
    border: '1px solid #7f1d1d',
  },
  emptyState: {
    textAlign: 'center',
    color: '#475569',
    padding: '40px 0',
    fontSize: '15px',
  },
  loader: {
    textAlign: 'center',
    color: '#38bdf8',
    padding: '30px',
    fontSize: '14px',
  },
};

export default function App() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [alert, setAlert] = useState(null);
  const [backendStatus, setBackendStatus] = useState('Đang kiểm tra...');
  const [form, setForm] = useState({ title: '', content: '', author: '' });
  const [submitting, setSubmitting] = useState(false);

  const showAlert = (msg, type = 'success') => {
    setAlert({ msg, type });
    setTimeout(() => setAlert(null), 3000);
  };

  // Kiểm tra backend health
  useEffect(() => {
    fetch(`${API_URL}/health`)
      .then(r => r.json())
      .then(() => setBackendStatus('Backend OK ✓'))
      .catch(() => setBackendStatus('Backend lỗi ✗'));
  }, []);

  // 1. Lấy danh sách bài viết
  const fetchPosts = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API_URL}/api/posts`);
      const data = await res.json();
      if (data.success) setPosts(data.data);
    } catch {
      showAlert('Không thể kết nối backend!', 'error');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchPosts(); }, []);

  // 2. Tạo bài viết mới
  const handleCreate = async (e) => {
    e.preventDefault();
    if (!form.title.trim() || !form.content.trim()) {
      return showAlert('Vui lòng điền tiêu đề và nội dung!', 'error');
    }
    setSubmitting(true);
    try {
      const res = await fetch(`${API_URL}/api/posts`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form),
      });
      const data = await res.json();
      if (data.success) {
        setPosts(prev => [data.data, ...prev]);
        setForm({ title: '', content: '', author: '' });
        showAlert('Tạo bài viết thành công!');
      }
    } catch {
      showAlert('Lỗi khi tạo bài viết!', 'error');
    } finally {
      setSubmitting(false);
    }
  };

  // 3. Xóa bài viết
  const handleDelete = async (id) => {
    if (!window.confirm('Bạn chắc muốn xóa bài viết này?')) return;
    try {
      const res = await fetch(`${API_URL}/api/posts/${id}`, { method: 'DELETE' });
      const data = await res.json();
      if (data.success) {
        setPosts(prev => prev.filter(p => p.id !== id));
        showAlert('Xóa bài viết thành công!');
      }
    } catch {
      showAlert('Lỗi khi xóa bài viết!', 'error');
    }
  };

  const formatDate = (iso) => new Date(iso).toLocaleString('vi-VN');

  return (
    <div style={styles.root}>
      <div style={styles.header}>
        <div>
          <h1 style={styles.headerTitle}>⚡ CMS Dashboard</h1>
          <div style={styles.headerSub}>Kiến trúc: React + Node.js + Docker</div>
        </div>
        <span style={styles.statusBadge}>{backendStatus}</span>
      </div>

      <div style={styles.main}>
        {alert && (
          <div style={{ ...styles.alert, ...(alert.type === 'error' ? styles.alertError : styles.alertSuccess) }}>
            {alert.msg}
          </div>
        )}

        {/* Form tạo bài viết */}
        <div style={styles.card}>
          <div style={styles.cardTitle}>✏️ Tạo bài viết mới</div>
          <form style={styles.form} onSubmit={handleCreate}>
            <input
              style={styles.input}
              placeholder="Tiêu đề bài viết *"
              value={form.title}
              onChange={e => setForm(f => ({ ...f, title: e.target.value }))}
            />
            <textarea
              style={styles.textarea}
              placeholder="Nội dung bài viết *"
              value={form.content}
              onChange={e => setForm(f => ({ ...f, content: e.target.value }))}
            />
            <input
              style={styles.input}
              placeholder="Tác giả (tùy chọn)"
              value={form.author}
              onChange={e => setForm(f => ({ ...f, author: e.target.value }))}
            />
            <button type="submit" style={styles.btnPrimary} disabled={submitting}>
              {submitting ? 'Đang đăng...' : '+ Đăng bài viết'}
            </button>
          </form>
        </div>

        {/* Danh sách bài viết */}
        <div style={styles.card}>
          <div style={styles.cardTitle}>📄 Danh sách bài viết ({posts.length})</div>
          {loading ? (
            <div style={styles.loader}>Đang tải...</div>
          ) : posts.length === 0 ? (
            <div style={styles.emptyState}>Chưa có bài viết nào. Hãy tạo bài đầu tiên!</div>
          ) : (
            posts.map(post => (
              <div key={post.id} style={styles.postItem}>
                <div style={styles.postHeader}>
                  <h3 style={styles.postTitle}>{post.title}</h3>
                  <button style={styles.btnDanger} onClick={() => handleDelete(post.id)}>
                    🗑 Xóa
                  </button>
                </div>
                <p style={styles.postContent}>{post.content}</p>
                <div style={styles.postMeta}>
                  <span>👤 {post.author}</span>
                  <span>🕐 {formatDate(post.createdAt)}</span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
}
