
--a
exec sys.sp_helpindex @objname = N'Ta'

select TableName = t.name,
       IndexName = idx.name,
       IndexType = case
                    when idx.is_primary_key = 1 then 'Primary key'
                    else 'Nonclustered'
                   end
from sys.indexes idx
inner join sys.tables t on idx.object_id = t.object_id
where t.name = 'Ta'

select * from Ta
select * from Ta where IdA = 501
select a2 from Ta
select idA,a2 from Ta where a2 = 5000


--b
select * from Ta where a2 between 3000 and 3015
--c
select * from Tb where b2=49

create nonclustered index IX_Tb_b2 on Tb(b2)
drop index IX_Tb_b2 on Tb

--d
select Tc.idC from Tc
inner join dbo.Ta on Ta.idA = Tc.idA
where Tc.idA = 5001

select Tc.idC from Tc
inner join dbo.Tb on Tb.idB = Tc.idB
where Tc.idB = 1236




create nonclustered index IX_Tc_idA on Tc(idA)
create nonclustered index IX_Tc_idB on Tc(idB)

drop index IX_Tc_idA on Tc
drop index IX_Tc_idB on Tc








--a








