drop table if exists role;
drop table if exists user;

create table user (
	id int not null auto_increment,
	username varchar(20) not null,
	password varchar(64) not null,
	constraint pk_user primary key (id)
);

create table role (
	id int not null auto_increment,
	user_id int not null,
	role varchar(20) not null,
	constraint pk_role primary key(id)
);

alter table role add constraint fk_role_user_1 foreign key (user_id) references user(id) on delete restrict on update restrict;

insert into user (username, password) values ('root', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
insert into user (username, password) values ('bob', 'f2d81a260dea8a100dd517984e53c56a7523d96942a834b9cdc249bd4e8c7aa9');

insert into role (user_id, role) values (1, 'admin');
insert into role (user_id, role) values (2, 'guest');
