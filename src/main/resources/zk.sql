create database zk default character set utf8;
use zk;


-- table alarm_settings
drop table is exists alarm_settings;
create table alarm_settings (
	id int primary key auto_increment,
	clusterId int not null,
	
	
);