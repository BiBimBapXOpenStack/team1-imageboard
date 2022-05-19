insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

--- user1 password : 111
--- user2 password : 222
insert into member (email, password, username) values ('aaa@naver.com', '$2a$10$uyiKz1qCjp81BqQi0Bou5uuTXSZSS2VWuhVc90cO.VkSMSpxYTAly', 'user1');
insert into member (email, password, username) values ('bbb@naver.com', '$2a$10$rQZNph2sGAwFM/NN9YN9beRln0.Wi83W9ESXH2GnUzW03dfQgtu8e', 'user2');


insert into member_authority (member_id, authority_name) values (1, 'ROLE_USER');
insert into member_authority (member_id, authority_name) values (1, 'ROLE_ADMIN');
insert into member_authority (member_id, authority_name) values (2, 'ROLE_USER');