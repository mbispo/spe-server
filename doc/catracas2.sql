select distinct p.cd_fnc, NM_Fnc, ds_crgfnl, nr_dia, nr_mes, nr_ano, sgp.fn_inttohora(hr_etr) as hora
from sgp.t_horpnt p
join sgp.t_Vfnc001 f on p.cd_fnc = f.cd_fnc and f.cd_empsgp = 1
where 1=1
and nr_ano = 2010
and nr_mes = 03
and nr_dia = 19
order by 7



set dateformat mdy
select *
from sgp.t_logspe
where datahora > '03/19/2010'
order by id desc




select f.CD_Fnc, NM_Fnc 
from sad.sgp.t_vfnc001 f
right outer join sgp.t_horpnt p on (f.cd_fnc = p.CD_Fnc and f.cd_empsgp = p.cd_empsgp)
where cd_cmrscrfnl = 159 
and cd_vradpt = 646 
and f.cd_fnc > 0
and p.nr_dia = (select substring(convert(varchar(10), getdate(),103), 1,2))
and p.nr_mes = (select substring(convert(varchar(10), getdate(),103), 4,2))
and p.nr_ano = (select substring(convert(varchar(10), getdate(),103), 7,4))


select f.CD_Fnc, NM_Fnc 
from sad.sgp.t_vfnc001 f
where cd_cmrscrfnl = 159 
and cd_vradpt = 646 
and f.cd_fnc > 0
and cd_fnc not in (	select cd_fnc from sgp.t_horpnt p
	where p.nr_dia = (select substring(convert(varchar(10), getdate(),103), 1,2))
	and p.nr_mes = (select substring(convert(varchar(10), getdate(),103), 4,2))
	and p.nr_ano = (select substring(convert(varchar(10), getdate(),103), 7,4))
	)





select substring(convert(varchar(10), getdate(),103), 1,2) as dia, 
       substring(convert(varchar(10), getdate(),103), 4,2) as mes,
	   substring(convert(varchar(10), getdate(),103), 7,4) as ano