drop table if exists role;
drop table if exists user;

create table user (
	username varchar(20) not null,
	password varchar(128) not null,
	enabled boolean,
	constraint pk_user primary key (username)
);

create table role (
	username varchar(20) not null,
	role varchar(20) not null,
	constraint pk_role primary key(username),
	constraint fk_role_user_1 foreign key (username) references user(username)
);

insert into user values ('root', '$2a$10$NZ9oX2RQRHzhK/vONLhotuq2jNcLLCtalEjNLFL/7jU7u.r3I0gxa', true);
insert into user values ('bob', '$2a$10$.54I8/A/rTQzVa4bQgDoRuipJo92ZbMlwVKAsAOOzx0wO.NTOsD4e', true);

insert into role values ('root', 'admin');
insert into role values ('bob', 'guest');
