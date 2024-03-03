insert into Land
values (1, 'Deutschland', 'deutsch', 987654),
       (2, 'Spanien', 'spanisch', 456789),
       (3, 'Romanien', 'rumanisch', 236005),
       (4, 'UK', 'englisch', 675345),
       (5, 'France', 'franzosich', 785432)

insert into Stadt
values (1, 'Berlin', 1),
        (2, 'Frankfurt', 1),
        (3, 'Essen', 1),
        (4, 'Madrid', 2),
        (5, 'Alicante', 2),
        (6, 'Malaga', 2),
        (7, 'San Sebastian', 2),
        (8, 'Manchester', 4),
        (9, 'Glasgow', 4),
        (10, 'Marseille', 5),
        (11, 'Nice', 5),
        (12, 'Paris', 5),
        (13, 'Cluj-Napoca', 3)

insert into Kunde
values (1, 'Tudor', 'tudor@gmail.com', '07234421', 3),
        (2, 'Ana', 'ana@gmail.com', '03325641', 1),
        (3, 'Raul', 'raul22@gmail.com', '0567421', 2)

insert into Reisepacket
values (1, 'Hauptstadten'),
        (2, 'schonste stadte deutschlands'),
        (3, 'wunderbar tour'),
         (4, 'alle lander')


insert into ReisepacketLog
values (1, 1, '2024-01-01'),
        (2, 2, '2023-09-01'),
        (3, 1, '2023-04-10'),
        (4, 3, '2023-07-01')

insert into ReisepacketStadtLink (ReisepacketId, StadtId, Tagen)
values  (3, 10, 1),
        (3, 11, 2),
        (3, 13, 2),
        (3, 1, 2),
        (3, 7, 4),
        (4, 10, 2),
        (4, 5, 2),
        (1, 1, 3),
        (1, 4, 2),
        (1, 12, 1),
        (2, 1, 2),
        (2, 2, 2),
        (2, 3, 2)
