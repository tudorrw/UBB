drop table `music-festivals`.festivalsTicketsPricesConcurrency;
create table `music-festivals`.festivalsTicketsPricesConcurrency
(
    festivalId   int not null,
    ticketTypeId int not null,
    price        int not null,
    primary key (festivalId, ticketTypeId),
    constraint festivalsTicketsPricesConcurrency_festivals_id_fk
        foreign key (festivalId) references festivals (id),
    constraint festivalsTicketsPricesConcurrency_ticketTypes_id_fk
        foreign key (ticketTypeId) references ticketTypes (id)
);

insert into `music-festivals`.festivalsTicketsPricesConcurrency (festivalId, ticketTypeId, price)
values
    (1, 1, 100),
    (1, 2, 240);

select * from `music-festivals`.festivalsTicketsPricesConcurrency;

