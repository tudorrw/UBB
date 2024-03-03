CREATE FUNCTION validateId (@Id INT)
RETURNS BIT AS
begin
    declare @valid int
    set @valid = 0
    if @Id is not null
        begin
            if @Id > 0
                set @valid = 1
        end
    return @valid
end
GO

drop function validateId

CREATE FUNCTION validateStrings (@string VARCHAR(50))
RETURNS BIT AS
begin
    declare @valid int
    set @valid = 0
    if len(@string) > 0
        set @valid = 1
    return @valid
end
GO

CREATE FUNCTION validateFormingYear (@FormingYear INT)
RETURNS BIT AS
begin
    declare @valid int
    set @valid = 0
    if @FormingYear is not null
        if @FormingYear between 1940 and year(getdate())
            set @valid = 1
    return @valid
end

print year(getdate())
GO
drop function validateFormingYear

CREATE PROCEDURE insertValuesIntoBandsTable(@BandId INT, @Name VARCHAR(50), @Country VARCHAR(50), @FormingYear INT)
    AS
        begin
            if dbo.validateId(@BandId) = 0
            begin
                print 'Invalid Id'
                return
            end
            if @BandId in (select BandId from Bands where BandId = @BandId)
            begin
                print 'Existent Id'
                return
            end
            if dbo.validateStrings(@Name) = 0
            begin
                print 'Invalid Name'
                return
            end
            if dbo.validateStrings(@Country) = 0
            begin
                print 'Invalid Country'
                return
            end
            if dbo.validateFormingYear (@FormingYear) = 0
            begin
                print 'Invalid Forming Year'
                return
            end
            insert into Bands(BandId, Name, Country, FormingYear)
            values (@BandId, @Name, @Country, @FormingYear)
        end
    GO
--drop procedure insertValuesIntoBandsTable
insertValuesIntoBandsTable '','', '', ''
insertValuesIntoBandsTable 13, 'aa','', ''
insertValuesIntoBandsTable 13, 'aa','aa', ''
insertValuesIntoBandsTable 13, 'aa','aa', 1939
insertValuesIntoBandsTable 13, 'Gojira', 'France', 1996
insertValuesIntoBandsTable 13, 'Bad Omens', 'US', 2015
select count(*) from Bands
delete from Bands where BandId = 13

CREATE VIEW getSongAndGenre
AS
    select S.Name as song_name_view, G.Name as genre_name_view from Songs AS S
    inner join dbo.Genres G on G.GenreId = S.GenreId
GO

select * from getSongAndGenre

CREATE FUNCTION GetSetlistByFestival (@FestivalName VARCHAR(50), @FestivalEdition INT)
RETURNS @SetlistByFestival TABLE (
        band_name_func varchar(50),
        song_name_func varchar(50)
        )
AS
    begin
        insert into @SetlistByFestival
        select B.Name, S.Name from Songs S
        inner join dbo.Bands B on S.BandId = B.BandId
        inner join dbo.Setlists S2 on S.SongId = S2.SongId
        inner join dbo.Festivals F on F.FestivalId = S2.FestivalId
        where F.Name = @FestivalName and F.Edition = @FestivalEdition
        if @@ROWCOUNT = 0
        begin
            insert into @SetlistByFestival
            values ('keine songs gefunden', '')
        end
        return
    end
GO

select * from dbo.GetSetlistByFestival('Rockstadt', 9)
select * from Festivals

select distinct band_name_func as bands, genre_name_view as genres from dbo.GetSetlistByFestival ('Rockstadt', 9)
inner join getSongAndGenre on song_name_view = song_name_func




create table 






