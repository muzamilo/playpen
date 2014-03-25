CREATE DATABASE `bassat` DEFAULT CHARSET utf8;
grant usage on *.* to bassat@localhost identified by 'bassat';
grant all privileges on bassat.* to bassat@localhost;

--
-- Table structure for table `import_statement`
--

DROP TABLE IF EXISTS `import_statement`;
CREATE TABLE IF NOT EXISTS `import_statement` (
  `import_statement_id` int(10) unsigned NOT NULL auto_increment,
  `import_datetime` datetime NOT NULL,
  `link_user_email` varchar(50) NOT NULL,
  `link_account_number` varchar(20) NULL,
  `md5` char(32) NOT NULL,
--  `pdf_binary` blob NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY  (`import_statement_id`),
  UNIQUE KEY `UNQ_USER_ACC_HASH` (`link_user_email`, `link_account_number`, `md5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


