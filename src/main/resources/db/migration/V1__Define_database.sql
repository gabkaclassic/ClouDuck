drop table if exists users cascade;
create table users (key varchar(255) not null, expire date, root_folder varchar(255), primary key (key));