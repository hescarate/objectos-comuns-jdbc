use COMUNS_RELATIONAL;

create table OTHER (

ID integer not null auto_increment, 
SIMPLE_ID integer not null,
`VALUE` varchar(5) not null unique,

primary key (ID)) ENGINE=InnoDB;
