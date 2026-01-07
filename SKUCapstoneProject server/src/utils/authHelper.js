const Guardian = require("../models/Guardian");

// IP 및 기기정보 추출 유틸
exports.getClientInfo = (req) => {
  const ip = (req.headers["x-forwarded-for"]?.toString().split(",")[0] || "").trim() || req.socket?.remoteAddress || "";
  const userAgent = req.headers["user-agent"] || "";
  return { ip, userAgent };
};

// 사용자의 권한 그룹(OwnerId) 확인 유틸
exports.resolveOwnerIdForUser = async (userId) => {
  const owner = await Guardian.findOne({ memberId: userId, role: "OWNER" });
  if (owner) return owner.ownerId;

  const sub = await Guardian.findOne({ memberId: userId, role: "SUB" });
  if (sub) return sub.ownerId;

  return userId;
};