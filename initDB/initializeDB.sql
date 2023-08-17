USE RistoDB;
INSERT INTO Users(username, password, name, lastname)
VALUES ('tomas', 'tomas','tomas','ventrucci');

INSERT INTO Tables(number, max_people)
VALUES (1, 2),
(2, 2),
(3, 4),
(4, 4),
(5, 6),
(6, 2),
(7, 7),
(8, 3),
(9, 2),
(10, 5),
(11, 6);

INSERT INTO categories 
VALUES("Servizi");

INSERT INTO products
(name, price, Inc_name)
VALUES("coperto",2,"Servizi");

INSERT INTO Workshifts(date, day_Moment, start_time, finish_time)
VALUES
('2023-08-01','Pranzo', 10, 15),
('2023-08-01','Cena', 18, 24),
('2023-08-02','Pranzo', 10, 15),
('2023-08-02','Cena', 18, 24),
('2023-08-03','Pranzo', 10, 15),
('2023-08-03','Cena', 18, 24),
('2023-08-04','Pranzo', 10, 15),
('2023-08-04','Cena', 18, 24),
('2023-08-05','Pranzo', 10, 15),
('2023-08-05','Cena', 18, 24),
('2023-08-06','Pranzo', 10, 15),
('2023-08-06','Cena', 18, 24),
('2023-08-07','Pranzo', 10, 15),
('2023-08-07','Cena', 18, 24),
('2023-08-08','Pranzo', 10, 15),
('2023-08-08','Cena', 18, 24),
('2023-08-09','Pranzo', 10, 15),
('2023-08-09','Cena', 18, 24),
('2023-08-10','Pranzo', 10, 15),
('2023-08-10','Cena', 18, 24),
('2023-08-11','Pranzo', 10, 15),
('2023-08-11','Cena', 18, 24),
('2023-08-12','Pranzo', 10, 15),
('2023-08-12','Cena', 18, 24),
('2023-08-13','Pranzo', 10, 15),
('2023-08-13','Cena', 18, 24),
('2023-08-14','Pranzo', 10, 15),
('2023-08-14','Cena', 18, 24);
