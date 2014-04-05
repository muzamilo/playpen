-- CREATE DATABASE `bassat` DEFAULT CHARSET utf8;
-- grant usage on *.* to bassat@localhost identified by 'bassat';
-- grant all privileges on bassat.* to bassat@localhost;


USE bassat;

--
-- Table structure for table `import_statement`
--
DROP TABLE IF EXISTS `import_statement`;
CREATE TABLE IF NOT EXISTS `import_statement` (
  `import_statement_id` int(10) unsigned NOT NULL auto_increment,
  `import_datetime` datetime NOT NULL,
  `link_user_email` varchar(50) NOT NULL,
  `link_account_number` varchar(20) NULL,
  `pdf_file_checksum` char(40) NOT NULL,
  `pdf_file_data` blob NOT NULL,
  `status` numeric(1) NOT NULL,
  PRIMARY KEY  (`import_statement_id`),
  UNIQUE KEY `UNQ_USER_ACC_HASH` (`link_user_email`, `link_account_number`, `pdf_file_checksum`)
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

insert into user(user_id, id_number, title, first_name, surname, email, password) values (1, '7706035097083', 'Mr', 'Muzamil', 'Omar', 'muzamilo@gmail.com', 'Muzamil0');
commit;

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
  UNIQUE KEY `UNQ_USER_EMAIL` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into account(account_id, user_id, type, identifier) values (1, 1, 1, '071153322');
commit;
