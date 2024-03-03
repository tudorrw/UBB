select S.Namen from Stadt S
where S.StadtId not in (
    select S.StadtId from Stadt
    inner join dbo.ReisepacketStadtLink RSL on S.StadtId = RSL.StadtId
    inner join dbo.Reisepacket R on R.ReisepacketId = RSL.ReisepacketId
                     )





select R.Beschreibung, count(S.StadtId) from Reisepacket R
inner join dbo.ReisepacketStadtLink RSL on R.ReisepacketId = RSL.ReisepacketId
inner join dbo.Stadt S on S.StadtId = RSL.StadtId
group by R.ReisepacketId, R.Beschreibung
order by count(S.StadtId)







select distinct(R.Beschreibung) from Reisepacket R
inner join dbo.ReisepacketStadtLink RSL on R.ReisepacketId = RSL.ReisepacketId
inner join dbo.Stadt S on S.StadtId = RSL.StadtId
where S.StadtId not in (
    select S.StadtId from Stadt
    where S.Namen = 'Berlin'
    )








