insert into Kunde
values  (1, 'Tudor', 'tudor@gmail.com', '032322719'),
        (2, 'Raul', 'raul@yahoo.com', '03552344'),
        (3, 'Sergiu', 'sergiu78@gmail.com', '30922226'),
        (4, 'Andrei', 'sandrei@yahoo.com', '9323203')

insert into Masseinheit
values  (1, 'g'),
        (2, 'ml'),
        (3, 'kg'),
        (4, 'l')

insert into Zutat
values (1, 'Milch', 2),
       (2, 'Zucker', 1),
       (3, 'Vanilla', 1),
       (4, 'Kokos', 1),
       (5, 'Schokolade', 1),
       (6, 'Mehl', 1),
       (7, 'Zitrone', 2),
       (8, 'Himbeer', 1),
       (9, 'Erdbeer', 1),
       (10, 'Butter', 1),
       (11, 'Wasser', 2),
       (12, 'Backpulver', 1),
        (13, 'Staubzucker', 1),
        (14, 'Sahne', 1),
        (15, 'Salz', 1)


insert into Kuchen
values (1, 'Vanilla-Cupcake', 'aaaaaaa'),
       (2, 'ErdbeerCupcake', 'bbbbbbbb'),
       (3, 'Zitronen Mousse', 'cccccccc'),
       (4, 'Kokos Tortchen', 'ddddddddd'),
       (5, 'Himbeertraum', 'eeeeeeeeeee'),
       (6, 'Nutellakuchen', 'ffffffffff'),
       (7, 'Pfanekuchen', 'hhhhhhhhh'),
       (8, 'Karottenkuchen', 'sssssssss'),
        (9, 'Schokoladenmousse', 'qqqqqqqqqq')


insert into KuchenZutatLink
values (1, 1, 300),
       (1, 2, 200),
       (1, 12, 14),
       (1, 15, 10),
        (2, 1, 400),
        (2, 2, 400),
        (2, 9, 200),
        (2, 10, 130),
        (2, 13, 50),
        (3, 7, 450),
        (3, 1, 200),
        (3, 2, 300),
        (4, 4, 500),
        (4, 2, 130),
        (4, 10, 200),
        (4, 6, 500),
        (4, 11, 400),
        (5, 8, 300),
        (5, 1, 333),
        (5, 2, 400),
        (5, 11, 200)

insert into CandybarMenu
values (1, 4, 'Geburtstagparty', '2023-09-09'),
       (2, 2, 'Hochzeit', '2023-09-20'),
       (3, 3, 'Geburtstagparty', '2023-10-01'),
       (4, 1, 'Geburtstagparty', '2023-10-04'),
        (5, 1, 'Weinachtsparty', '2023-12-24'),
       (6, 4, 'Weinachtsparty', '2023-12-23')


insert into CandybarMenuKuchenLink
values  (3, 9, 1),
        (5, 5, 2),
        (2, 9, 5),
       (2, 1, 200),
       (3, 4, 2),
       (3, 5, 1),
       (5, 6, 4),
       (6, 2, 40),
       (1, 5, 1),
        (1, 8, 2),
        (4, 9, 2),
        (5, 9, 1)

