delete from computer;
delete from company;

alter table computer AUTO_INCREMENT = 1;
alter table company AUTO_INCREMENT = 1;

insert into company (id,name) values (1,'Apple Inc.');
insert into company (id,name) values (2,'Thinking Machines');
insert into company (id,name) values (3,'RCA');
insert into company (id,name) values (4,'Netronics');

insert into computer (id,name,introduced,discontinued,company_id) values (1,'MacBook Pro 15.4 inch',null,null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (2,'CM-2a',null,null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (5,'CM-5','1991-01-01',null,2);
insert into computer (id,name,introduced,discontinued,company_id) values (6,'MacBook Pro','2006-01-10',null,1);
insert into computer (id,name,introduced,discontinued,company_id) values (7,'Apple IIe',null,null,null);
insert into computer (id,name,introduced,discontinued,company_id) values (12,'Apple III','1980-05-01','1984-04-01',1);
insert into computer (id,name,introduced,discontinued,company_id) values (18,'COSMAC ELF',null,null,3);
insert into computer (id,name,introduced,discontinued,company_id) values (19,'COSMAC VIP','1977-01-01',null,3);
insert into computer (id,name,introduced,discontinued,company_id) values (20,'ELF II','1977-01-01',null,4);