
 create table Ta (
    idA int not null primary key,
    a2 int unique,
    a3 int
);

create table Tb (
    idB int not null primary key,
    b2 int,
    b3 int
);

create table Tc (
    idC int not null primary key,
    idA int foreign key references Ta(idA),
    idB int foreign key references Tb(idB)
);

create or alter procedure insertIntoTables
AS
    begin
        declare @count as int

        set @count = 0
        while @count < 3000
        begin
            insert into Tb(idB, b2, b3)
            values (@count + 1, cast(rand() * 3000 as int) + 1, cast(rand() * 3000 as int) + 1)
            set @count = @count + 1
        end

        set @count = 0
        while @count < 10000
        begin
            insert into Ta(idA, a2, a3)
            values (@count + 1, @count + 1001, 10000 - @count)
            set @count = @count + 1
        end


        set @count = 0
        while @count < 30000
        begin
            insert into Tc(idC, idA, idB)
            values (@count + 1, @count % 10000 + 1, @count % 3000 + 1)
            set @count = @count + 1
        end
    end
GO
exec insertIntoTables

select count(*) from Ta
select count(*) from Tb
select count(*) from Tc

select * from Ta
select * from Tb
select * from Tc
delete from Tc
delete from Ta
delete from Tb

drop table Tc
drop table Ta
drop table Tb

