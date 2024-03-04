create table Bands (
    BandId int not null primary key,
    Name varchar(50) not null,
    Country varchar(50) not null,
    FormingYear int not null
)
create table Genres (
    GenreId int not null primary key,
    Name varchar(50) not null
)

create table Festivals (
    FestivalId int not null primary key,
    Name varchar(50) not null,
    Location varchar(50) not null,
    Edition int not null,
    Startdate date not null,
    EndDate date not null,
    EmailContact varchar(50) not null
)


create table Songs (
    SongId int not null primary key,
    BandId int foreign key references Bands(BandId),
    Name varchar(50) not null,
    Duration int not null,
    ReleaseDate date not null,
    GenreId int foreign key references Genres(GenreId)
)

create table Organisers (
    OrganiserId int not null primary key,
    Name varchar(50) not null,
    EmailContact varchar(50) not null
)

create table Sponsors (
    SponsorId int not null primary key,
    Name varchar(50) not null,
    EmailContact varchar(50) not null
)

CREATE TABLE Setlists (
    FestivalId INT,
    SongId INT,
    PRIMARY KEY (FestivalId, SongId),
    FOREIGN KEY (FestivalId) REFERENCES Festivals(FestivalId),
    FOREIGN KEY (SongId) REFERENCES Songs(SongId)
)

CREATE TABLE FestivalOrganisers (
    FestivalId INT,
    OrganiserId INT,
    PRIMARY KEY (FestivalId, OrganiserId),
    FOREIGN KEY (FestivalId) REFERENCES Festivals(FestivalId),
    FOREIGN KEY (OrganiserId) REFERENCES Organisers(OrganiserId)
)

CREATE TABLE FestivalSponsors (
    FestivalId INT,
    SponsorId INT,
    AmountInvested INT,
    PRIMARY KEY (FestivalId, SponsorId),
    FOREIGN KEY (FestivalId) REFERENCES Festivals(FestivalId),
    FOREIGN KEY (SponsorId) REFERENCES Sponsors(SponsorId)
)


create table Tickets (
    TicketId int not null primary key,
    FestivalId int foreign key references Festivals(FestivalId),
    TicketType varchar(50) not null,
    Price int not null,
    PurchaseDate date not null,
    PurchaseName varchar(50) not null,
    PaymentMethod varchar(50) not null
)
