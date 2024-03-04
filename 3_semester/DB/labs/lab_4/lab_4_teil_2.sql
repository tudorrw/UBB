  --übung 3
create table LogTriggerTabelle (
    action_date datetime,
    action_type char(1),
    table_name varchar(50),
    affected_tuples int
)
delete from LogTriggerTabelle
--drop table LogTriggerTabelle

create or alter trigger TicketsTableTrigger
    on Tickets
    for insert, update, delete
as
begin
    set nocount on
    declare @ActionType as char(1)
    declare @TableName as varchar(50)
    declare @AffectedTuple as int

    if exists (select 1 from inserted) and exists (select 1 from deleted)
        begin
            set @ActionType = 'U'
            set @AffectedTuple = (select count(*) from inserted)
        end
    else if exists (select 1 from inserted)
        begin
            set @ActionType = 'I'
            set @AffectedTuple = (select count(*) from inserted)

        end
    else if exists (select 1 from deleted)
        begin
            set @ActionType = 'D'
            set @AffectedTuple = (select count(*) from deleted)
        end
    set @TableName = object_name(object_id('Tickets'))
    insert into LogTriggerTabelle(action_date, action_type, table_name, affected_tuples)
    values (getdate(), @ActionType, @TableName, @AffectedTuple)
end
go

drop trigger TicketsTableTrigger

select * from Tickets
select * from LogTriggerTabelle

insert into Tickets(ticketid, festivalid, tickettype, price, purchasedate, purchasename, paymentmethod)
values(9, 2, 'VIP Access', 500, '2022-01-05', 'Marius', 'Credit Card'),
    (10, 2, 'VIP Access', 500, '2022-01-05', 'Marius', 'Credit Card')


--delete from Tickets where TicketId = 9

update Tickets set TicketType = 'VIP Acs' where TicketType = 'VIP Access'
--update Tickets set TicketType = 'VIP Access' where TicketType = 'VIP Acs'

delete from Tickets where TicketId = 5 or TicketId = 8

update Tickets set TicketType = 'EB' where TicketType = 'Early Bird'
--update Tickets set TicketType = 'Early Bird' where TicketType = 'EB'

delete from Tickets where PaymentMethod = 'Credit Card'



 --übung 4
create or alter procedure UpdateOrDeleteSong (@SongId int, @GenreId int)
as
    if exists (select 1 from Genres where GenreId = @GenreId and Name like '%metal')
    begin
        update Songs_for_lab_4 set Duration = Duration + 10 where SongId = @SongId
    end
else
    begin
        delete from Songs_for_lab_4 where SongId = @SongId
    end
go

create or alter procedure CursorSongs
as
    begin
        declare @SongId as int
        declare @GenreId as int

        declare songCursor cursor for
        select SongId, GenreId from Songs_for_lab_4

        open songCursor

        fetch next from songCursor into @SongId, @GenreId
        while(@@fetch_status = 0)
        begin
            exec UpdateOrDeleteSong @SongId, @GenreId
            fetch next from songCursor into @SongId, @GenreId
        end

        close songCursor
        deallocate songCursor
    end
go

EXEC CursorSongs

select * from Songs
select * from Genres
select * from Songs_for_lab_4

delete from Songs_for_lab_4

insert into Songs_for_lab_4
select * from Songs

select count(*) from Songs S
inner join dbo.Genres G on G.GenreId = S.GenreId
where G.Name like '%metal'











