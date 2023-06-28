create sequence hibernate_sequence start with 9 increment by 1;

create table product (
id bigint not null primary key,
name varchar(128) not null,
cost varchar(10) not null,
created_by varchar(30),
created_on Date,
modified_by varchar(30),
modified_on Date,
is_deleted varchar(1)

);

create table toppings (
id bigint not null primary key,
name varchar(128) not null,
cost varchar(10) not null,
created_by varchar(30),
created_on Date,
modified_by varchar(30),
modified_on Date,
is_deleted varchar(1),
used_count int

);