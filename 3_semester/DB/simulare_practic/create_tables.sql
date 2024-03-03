--backerei

create table Kunde (
    KundeId int not null primary key,
    Namen varchar(255),
    Emailadresse varchar(255),
    Telefonnummer varchar(10)
)

create table Kuchen (
    KuchenId int not null primary key,
    Namen varchar(255),
    Beschreibung varchar(MAX)
)

create table Masseinheit(
    MasseinheitId int not null primary key,
    Namen varchar(5)
)

create table Zutat (
    ZutatId int not null primary key,
    Namen varchar(255),
    MasseinheitId int,
    foreign key (MasseinheitId) references Masseinheit(MasseinheitId)
)


create table KuchenZutatLink (
    KuchenId int,
    ZutatId int,
    Quantitat int,
    primary key(KuchenId, ZutatId),
    foreign key (KuchenId) references Kuchen(KuchenId),
    foreign key (ZutatId) references Zutat(ZutatId)
)

create table CandybarMenu (
    CandybarMenuId int not null primary key,
    KundeId int,
    VeranstaltungsThema varchar(50),
    Datum date,
    foreign key (KundeId) references Kunde(KundeId)
)

create table  CandybarMenuKuchenLink (
    CandybarMenuId int,
    KuchenId int,
    Menge int,
    primary key(CandybarMenuId, KuchenId),
    foreign key (CandybarMenuId) references CandybarMenu(CandybarMenuId),
    foreign key (KuchenId) references Kuchen(KuchenId)
)






-- laden

create table Genre (
    GenreId int not null primary key,
    Namen varchar(255)
)

create table Schallplatte (
    SchallplatteId int not null primary key,
    Namen varchar(255),
    GenreId int,
    foreign key (GenreId) references Genre(GenreId)
)

create table Lied (
    LiedId int not null primary key,
    Titel varchar(255),
    ArtistNamen varchar(255),
    Dauer int not null
)

create table SchallplatteLiedLink (
    SchallplatteId int,
    LiedId int,
    primary key(SchallplatteId, LiedId)
)

create table Kunde(
    KundeId int not null primary key,
    Namen varchar(255),
    Emailadresse varchar(255)
)

create table Bestellung (
    BestellungId int not null primary key,
    KundeId int,
    Adresse varchar(255),
    foreign key (KundeId) references Kunde(KundeId)
)

create table BestellungSchallplatteLink(
    BestellungId int,
    SchallplatteId int,
    primary key(BestellungId, SchallplatteId)
)
