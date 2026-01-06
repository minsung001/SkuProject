require('dotenv').config();

const express = require('express');
const { connectDB } = require('./src/db'); 
const app = express();

const PORT = process.env.PORT || 3000;

// 미들웨어 설정
app.use(express.json());
app.use((req, res, next) => {
    console.log(`[${new Date().toLocaleString()}] 📢 요청 들어옴: ${req.method} ${req.url}`);
    next();
});
app.use(express.urlencoded({ extended: true }));

// [라우터 연결]
const authRouter = require('./src/routes/authRoutes');
const policyRouter = require('./src/routes/policyRoutes'); // 1. Policy 라우터 추가

// 기존 인증 라우터
app.use('/auth', authRouter);

// 2. 복지 정책 라우터 추가 (안드로이드에서 /api/policies 로 요청)
app.use('/api/policies', policyRouter);

// 기본 접속 테스트
app.get('/', (req, res) => {
    res.send('Hello! Capstone Server is Running 🚀');
});

// DB 연결 후 서버 시작
connectDB()
    .then(() => {
        app.listen(PORT, () => {
            console.log(`---------------------------------------`);
            console.log(`🚀 서버가 http://localhost:${PORT} 에서 대기 중입니다.`);
            console.log(`🚀 데이터베이스 연결 성공`);
            console.log(`🚀 정책 API: http://localhost:${PORT}/api/policies`); // 확인용 로그 추가
            console.log(`---------------------------------------`);
        });
    })
    .catch((err) => {
        console.error("❌ 서버 시작 실패 (DB 연결 오류):", err.message);
        process.exit(1);
    });