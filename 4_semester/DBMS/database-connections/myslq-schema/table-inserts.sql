insert into festivals(id, name, location, edition, startDate, endDate, emailContact)
values (1, 'Rockstadt',	'Romania',	5,	'2018-08-02',	'2018-08-05',	'info@rockstadtextremefest.ro'),
       (2,	'Hellfest',	'France', 15,	'2022-06-17',	'2022-06-26',	'info@hellfest.fr'),
		(3,	'Wacken', 'Germany',	33,	'2023-08-02',	'2023-08-05',	'info@wacken.de'),
		(4,	'Rockstadt',	'Romania', 9,	'2023-08-02',	'2023-08-06',	'info@rockstadtextremefest.ro');


INSERT INTO bands (id, name, country, formingYear)
VALUES
    (1, 'Dropkick Murphys', 'USA', 1996),
    (2, 'Obituary', 'USA', 1984),
    (3, 'Alestorm', 'Scotland', 2004),
    (4, 'Metallica', 'USA', 1981),
    (5, 'Megadeth', 'USA', 1983),
    (6, 'Kreator', 'Germany', 1982),
    (7, 'In Flames', 'Sweden', 1990),
    (8, 'Mayhem', 'Norway', 1986),
    (9, 'Meshuggah', 'Sweden', 1987),
    (10, 'Sleep Token', 'UK', 2016),
    (11, 'Heilung', 'Germany', 2014),
    (12, 'Architects', 'UK', 2004);


INSERT INTO genres (id, name)
VALUES
    (1, 'metalcore'),
    (2, 'thrash metal'),
    (3, 'black metal'),
    (4, 'alternative metal'),
    (5, 'folk'),
    (6, 'neofolk'),
    (7, 'progressive metal'),
    (8, 'extreme metal'),
    (9, 'melodic death metal'),
    (10, 'pirate metal'),
    (11, 'heavy metal'),
    (12, 'celtic punk'),
    (13, 'death metal');

INSERT INTO genres (name)
VALUES ('grindcore');

INSERT INTO Songs (id, bandId, name, duration, releaseDate, genreId)
VALUES
    (1, 12, 'Animals', 244, '2021-02-26', 1),
    (2, 12, 'Doomsday', 248, '2018-11-09', 1),
    (3, 12, 'Holy Hell', 253, '2018-11-09', 1),
    (4, 12, 'Royal Beggars', 241, '2018-11-09', 1),
    (5, 12, 'Black Lungs', 231, '2021-02-26', 1),
    (6, 10, 'Vore', 339, '2023-02-16', 7),
    (7, 10, 'Alkaline', 274, '2021-09-24', 4),
    (8, 10, 'Chokehold', 304, '2023-05-19', 4),
    (9, 10, 'Hypnosis', 335, '2021-09-24', 7),
    (10, 10, 'The Summoning', 395, '2023-05-19', 4),
    (11, 8, 'Freezing Moon', 353, '1994-01-01', 3),
    (12, 8, 'Life Eternal', 417, '1994-01-01', 3),
    (13, 8, 'Chimera', 420, '1994-01-17', 3),
    (14, 8, 'Deathcrush', 213, '1993-01-01', 3),
    (15, 8, 'Funeral Fog', 347, '1994-01-01', 3),
    (16, 6, 'State of Unrest', 217, '2023-02-10', 2),
    (17, 6, 'Hail to the Hordes', 242, '2017-01-27', 2),
    (18, 6, 'World War Now', 250, '2017-01-27', 11),
    (19, 6, 'Enemy Of God', 343, '2005-01-10', 2),
    (20, 6, 'Voices of the Dead', 273, '2005-01-10', 2),
    (21, 4, 'Master Of Puppets', 515, '1984-07-26', 11),
    (22, 4, 'Fade to Black', 417, '1984-07-26', 11),
    (23, 4, 'The Memory Remains', 279, '1997-01-01', 11),
    (24, 4, 'Sad But True', 323, '1991-08-12', 11),
    (25, 4, 'Enter Sandman', 340, '1991-08-12', 11),
    (26, 2, 'Redneck Stomp', 212, '2005-07-11', 13),
    (27, 2, 'Chopped in Half', 225, '1990-01-01', 13),
    (28, 2, 'Slowly We Rot', 228, '1989-01-01', 13),
    (29, 2, 'Visions in My Head', 256, '2014-10-27', 13),
    (30, 2, 'Violence', 126, '2014-10-27', 13),
    (31, 1, 'I''m Shipping Up To Boston', 153, '2005-07-19', 12),
    (32, 1, 'Rose Tattoo', 306, '2013-01-08', 12),
    (33, 1, 'The Boys Are Back', 199, '2023-01-08', 12),
    (34, 1, 'The State Of Massachusetts', 232, '2007-09-18', 12),
    (35, 3, 'Drink', 202, '2014-08-04', 10),
    (36, 3, 'Mexico', 190, '2017-05-26', 10),
    (37, 3, 'Hangover', 221, '2014-08-04', 10),
    (38, 3, 'Keelhauled', 222, '2009-01-01', 10),
    (39, 3, 'Leviathan', 355, '2009-01-01', 10),
    (40, 5, 'Symphony Of Destruction', 241, '1992-07-14', 2),
    (41, 5, 'Tornado Of Souls', 319, '1990-10-04', 2),
    (42, 5, 'Hangar 18', 311, '1990-10-04', 2),
    (43, 5, 'A Tout Le Monde', 262, '1994-10-28', 2),
    (44, 7, 'I Am Above', 229, '2019-03-01', 9),
    (45, 7, 'Meet Your Maker', 237, '2023-02-10', 4),
    (46, 7, 'Only For The Weak', 296, '2000-07-03', 4),
    (47, 7, 'The Jester''s Dance', 129, '2000-07-03', 9),
    (48, 9, 'Bleed', 442, '2008-03-07', 8),
    (49, 9, 'Combustion', 328, '2008-03-07', 8),
    (50, 9, 'Demiurge', 372, '2012-02-09', 8),
    (51, 9, 'Broken Cog', 395, '2022-03-31', 8),
    (52, 11, 'Norupo', 437, '2019-06-28', 6),
    (53, 11, 'Anoana', 296, '2022-07-19', 5),
    (54, 11, 'Svanrand', 216, '2019-06-28', 5),
    (55, 11, 'Hakkerskaldyr', 129, '2018-01-12', 6);


INSERT INTO Setlists (festivalid, songid)
VALUES
    (1, 27),
    (1, 28),
    (1, 29),
    (1, 46),
    (1, 47),
    (2, 23),
    (2, 24),
    (2, 25),
    (2, 26),
    (2, 27),
    (2, 28),
    (2, 29),
    (2, 30),
    (2, 31),
    (2, 33),
    (2, 40),
    (2, 41),
    (3, 14),
    (3, 15),
    (3, 18),
    (3, 19),
    (3, 20),
    (3, 31),
    (3, 32),
    (3, 33),
    (3, 34),
    (4, 1),
    (4, 2),
    (4, 3),
    (4, 5),
    (4, 11),
    (4, 12),
    (4, 13),
    (4, 26),
    (4, 27),
    (4, 29),
    (4, 30),
    (4, 31),
    (4, 32),
    (4, 34),
    (4, 35),
    (4, 36),
    (4, 38),
    (4, 39),
    (4, 44),
    (4, 45),
    (4, 46),
    (4, 52),
    (4, 54),
    (4, 55);


INSERT INTO Organisers (id, Name, EmailContact)
VALUES
    (1, 'Rockstadt Events', 'info@rockstadtevents.ro'),
    (2, 'Wacken Foundation', 'info@wackenfoundation.de'),
    (3, 'Hellfest Productions', 'info@hellfestprod.fr'),
    (4, 'Obscure Promotion', 'info@obscurepromotion.cz'),
    (5, 'K Production', 'info@kproduction.fr');

INSERT INTO Sponsors (id, Name, EmailContact)
VALUES
    (1, 'RedBull', 'customerservice@redbullshopus.com'),
    (2, 'Heineken', 'service.experience@heineken.com'),
    (3, 'Yamaha Music', 'media@yamaha.com'),
    (4, 'Telekom', 'info@telekom.com'),
    (5, 'Mastercard', 'customerservice@mastercard.com');


INSERT INTO FestivalSponsors (festivalid, sponsorid, amountinvested)
VALUES
    (1, 1, 1200),
    (1, 2, 6000),
    (2, 3, 10000),
    (4, 5, 4700);

INSERT INTO FestivalOrganisers (festivalid, organiserid)
VALUES
    (1, 1),
    (2, 3),
    (3, 2),
    (3, 5),
    (4, 1);

