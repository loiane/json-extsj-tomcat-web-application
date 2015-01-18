-- the app is setup to gen the tables
-- below is based on those tables
-- tables for the webapp
drop table if exists webapp.users;;

CREATE TABLE webapp.users (
  user_id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'surrogate key for table',
  user_name VARCHAR(32) NOT NULL COMMENT 'the login user name',
  password VARCHAR(32) NOT NULL COMMENT 'the login password encrypted ',
  email VARCHAR(64) NOT NULL COMMENT 'user email address',
  enabled bit(1) NOT NULL DEFAULT 1 COMMENT 'whether the account is enabled',
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'the last time the record was updated',
  PRIMARY KEY (user_id),
  UNIQUE KEY enail_uc (email),
  UNIQUE KEY user_idx (user_name),
  UNIQUE KEY user_pwd_en_idx (user_name,password,enabled)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='this is the user data';;

drop table if exists webapp.user_roles;;

CREATE TABLE webapp.user_role (
  user_role_id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'surrogate key for table',
  user_id INT(10) UNSIGNED NOT NULL COMMENT 'foreign key to user table',
  role ENUM('ROLE_ADMIN','ROLE_USER','ROLE_GUEST') NOT NULL COMMENT 'the assigned user role',
  PRIMARY KEY (user_role_id),
  KEY user_roles_fk (user_id),
  KEY role_user_idx (user_role_id, user_id),
  KEY user_fk (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='this is the user role data';;

--
-- DATA
--

-- admin user inti pwd is admin (md5)
INSERT INTO webapp.users
(user_id, user_name, password, email)
VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@dev.nul');;

INSERT INTO webapp.user_role
(user_id, role)
VALUES (1, 'ROLE_ADMIN');;

-- guest user inti pwd is guest (md5)
INSERT INTO webapp.users
(user_id, user_name, password, email)
VALUES
(2, 'guest', '084e0343a0486ff05530df6c705c8bb4', 'guest@dev.nul');;

INSERT INTO webapp.user_role
(user_id, role)
VALUES (2, 'ROLE_GUEST');;

-- user user inti pwd is user (md5)
INSERT INTO webapp.users
(user_id, user_name, password, email)
VALUES
(3, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'user@dev.nul');;

INSERT INTO webapp.user_role
(user_id, role)
VALUES (3, 'ROLE_USER');;
