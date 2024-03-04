SELECT DISTINCT Bands.Name  FROM Bands
INNER JOIN Songs ON Bands.BandID = Songs.BandID
INNER JOIN Setlists ON Songs.SongID = Setlists.SongID
INNER JOIN Festivals ON Setlists.FestivalID = Festivals.FestivalID AND Festivals.Name LIKE 'Rock%'


SELECT Genres.Name, COUNT(*) FROM Songs
INNER JOIN Genres ON Songs.GenreID = Genres.GenreID
GROUP BY Genres.Name, Genres.GenreID;


SELECT TicketType FROM Tickets
GROUP BY TicketType HAVING COUNT(*) < 3;
Select * from Tickets;


SELECT Festivals.Name, Festivals.Edition FROM Festivals
INNER JOIN Setlists ON Festivals.FestivalID = Setlists.FestivalID
INNER JOIN Songs ON Setlists.SongID = Songs.SongID
INNER JOIN Bands ON Songs.BandID = Bands.BandID
WHERE Bands.Name = 'Obituary'
INTERSECT
SELECT Festivals.Name, Festivals.Edition FROM Festivals
INNER JOIN Setlists ON Festivals.FestivalID = Setlists.FestivalID
INNER JOIN Songs ON Setlists.SongID = Songs.SongID
INNER JOIN Bands ON Songs.BandID = Bands.BandID
WHERE Bands.Name = 'Megadeth';


SELECT TOP 3 YEAR(Songs.ReleaseDate), COUNT(*) FROM Songs
GROUP BY YEAR(Songs.ReleaseDate) HAVING COUNT(*) > 3
ORDER BY COUNT(*) DESC;

SELECT SUM(Songs.Duration) FROM Songs
WHERE Songs.SongID IN (
	SELECT Songs.SongID FROM Songs
	INNER JOIN Bands ON Songs.BandID = Bands.BandID
	WHERE Bands.Name = 'Mayhem')
AND Songs.SongID NOT IN (SELECT Songs.SongID FROM Songs
INNER JOIN Setlists ON Songs.SongID = Setlists.SongID
INNER JOIN Festivals ON Setlists.FestivalID = Festivals.FestivalID
WHERE Festivals.Name = 'Wacken');

SELECT AVG(Songs.Duration) FROM Songs
WHERE Songs.Duration >= ALL(
SELECT Songs.Duration FROM Songs
INNER JOIN Bands ON Songs.BandID = Bands.BandID
WHERE Bands.Country = 'UK' OR Bands.Country = 'Scotland');

SELECT Songs.Name FROM Songs
JOIN Setlists ON Songs.SongID = Setlists.SongID
JOIN Festivals ON Setlists.FestivalID = Festivals.FestivalID
WHERE Festivals.Name = 'Hellfest'
EXCEPT
SELECT Songs.Name FROM Songs
JOIN Setlists ON Songs.SongID = Setlists.SongID
JOIN Festivals ON Setlists.FestivalID = Festivals.FestivalID
WHERE Festivals.Name = 'Wacken';


SELECT Songs.Name, Festivals.Name, Festivals.Edition FROM Songs
LEFT OUTER JOIN Setlists ON Songs.SongID = Setlists.SongID
LEFT OUTER JOIN Festivals ON Setlists.FestivalID = Festivals.FestivalID


SELECT Songs.Name FROM Songs
WHERE Songs.Duration <= ANY(
SELECT Songs.Duration FROM Songs
INNER JOIN Bands ON Songs.BandID = Bands.BandID
WHERE Bands.Name = 'Alestorm'
UNION
SELECT Songs.Duration FROM Songs
INNER JOIN Bands ON Songs.BandID = Bands.BandID
WHERE Bands.Name = 'In Flames')
AND Songs.BandID NOT IN (
SELECT Bands.BandID FROM Bands
WHERE Bands.Name = 'Alestorm' OR Bands.Name = 'In Flames');


