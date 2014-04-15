-- CREATE DATABASE `bassat` DEFAULT CHARSET utf8;
-- grant usage on *.* to bassat@localhost identified by 'bassat';
-- grant all privileges on bassat.* to bassat@localhost;


USE bassat;

--
-- Table structure for table `imported_statement`
--
DROP TABLE IF EXISTS `imported_statement`;
CREATE TABLE IF NOT EXISTS `imported_statement` (
  `imported_statement_id` int(10) unsigned NOT NULL auto_increment,
  `import_datetime` datetime NOT NULL,
  `link_user_id` int(10) NOT NULL,
  `link_account_identifier` varchar(20) NULL,
  `pdf_file_checksum` char(40) NOT NULL,
  `pdf_file_data` blob NOT NULL,
  `status` numeric(1) NOT NULL,
  PRIMARY KEY  (`imported_statement_id`),
  UNIQUE KEY `UNQ_USER_ACC_HASH` (`link_user_id`, `link_account_identifier`, `pdf_file_checksum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(10) unsigned NOT NULL auto_increment,
  `id_number` varchar(20) NOT NULL,
  `title` varchar(10) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `UNQ_USER_EMAIL` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `account`
--
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `account_id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) NOT NULL,
  `type` numeric(1) NOT NULL,
  `identifier` varchar(20) NOT NULL,
  PRIMARY KEY  (`account_id`),
  UNIQUE KEY `UNQ_ACC_IDENTIFIER` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `statement`
--
DROP TABLE IF EXISTS `statement`;
CREATE TABLE IF NOT EXISTS `statement` (
  `statement_id` int(10) unsigned NOT NULL auto_increment,
  `imported_statement_id` int(10) NOT NULL,
  `account_identifier` varchar(20) NOT NULL,
  `source_ref` varchar(20) NOT NULL,
  `frequency` varchar(10) NOT NULL,
  `from_date` datetime NOT NULL,
  `to_date` datetime NOT NULL,
  PRIMARY KEY  (`statement_id`),
  UNIQUE KEY `UNQ_SOURCE_REF` (`account_identifier`, `source_ref`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `transaction`
--
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE IF NOT EXISTS `transaction` (
  `transaction_id` int(10) unsigned NOT NULL auto_increment,
  `statement_id` int(10) NOT NULL,
  `type` numeric(1) NOT NULL,
  `tx_date` date NOT NULL,
  `description` varchar(64) NOT NULL,
  `tag` varchar(64) NOT NULL,
  `amount` decimal(13,2) NOT NULL,
  PRIMARY KEY  (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- SETUP TEST DATA
insert into user(user_id, id_number, title, first_name, surname, email, password) values (1, '7706035097083', 'Mr', 'Muzamil', 'Omar', 'muzamilo@gmail.com', 'Muzamil0');
insert into account(account_id, user_id, type, identifier) values (1, 1, 1, '071153322');
insert into account(account_id, user_id, type, identifier) values (2, 1, 2, '5520578441137929');
commit;

