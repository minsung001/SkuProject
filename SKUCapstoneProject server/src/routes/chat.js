// src/routes/chat.js
const express = require('express');
const router = express.Router();

// 임시 경로 (서버 구동 확인용)
router.get('/', (req, res) => {
    res.json({ ok: true, message: "Chat router is ready!" });
});

module.exports = router;