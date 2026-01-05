require('dotenv').config();

const express = require('express');
const { connectDB } = require('./src/db'); 
const app = express();

const PORT = process.env.PORT || 3000;

// 미들웨어 설정
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// [라우터 연결]
const authRouter = require('./src/routes/authRoutes'); // 변수명을 용도에 맞게 authRouter로 변경했습니다.

// ❗ 안드로이드 ApiService의 @POST("/auth/...") 경로와 일치하도록 수정
// 기존 '/login'을 '/auth'로 변경하였습니다.
app.use('/auth', authRouter);

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
            console.log(`---------------------------------------`);
        });
    })
    .catch((err) => {
        console.error("❌ 서버 시작 실패 (DB 연결 오류):", err.message);
        process.exit(1);
    });