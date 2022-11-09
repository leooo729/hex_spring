drop table if exists train;
drop table if exists train_stop;
drop table if exists train_ticket;
--schema.sql
CREATE TABLE `train` (
    `uuid` varchar(32) NOT NULL COMMENT 'PK',
    `train_no` int NOT NULL COMMENT '車次',
    `train_kind` varchar(1) NOT NULL COMMENT '車種（A:諾亞方舟號,B:霍格華茲號)',
    PRIMARY KEY (`uuid`)
  );

CREATE TABLE `train_stop` (
    `uuid` varchar(32) NOT NULL COMMENT 'PK',
    `train_uuid` varchar(32) NOT NULL COMMENT '車輛UUID',
    `seq` int NOT NULL COMMENT '停靠順序',
    `name` varchar(20) NOT NULL COMMENT '停靠站名',
    `time` time NOT NULL COMMENT '停靠時間',
    `delete_flag` varchar(1) NOT NULL COMMENT '是否刪除？',
    PRIMARY KEY (`uuid`)
  );

CREATE TABLE `train_ticket` (
    `id` INTEGER AUTO_INCREMENT COMMENT 'PK',
    `ticket_no` varchar(32) NOT NULL COMMENT '車票號碼',
    `train_uuid` varchar(32) NOT NULL COMMENT '車輛UUID',
    `from_stop` varchar(20) NOT NULL COMMENT '上車站名',
    `to_stop` varchar(20) NOT NULL COMMENT '下車站名',
    `take_date` date NOT NULL COMMENT '搭乘日期',
    `price` double NOT NULL COMMENT '票價',
    PRIMARY KEY (`id`)
  );

INSERT INTO `train` (`uuid`, `train_no`, `train_kind`) VALUES
  ('20470FDD9ABA4F76949B15504CB85904', 1002, 'B'),
  ('C4FFFEF9886742F49DBB012F95F3C931', 223, 'A');

INSERT INTO `train_stop` (`uuid`, `train_uuid`, `seq`, `name`, `time`, `delete_flag`) VALUES
  ('7FC5732ECF45423DAB5AD4E94019FA6A', '20470FDD9ABA4F76949B15504CB85904', 1, '松山', '05:28:00', 'N'),
  ('805CF25659914633BCF6329ED9EFBE6C', '20470FDD9ABA4F76949B15504CB85904', 2, '台北', '05:52:00', 'N'),
  ('7FEF1B366E854FF08CD60B1D2CB44ECC', '20470FDD9ABA4F76949B15504CB85904', 3, '萬華', '06:05:00', 'N'),
  ('8578980B58CC4137868C0DFA48255FC0', '20470FDD9ABA4F76949B15504CB85904', 4, '板橋', '06:41:00', 'N'),
  ('8581C57E53A044A1877E3EAF38A0418C', '20470FDD9ABA4F76949B15504CB85904', 5, '樹林', '07:15:00', 'N'),
  ('8590A703C8CE43AAA638292F111993AB', 'C4FFFEF9886742F49DBB012F95F3C931', 1, '台北', '10:00:00', 'N'),
  ('8599F61328764FF292D6630455F91D70', 'C4FFFEF9886742F49DBB012F95F3C931', 2, '台中', '12:05:00', 'N'),
  ('859BDB67755B473A9F11C28971644CC1', 'C4FFFEF9886742F49DBB012F95F3C931', 3, '高雄', '14:10:00', 'N');