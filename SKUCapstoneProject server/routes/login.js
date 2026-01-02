// routes/user.js
const express = require('express');
const router = express.Router();
const db = require('../db'); // Firestore 연결 객체

router.post('/signup', async (req, res) => {
    try {
        // req.body : 클라이언트(앱)가 보내준 JSON 데이터가 여기 들어있습니다.
        const { id, password, name } = req.body;

        // 필수 데이터가 왔는지 체크
        if (!id || !password || !name) {
            return res.status(400).json({ message: "모든 정보를 입력해주세요." });
        }

        // 이미 있는 아이디인지 확인 (중복 가입 방지)
        const userRef = db.collection('users').doc(id);
        const doc = await userRef.get();

        if (doc.exists) {
            return res.status(409).json({ message: "이미 존재하는 아이디입니다." });
        }

        // DB에 저장
        // .set()은 문서를 새로 만들거나 덮어씁니다.
        // doc(id)를 쓰면 우리가 원하는 ID를 문서 이름으로 지정할 수 있습니다.
        await userRef.set({
            password: password, // (실무에선 암호화 필수, 지금은 기능 구현 우선)
            name: name,
            createdAt: new Date().toISOString()
        });

        res.status(200).json({ message: "회원가입 성공!", userId: id });

    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "서버 에러 발생" });
    }
});

router.post('/login', async (req, res) => {
    try {
        const { id, password } = req.body;

        const userRef = db.collection('users').doc(id);
        const doc = await userRef.get();

        // 1단계: 아이디가 DB에 없는 경우
        if (!doc.exists) {
            return res.status(404).json({ message: "존재하지 않는 아이디입니다." });
        }

        // 2단계: 비밀번호 비교
        const userData = doc.data();
        if (userData.password !== password) {
            return res.status(401).json({ message: "비밀번호가 틀렸습니다." });
        }

        // 로그인 성공
        res.status(200).json({
            message: "로그인 성공!",
            name: userData.name // 로그인 후 환영 인사를 위해 이름도 보내줌
        });

    } catch (error) {
        console.error(error);
        res.status(500).json({ message: "서버 에러 발생" });
    }
});

module.exports = router;