create table Land (
    LandId int not null primary key,
    Namen varchar(255),
    Sprache varchar(255),
    Flache bigint
)

create table Kunde (
    KundeId int not null primary key,
    Namen varchar(255),
    Emailadresse varchar(255),
    Telefonnummer varchar(255),
    LandId int,
    foreign key (LandId) references Land(LandId)
)


create table Stadt (
    StadtId int not null primary key,
    Namen varchar(255),
    LandId int,
    foreign key (LandId) references Land(LandId)
)

create table Reisepacket (
    ReisepacketId int not null primary key,
    Beschreibung varchar(MAX),
)

create table ReisepacketStadtLink (
    ReisepacketId int,
    StadtId int,
    primary key(ReisepacketId, StadtId),
    foreign key (ReisepacketId) references Reisepacket(ReisepacketId),
    foreign key (StadtId) references Stadt(StadtId),
    Tagen int
)

create table ReisepacketLog (
    ReisepacketId int,
    KundeId int,
    primary key(ReisepacketId, KundeId),
    startdatum date
)

