-- admin user inti pwd is admin (md5)
INSERT INTO webapp.users
(user_id, user_name, password, email)
VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'admin@dev.nul');

INSERT INTO webapp.user_roles
(user_id, role)
VALUES (1, 'ROLE_ADMIN');

