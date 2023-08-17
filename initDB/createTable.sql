-- *********************************************
-- * Standard SQL generation                   
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Wed Aug  9 16:14:05 2023 
-- * LUN file: C:\Users\Tomas\Documents\Uni\2anno\2\BASI DB\PROGETTO\RistoDB.lun 
-- * Schema: RistoDB/SQL2 
-- ********************************************* 


-- Database Section
-- ________________ 

DROP DATABASE IF EXISTS RistoDB;

CREATE DATABASE IF NOT EXISTS RistoDB;

USE RistoDB;

-- DBSpace Section
-- _______________


-- Tables Section
-- _____________ 

create table Categories (
     name varchar(30) not null,
     constraint ID_Categories_ID primary key (name));

create table Orders (
     date date not null,
     time time not null,
     closing_time time,
     username varchar(20) not null,
     number int not null,
     constraint ID_Orders_ID primary key (date, time));

create table Orders_Details (
     product_ID int not null,
     date date not null,
     time time not null,
     quantity int not null,
     constraint ID_Orders_Details_ID primary key (product_ID, date, time));

create table Products (
     ID int auto_increment not null,
     name varchar(30) not null,
     price float(8) not null,
     Inc_name varchar(30) not null,
     constraint ID_Products_ID primary key (ID));

create table Receipts (
     ID int auto_increment not null,
     date date not null,
     number int not null,
     time time not null,
     constraint ID_Receipts_ID primary key (ID, date),
     constraint FKissue_ID unique (number));

create table Shifts_Assignment (
     username varchar(20) not null,
     date date not null,
     day_Moment char(10) not null,
     constraint SID_Shifts_Assignment_ID unique (username, date, day_Moment));

create table Tables (
     number int not null,
     max_people int not null,
     constraint ID_Tables_ID primary key (number));

create table Users (
     username varchar(20) unique not null,
     password varchar(20) not null,
     name char(20) not null,
     lastname char(20) not null,
     constraint ID_Users_ID primary key (username));

create table Workshifts (
     date date not null,
     day_Moment char(10) not null,
     start_time int not null,
     finish_time int not null,
     constraint ID_Workshifts_ID primary key (date, day_Moment));


-- Constraints Section
-- ___________________ 

alter table Orders add constraint FKwrite_FK
     foreign key (username)
     references Users(username);

alter table Orders add constraint FKmake_FK
     foreign key (number)
     references Tables(number);

alter table Orders_Details add constraint FKcontains_FK
     foreign key (date, time)
     references Orders(date, time);

alter table Orders_Details add constraint FKbelong
     foreign key (product_ID)
     references Products(ID);

alter table Products add constraint FKinclude_FK
     foreign key (Inc_name)
     references Categories(name);

alter table Receipts add constraint FKissue_FK
     foreign key (number)
     references Tables(number);

alter table Shifts_Assignment add constraint FKperform
     foreign key (username)
     references Users(username);

alter table Shifts_Assignment add constraint FKcomposition_FK
     foreign key (date, day_Moment)
     references Workshifts(date, day_Moment);


-- Index Section
-- _____________ 

create unique index ID_Categories_IND
     on Categories (name);

create unique index ID_Orders_IND
     on Orders (date, time);

create index FKwrite_IND
     on Orders (username);

create index FKmake_IND
     on Orders (number);

create unique index ID_Orders_Details_IND
     on Orders_Details (date, time);

create index FKcontains_IND
     on Orders_Details (date, time);

create unique index ID_Products_IND
     on Products (ID);

create index FKinclude_IND
     on Products (Inc_name);

create unique index ID_Receipts_IND
     on Receipts (ID, date);

create unique index FKissue_IND
     on Receipts (number);

create unique index SID_Shifts_Assignment_IND
     on Shifts_Assignment (username, date, day_Moment);

create index FKcomposition_IND
     on Shifts_Assignment (date, day_Moment);

create unique index ID_Tables_IND
     on Tables (number);

create unique index ID_Users_IND
     on Users (username);

create unique index ID_Workshifts_IND
     on Workshifts (date, day_Moment);

