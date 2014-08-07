SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `nuri` ;
CREATE SCHEMA IF NOT EXISTS `nuri` DEFAULT CHARACTER SET utf8 ;
USE `nuri` ;

-- -----------------------------------------------------
-- Table `nuri`.`user_bbs`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`user_bbs` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`user_bbs` (
  `bbs_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `bbs_group_code` INT(11) NULL DEFAULT NULL ,
  `bbs_code` INT(11) NULL DEFAULT NULL ,
  `title` VARCHAR(200) NULL DEFAULT NULL ,
  `contents` VARCHAR(5000) NULL DEFAULT NULL ,
  `user_sign` VARCHAR(200) NULL DEFAULT NULL ,
  `create_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_by_seq` INT(11) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`bbs_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`bbs_replies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`bbs_replies` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`bbs_replies` (
  `rep_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `precedency_seq` INT(11) NULL DEFAULT NULL ,
  `bbs_seq` INT(11) NOT NULL ,
  `bbs_group_code` INT(11) NULL DEFAULT NULL ,
  `bbs_code` INT(11) NULL DEFAULT NULL ,
  `contents` VARCHAR(1000) NULL DEFAULT NULL ,
  `create_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_by_seq` INT(11) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`rep_seq`) ,
  INDEX `fk_bbs_replies_user_bbs1_idx` (`bbs_seq` ASC) ,
  CONSTRAINT `fk_bbs_replies_user_bbs1`
    FOREIGN KEY (`bbs_seq` )
    REFERENCES `nuri`.`user_bbs` (`bbs_seq` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`comm_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`comm_code` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`comm_code` (
  `comm_code_seq` VARCHAR(12) NOT NULL ,
  `grp_name_kor` VARCHAR(45) NOT NULL ,
  `grp_name_eng` VARCHAR(45) NOT NULL ,
  `grp_description` VARCHAR(500) NOT NULL ,
  `create_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`comm_code_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`companies`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`companies` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`companies` (
  `company_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `company_name` VARCHAR(45) NULL DEFAULT NULL ,
  `company_ceo_name` VARCHAR(45) NULL DEFAULT NULL ,
  `company_biz_no` VARCHAR(45) NULL DEFAULT NULL ,
  `company_zipcode` VARCHAR(45) NULL DEFAULT NULL ,
  `company_address` VARCHAR(45) NULL DEFAULT NULL ,
  `company_tel` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`company_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`dtal_code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`dtal_code` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`dtal_code` (
  `code_seq` VARCHAR(12) NOT NULL ,
  `comm_code_seq` VARCHAR(12) NOT NULL ,
  `code_name_kor` VARCHAR(45) NULL DEFAULT NULL ,
  `code_name_eng` VARCHAR(45) NULL DEFAULT NULL ,
  `code_description` VARCHAR(45) NULL DEFAULT NULL ,
  `create_by` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_by` VARCHAR(45) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`code_seq`) ,
  INDEX `fk_dtal_code_comm_code1_idx` (`comm_code_seq` ASC) ,
  CONSTRAINT `fk_dtal_code_comm_code1`
    FOREIGN KEY (`comm_code_seq` )
    REFERENCES `nuri`.`comm_code` (`comm_code_seq` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '	';


-- -----------------------------------------------------
-- Table `nuri`.`attachment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`attachment` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`attachment` (
  `attach_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `attach_doc_type` VARCHAR(45) NOT NULL ,
  `attach_doc_key` VARCHAR(12) NOT NULL ,
  `filename` VARCHAR(45) NULL ,
  `fake_name` VARCHAR(45) NULL DEFAULT NULL ,
  `file_size` INT(11) NULL DEFAULT NULL ,
  `content_type` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`attach_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`login_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`login_history` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`login_history` (
  `his_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `user_id` VARCHAR(45) NULL DEFAULT NULL ,
  `user_seq` INT(11) NULL DEFAULT NULL ,
  `conn_ip` VARCHAR(45) NULL DEFAULT NULL ,
  `conn_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`his_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`notes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`notes` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`notes` (
  `note_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `precedency_seq` INT(11) NULL DEFAULT NULL COMMENT '쪽지에 대한 답장일 경우 원 쪽지의 seq' ,
  `send_user_id` VARCHAR(45) NULL DEFAULT NULL ,
  `send_user_seq` INT(11) NULL DEFAULT NULL ,
  `recv_user_id` VARCHAR(45) NULL DEFAULT NULL ,
  `recv_user_seq` INT(11) NULL DEFAULT NULL ,
  `recv_status` CHAR(1) NULL DEFAULT NULL COMMENT '0 : 미확인, 1 : 확인' ,
  `recv_date` DATETIME NULL DEFAULT NULL COMMENT '확인일자' ,
  `title` VARCHAR(200) NULL DEFAULT NULL ,
  `contents` VARCHAR(2000) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`note_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '쪽지';


-- -----------------------------------------------------
-- Table `nuri`.`notices`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`notices` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`notices` (
  `notice_seq` VARCHAR(12) NOT NULL ,
  `notice_type` CHAR(1) NULL DEFAULT '0' COMMENT '0:일반 9:긴급' ,
  `title` VARCHAR(200) NULL DEFAULT NULL ,
  `contents` VARCHAR(5000) NULL DEFAULT NULL ,
  `read_cnt` INT NULL DEFAULT 0 ,
  `create_by` VARCHAR(12) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_by` VARCHAR(12) NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`notice_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '공지사항';


-- -----------------------------------------------------
-- Table `nuri`.`roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`roles` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`roles` (
  `role_seq` VARCHAR(12) NOT NULL ,
  `role_code` VARCHAR(45) NOT NULL ,
  `role_name_kor` VARCHAR(45) NOT NULL ,
  `role_name_eng` VARCHAR(45) NULL DEFAULT NULL ,
  `description` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL COMMENT '삭제여부' ,
  PRIMARY KEY (`role_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '역할';


-- -----------------------------------------------------
-- Table `nuri`.`seq_table`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`seq_table` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`seq_table` (
  `seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `table_name` VARCHAR(45) NOT NULL COMMENT 'SEQ 관리할 테이블 명' ,
  `seq_name` VARCHAR(45) NOT NULL COMMENT '해당 테이블의 SEQ Field 명' ,
  `seq_prefix` VARCHAR(45) NOT NULL COMMENT 'Seq의 접두어 ex) USR, BBS 등' ,
  `steps` INT(11) NULL DEFAULT NULL COMMENT '증가시킬 값' ,
  `seq_value` INT(11) NULL DEFAULT NULL COMMENT '현재 테이블의 시퀀스 값' ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`seq`) ,
  UNIQUE INDEX `table_name_UNIQUE` (`table_name` ASC) ,
  UNIQUE INDEX `seq_name_UNIQUE` (`seq_name` ASC) ,
  UNIQUE INDEX `seq_prefix_UNIQUE` (`seq_prefix` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`user_authority`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`user_authority` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`user_authority` (
  `auth_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `user_id` VARCHAR(45) NULL DEFAULT NULL ,
  `send_code` VARCHAR(45) NULL DEFAULT NULL ,
  `return_code` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` VARCHAR(45) NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`auth_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '회원가입시 인증 이메일 이력';


-- -----------------------------------------------------
-- Table `nuri`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`users` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`users` (
  `user_seq` VARCHAR(12) NOT NULL ,
  `user_type` INT(1) NULL DEFAULT NULL COMMENT '0 : personal, 1 : Biz' ,
  `user_id` VARCHAR(80) NOT NULL COMMENT '전자우편 주소를 ID로 사용한다.' ,
  `passwd` VARCHAR(80) NOT NULL ,
  `user_name` VARCHAR(45) NOT NULL ,
  `nickname` VARCHAR(45) NOT NULL ,
  `contract1` VARCHAR(45) NULL DEFAULT NULL COMMENT '연락처1' ,
  `contract2` VARCHAR(45) NULL DEFAULT NULL COMMENT '연락처2' ,
  `authorize_code` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '인증코드' ,
  `authorize_date` DATETIME NULL DEFAULT NULL COMMENT '인증일' ,
  `has_notice` INT(1) NULL DEFAULT NULL COMMENT '[운영자 공지] 0 : 없음, 1 : 있음' ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  `update_date` DATETIME NULL DEFAULT NULL ,
  `disabled` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`user_seq`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`user_history`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`user_history` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`user_history` (
  `his_seq` INT(11) NOT NULL AUTO_INCREMENT ,
  `user_seq` VARCHAR(12) NOT NULL ,
  `new_nickname` VARCHAR(45) NULL DEFAULT NULL ,
  `old_nickname` VARCHAR(45) NULL DEFAULT NULL ,
  `create_date` DATETIME NULL DEFAULT NULL ,
  PRIMARY KEY (`his_seq`) ,
  INDEX `fk_user_history_users1_idx` (`user_seq` ASC) ,
  CONSTRAINT `fk_user_history_users1`
    FOREIGN KEY (`user_seq` )
    REFERENCES `nuri`.`users` (`user_seq` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '유저 별명 변경 이력';


-- -----------------------------------------------------
-- Table `nuri`.`user_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`user_roles` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`user_roles` (
  `seq` INT NOT NULL AUTO_INCREMENT ,
  `user_seq` VARCHAR(12) NOT NULL ,
  `role_seq` VARCHAR(12) NOT NULL ,
  INDEX `fk_users_has_roles_roles1_idx` (`role_seq` ASC) ,
  INDEX `fk_users_has_roles_users1_idx` (`user_seq` ASC) ,
  PRIMARY KEY (`seq`) ,
  CONSTRAINT `fk_users_has_roles_users1`
    FOREIGN KEY (`user_seq` )
    REFERENCES `nuri`.`users` (`user_seq` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_roles_roles1`
    FOREIGN KEY (`role_seq` )
    REFERENCES `nuri`.`roles` (`role_seq` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `nuri`.`menus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`menus` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`menus` (
  `menu_seq` VARCHAR(12) NOT NULL COMMENT '순번' ,
  `menu_id` VARCHAR(45) NOT NULL COMMENT '메뉴 고유 ID (자동)' ,
  `menu_func` VARCHAR(45) NOT NULL COMMENT '메뉴 접속시 실행할 함수명' ,
  `prt_menu_id` VARCHAR(45) NULL ,
  `menu_name` VARCHAR(45) NOT NULL ,
  `menu_cls` VARCHAR(45) NULL ,
  `menu_url` VARCHAR(100) NOT NULL ,
  `description` VARCHAR(200) NULL ,
  `ords` INT NULL ,
  `create_by` VARCHAR(45) NULL ,
  `create_date` DATETIME NULL ,
  `update_by` VARCHAR(45) NULL ,
  `update_date` DATETIME NULL ,
  `disabled` DATETIME NULL ,
  PRIMARY KEY (`menu_seq`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nuri`.`menus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`menus` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`menus` (
  `menu_seq` VARCHAR(12) NOT NULL COMMENT '순번' ,
  `menu_id` VARCHAR(45) NOT NULL COMMENT '메뉴 고유 ID (자동)' ,
  `menu_func` VARCHAR(45) NOT NULL COMMENT '메뉴 접속시 실행할 함수명' ,
  `prt_menu_id` VARCHAR(45) NULL ,
  `menu_name` VARCHAR(45) NOT NULL ,
  `menu_cls` VARCHAR(45) NULL ,
  `menu_url` VARCHAR(100) NOT NULL ,
  `description` VARCHAR(200) NULL ,
  `ords` INT NULL ,
  `create_by` VARCHAR(45) NULL ,
  `create_date` DATETIME NULL ,
  `update_by` VARCHAR(45) NULL ,
  `update_date` DATETIME NULL ,
  `disabled` DATETIME NULL ,
  PRIMARY KEY (`menu_seq`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `nuri`.`menu_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `nuri`.`menu_roles` ;

CREATE  TABLE IF NOT EXISTS `nuri`.`menu_roles` (
  `seq` INT NOT NULL AUTO_INCREMENT ,
  `menu_id` VARCHAR(45) NOT NULL ,
  `role_seq` VARCHAR(12) NOT NULL ,
  `create_by` VARCHAR(12) NULL ,
  `create_date` DATETIME NULL ,
  PRIMARY KEY (`seq`) )
ENGINE = InnoDB;

USE `nuri` ;

-- -----------------------------------------------------
-- function fn_generate_seq
-- -----------------------------------------------------

USE `nuri`;
DROP function IF EXISTS `nuri`.`fn_generate_seq`;

DELIMITER $$
USE `nuri`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_generate_seq`(tableName varchar(50)) RETURNS text CHARSET utf8
BEGIN
  RETURN (
		SELECT concat(rpad(seq_prefix, 12-LENGTH(seq_value + CAST(steps AS UNSIGNED)), 0), (seq_value + CAST(steps AS UNSIGNED)) ) as seq
/* concat(rpad(seq_prefix, 12-LENGTH(CAST(seq_value AS UNSIGNED) + CAST(steps AS UNSIGNED)), 0), (seq_value + CAST(steps AS UNSIGNED))) as seq
		*/
		FROM seq_table
		WHERE table_name = tableName 
		);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function fn_get_current_seq
-- -----------------------------------------------------

USE `nuri`;
DROP function IF EXISTS `nuri`.`fn_get_current_seq`;

DELIMITER $$
USE `nuri`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `fn_get_current_seq`(tableName varchar(50)) RETURNS text CHARSET utf8
BEGIN
  RETURN (
		SELECT concat(rpad(seq_prefix, 12-LENGTH(seq_value + CAST(steps AS UNSIGNED)), 0), seq_value) as seq
		/* SELECT concat(rpad(seq_prefix, 12-LENGTH(CAST(seq_value AS UNSIGNED) + CAST(steps AS UNSIGNED)), 0), seq_value) as seq */
		FROM seq_table
		WHERE table_name = tableName 
		);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function fn_generate_menu_id
-- -----------------------------------------------------

USE `nuri`;
DROP function IF EXISTS `nuri`.`fn_generate_menu_id`;

DELIMITER $$
USE `nuri`$$
CREATE FUNCTION `nuri`.`fn_generate_menu_id` (prtMenuId varchar(50)) RETURNS text CHARSET utf8
BEGIN
 RETURN (
	SELECT CONCAT( IF(STRCMP(prtMenuId, '#'), prtMenuId, ''), IFNULL(R.SEQ, '01')) AS SEQ
	FROM (
		SELECT IF(CAST(SUBSTR(MAX(menu_id), -2) AS SIGNED) + 1 < 10 , LPAD(CAST(SUBSTR(MAX(menu_id), -2) AS SIGNED) + 1, 2, 0), CAST(SUBSTR(MAX(menu_id), -2) AS SIGNED) + 1) AS SEQ
		  FROM nuri.menus M 
		 WHERE prt_menu_id = prtMenuId
		) R
	);
END$$

DELIMITER ;
USE `nuri`;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`comm_code_BINS` $$
USE `nuri`$$


CREATE
DEFINER=`root`@`localhost`
TRIGGER `nuri`.`comm_code_BINS`
BEFORE INSERT ON `nuri`.`comm_code`
FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'comm_code';
END$$


DELIMITER ;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`dtal_code_BINS` $$
USE `nuri`$$


CREATE
DEFINER=`root`@`localhost`
TRIGGER `nuri`.`dtal_code_BINS`
BEFORE INSERT ON `nuri`.`dtal_code`
FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'dtal_code';
END$$


DELIMITER ;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`notices_BINS` $$
USE `nuri`$$
CREATE TRIGGER `notices_BINS` BEFORE INSERT ON notices FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'notices';
END
$$


DELIMITER ;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`roles_BINS` $$
USE `nuri`$$


CREATE TRIGGER `roles_BINS` BEFORE INSERT ON roles FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'roles';
END$$


DELIMITER ;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`users_BINS` $$
USE `nuri`$$


CREATE TRIGGER `users_BINS` BEFORE INSERT ON users FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'users';
END$$


DELIMITER ;

DELIMITER $$

USE `nuri`$$
DROP TRIGGER IF EXISTS `nuri`.`menus_BINS` $$
USE `nuri`$$


CREATE TRIGGER `menus_BINS` BEFORE INSERT ON menus FOR EACH ROW
-- Edit trigger body code below this line. Do not edit lines above this one
BEGIN 
	UPDATE SEQ_TABLE SET SEQ_VALUE = SEQ_VALUE + CAST(STEPS AS UNSIGNED) WHERE TABLE_NAME = 'menus';
END
$$


DELIMITER ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `nuri`.`roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role00000010', 'ROLE_ANONYMOUS', '게스트', 'Guest', '손님', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role00000020', 'ROLE_MEMBER', '회원', 'User', '가온누리 활동 회원', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role00000030', 'ROLE_SPONSOR', '스폰서', 'Sponsor', '후원자', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role00000040', 'ROLE_ESTA', '기관', 'Establishment', '복지관 등', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role00000050', 'ROLE_CLERK', '서기', 'Clerk', '봉사 관련 정보를 입력하는 회원', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role10000000', 'ROLE_TESTER', '테스터', 'Tester', '테스트 권한', now(), NULL, NULL);
INSERT INTO `nuri`.`roles` (`role_seq`, `role_code`, `role_name_kor`, `role_name_eng`, `description`, `create_date`, `update_date`, `disabled`) VALUES ('role90000000', 'ROLE_ADMIN', '관리자', 'Admin', '관리자', now(), NULL, NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `nuri`.`seq_table`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`seq_table` (`seq`, `table_name`, `seq_name`, `seq_prefix`, `steps`, `seq_value`, `create_date`, `update_date`, `disabled`) VALUES (1, 'users', 'user_seq', 'USR', 10, 90, now(), NULL, NULL);
INSERT INTO `nuri`.`seq_table` (`seq`, `table_name`, `seq_name`, `seq_prefix`, `steps`, `seq_value`, `create_date`, `update_date`, `disabled`) VALUES (2, 'notices', 'notice_seq', 'NOTI', 10, 10, now(), NULL, NULL);
INSERT INTO `nuri`.`seq_table` (`seq`, `table_name`, `seq_name`, `seq_prefix`, `steps`, `seq_value`, `create_date`, `update_date`, `disabled`) VALUES (3, 'menus', 'menu_seq', 'MNU', 10, 180, now(), NULL, NULL);
INSERT INTO `nuri`.`seq_table` (`seq`, `table_name`, `seq_name`, `seq_prefix`, `steps`, `seq_value`, `create_date`, `update_date`, `disabled`) VALUES (4, 'roles', 'role_seq', 'role', 10, 50, now(), NULL, NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `nuri`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`users` (`user_seq`, `user_type`, `user_id`, `passwd`, `user_name`, `nickname`, `contract1`, `contract2`, `authorize_code`, `authorize_date`, `has_notice`, `create_date`, `update_date`, `disabled`) VALUES ('USR000000010', 0, 'test@yeory.com', 'fd53bdae384ef9d25605dac9128ed37f71c450737c6cde8e975a3642da10d5833a3ab01962b544df', '이재열', 'jYeory', NULL, NULL, NULL, NULL, NULL, now(), NULL, NULL);
INSERT INTO `nuri`.`users` (`user_seq`, `user_type`, `user_id`, `passwd`, `user_name`, `nickname`, `contract1`, `contract2`, `authorize_code`, `authorize_date`, `has_notice`, `create_date`, `update_date`, `disabled`) VALUES ('USR000000070', 0, 'test01@yeory.com', 'fd53bdae384ef9d25605dac9128ed37f71c450737c6cde8e975a3642da10d5833a3ab01962b544df', '일총무', '일총무', NULL, NULL, NULL, NULL, NULL, now(), NULL, NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `nuri`.`user_roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`user_roles` (`seq`, `user_seq`, `role_seq`) VALUES (1, 'USR000000010', 'role90000000');
INSERT INTO `nuri`.`user_roles` (`seq`, `user_seq`, `role_seq`) VALUES (2, 'USR000000010', 'role10000000');

COMMIT;

-- -----------------------------------------------------
-- Data for table `nuri`.`menus`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000010', '01', 'noticePage', '#', '공지사항', 'icon-edit', '/notice/noticeList.do', NULL, 2, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000050', '03', 'dashBoardPage', '#', 'Dashboard', 'icon-dashboard', '/gaon/dashboard.do', NULL, 1, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000130', '05', '#', '#', '관리자메뉴', 'fa fa-sitemap', '#', '관리자메뉴 ', 3, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000140', '0501', 'treeview', '05', '메뉴관리', 'icon-desktop', '/admin/menu/menuMngt.do', '메뉴관리', 2, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000150', '0502', 'roleMngtPage', '05', '권한관리', 'icon-key', '/admin/role/roleMngt.do', '권한관리', 3, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000160', '0503', 'test', '05', '코드관리', 'fa fa-gear', '/balnk.do', '코드관리', 1, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000170', '050301', 'test', '0503', '공통코드', 'fa fa-asterisk', '/blank.do', '공통코드', 1, 'USR000000010', now(), NULL, NULL, NULL);
INSERT INTO `nuri`.`menus` (`menu_seq`, `menu_id`, `menu_func`, `prt_menu_id`, `menu_name`, `menu_cls`, `menu_url`, `description`, `ords`, `create_by`, `create_date`, `update_by`, `update_date`, `disabled`) VALUES ('MNU000000180', '050302', 'test', '0503', '세부코드', 'fa fa-asterisk', '/blank.do', '세부코드', 2, 'USR000000010', now(), NULL, NULL, NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `nuri`.`menu_roles`
-- -----------------------------------------------------
START TRANSACTION;
USE `nuri`;
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (1, '01', 'role00000010', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (2, '01', 'role00000020', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (3, '01', 'role00000030', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (4, '01', 'role00000040', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (5, '01', 'role00000050', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (6, '01', 'role10000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (7, '03', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (8, '05', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (9, '0501', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (10, '0502', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (11, '0503', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (12, '050301', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (13, '050302', 'role90000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (14, '03', 'role00000010', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (15, '03', 'role00000020', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (16, '03', 'role00000030', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (17, '03', 'role00000040', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (18, '03', 'role00000050', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (19, '03', 'role10000000', 'USR000000010', NULL);
INSERT INTO `nuri`.`menu_roles` (`seq`, `menu_id`, `role_seq`, `create_by`, `create_date`) VALUES (20, '03', 'role90000000', 'USR000000010', NULL);

COMMIT;
