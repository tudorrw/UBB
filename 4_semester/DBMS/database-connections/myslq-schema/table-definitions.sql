create table festivals
(
    id           int auto_increment
        primary key,
    name         varchar(45) not null,
    location     varchar(45) not null,
    edition      varchar(45) not null,
    startDate    date        not null,
    endDate      date        not null,
    emailContact varchar(45) not null
);


create table bands
(
    id          int auto_increment
        primary key,
    name        varchar(45) not null,
    country     varchar(45) not null,
    formingYear int         not null
);

create table genres
(
    id   int auto_increment
        primary key,
    name varchar(45) not null
);


create table songs
(
    id          int auto_increment
        primary key,
    bandId      int         not null,
    name        varchar(45) not null,
    duration    int         not null,
    releaseDate date        not null,
    genreId     int         null,
    constraint songs_bands_id_fk
        foreign key (bandId) references bands (id),
    constraint songs_genres_id_fk
        foreign key (genreId) references genres (id)
);


create table setlists
(
    festivalId int not null,
    songId     int not null,
    primary key (songId, festivalId),
    constraint setlists_festivals_id_fk
        foreign key (festivalId) references festivals (id),
    constraint setlists_songs_id_fk
        foreign key (songId) references songs (id)
);


create table organisers
(
    id           int auto_increment
        primary key,
    name         varchar(45) not null,
    emailContact varchar(45) not null
);

create table sponsors
(
    id           int auto_increment
        primary key,
    name         varchar(45) not null,
    emailContact varchar(45) not null
);

create table festivalSponsors
(
    festivalId     int not null,
    sponsorId      int not null,
    amountInvested int not null,
    primary key (festivalId, sponsorId),
    constraint festivalSponsors_festivals_id_fk
        foreign key (festivalId) references festivals (id),
    constraint festivalSponsors_sponsors_id_fk
        foreign key (sponsorId) references sponsors (id)
);



create table festivalOrganisers
(
    festivalId  int not null,
    organiserId int not null,
    primary key (festivalId, organiserId),
    constraint festivalOrganisers_festivals_id_fk
        foreign key (festivalId) references festivals (id),
    constraint festivalOrganisers_organisers_id_fk
        foreign key (organiserId) references organisers (id)
);

