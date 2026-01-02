require('dotenv').config();

const express = require('express');
const app = express();

const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const loginRouter = require('./routes/login'); // 로그인라우터
const chatRouter = require('./routes/chat'); // 지피터 라우터

app.use('/login', loginRouter);
app.use('/chat', chatRouter); 

// 기본 접속 테스트
app.get('/', (req, res) => {
    res.send('Hello! Capstone Server is Running 🚀');
});

// 서버 시작
app.listen(PORT, () => {
    console.log(`---------------------------------------`);
    console.log(`🚀 서버가 http://localhost:${PORT} 에서 대기 중입니다.`);
    console.log(`---------------------------------------`);
});
