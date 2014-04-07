-- the app is setup to gen the tables
-- below is based on those tables
-- tables for the webapp
drop table if exists webapp.users;

CREATE TABLE webapp.users (
  user_id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'surrogate key for table',
  user_name VARCHAR(32) NOT NULL COMMENT 'the login user name',
  password VARCHAR(32) NOT NULL COMMENT 'the login password',
  email VARCHAR(64) NOT NULL COMMENT 'user email address',
  enabled bit(1) NOT NULL DEFAULT 1 COMMENT 'whether the account is enabled',
  update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'the last time the record was updated',
  PRIMARY KEY (user_id),
  UNIQUE KEY enail_uc (email),
  UNIQUE KEY user_idx (user_name),
  UNIQUE KEY user_pwd_en_idx (user_name,password,enabled)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='this is the user data';

drop table if exists webapp.user_roles;

CREATE TABLE webapp.user_roles (
  user_role_id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'surrogate key for table',
  user_id INT(10) UNSIGNED NOT NULL COMMENT 'foreign key to user table',
  role ENUM('ROLE_ADMIN','ROLE_USER','ROLE_GUEST') NOT NULL COMMENT 'the assigned user role',
  PRIMARY KEY (user_role_id),
  KEY user_roles_fk (user_id),
  KEY role_user_idx (user_role_id, user_id),
  KEY user_fk (user_id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='this is the user role data';
