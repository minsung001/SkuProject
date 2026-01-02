const admin = require('firebase-admin');
const serviceAccount = require('./dbKey.json'); 

if (!admin.apps.length) {
    admin.initializeApp({
        credential: admin.credential.cert(serviceAccount)
    });
    console.log("🔥 Firebase Admin SDK가 성공적으로 연결되었습니다!");
}

const db = admin.firestore();

module.exports = db;