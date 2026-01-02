require('dotenv').config();

const express = require('express');
const { connectDB } = require('./src/db'); 
const app = express();

const PORT = process.env.PORT || 3000;

// 미들웨어 설정
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// [라우터 연결]
const loginRouter = require('./src/routes/authRoutes'); 

// ❗ 아직 chat.js 파일이 없으므로, 에러 방지를 위해 아래 두 줄을 주석 처리했습니다.
// const chatRouter = require('./src/routes/chat'); 
// app.use('/chat', chatRouter); 

app.use('/login', loginRouter);

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