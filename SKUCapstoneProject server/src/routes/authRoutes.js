const express = require("express");
const router = express.Router();
const authCtrl = require("../controllers/authController");

router.post("/request-verify", authCtrl.requestVerify);
router.post("/verify", authCtrl.verifyCode);
router.post("/signup", authCtrl.signup);
router.post("/login", authCtrl.login);
router.post("/refresh", authCtrl.refresh);

module.exports = router;