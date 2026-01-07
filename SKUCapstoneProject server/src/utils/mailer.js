const nodemailer = require("nodemailer");

const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: process.env.GMAIL_USER,
    pass: process.env.GMAIL_PASS,
  },
});

async function sendVerifyMail(to, code) {
  await transporter.sendMail({
    from: `"BabyCare" <${process.env.GMAIL_USER}>`,
    to,
    subject: "이메일 인증 코드",
    text: `인증 코드: ${code}\n\n5분 이내에 입력해주세요.`,
  });
}

module.exports = { sendVerifyMail };
