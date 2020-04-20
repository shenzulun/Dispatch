
/*
 Navicat Premium Data Transfer

 Source Server         : Dispatch
 Source Server Type    : SQLite
 Source Server Version : 3017000
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3017000
 File Encoding         : 65001

 Date: 20/04/2020 09:14:55
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for T_DISPATCH_RECORD
-- ----------------------------
DROP TABLE IF EXISTS "T_DISPATCH_RECORD";
CREATE TABLE "T_DISPATCH_RECORD" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "trans_no" TEXT NOT NULL,
  "serno" TEXT,
  "source" TEXT,
  "target" TEXT,
  "version" TEXT,
  "code" TEXT,
  "message" TEXT,
  "request_msg" TEXT,
  "response_msg" TEXT,
  "create_time" TEXT NOT NULL DEFAULT (datetime('now','localtime')),
  "update_time" TEXT
);

-- ----------------------------
-- Auto increment value for T_DISPATCH_RECORD
-- ----------------------------
UPDATE "sqlite_sequence" SET seq = 5 WHERE name = 'T_DISPATCH_RECORD';

-- ----------------------------
-- Triggers structure for table T_DISPATCH_RECORD
-- ----------------------------
CREATE TRIGGER "TRI_MESSAGE_UPDATE"
BEFORE UPDATE
ON "T_DISPATCH_RECORD"
FOR EACH ROW
BEGIN
  update T_DISPATCH_RECORD set update_time=datetime('now','localtime') where id=old.id;
END;

PRAGMA foreign_keys = true;
