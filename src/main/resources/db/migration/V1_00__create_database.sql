use OBJ_COMUNS_RELATIONAL;

create table SIMPLE (
ID integer not null auto_increment, 
STRING varchar(5) not null unique, 
primary key (ID)) ENGINE=InnoDB;
