select C.CandybarMenuId, C.VeranstaltungsThema, C.Datum from CandybarMenu C
join CandybarMenuKuchenLink CMKL1 on C.CandybarMenuId = CMKL1.CandybarMenuId
join CandybarMenuKuchenLink CMKL2 on C.CandybarMenuId = CMKL2.CandybarMenuId
join dbo.Kuchen K1 on K1.KuchenId = CMKL1.KuchenId
join dbo.Kuchen K2 on K2.KuchenId = CMKL2.KuchenId
where K1.Namen = 'Himbeertraum' and K2.Namen = 'Schokoladenmousse'


select top 1 K.Namen, K.Beschreibung, count(Z.ZutatId) from Kuchen K
join KuchenZutatLink KZL on K.KuchenId = KZL.KuchenId
join dbo.Zutat Z on Z.ZutatId = KZL.ZutatId
group by K.KuchenId, k.Namen, K.Beschreibung
order by count(Z.ZutatId)

