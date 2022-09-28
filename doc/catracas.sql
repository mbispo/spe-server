use senior

--matricula X cracha
SELECT NumCad, NumCra
FROM R038HCH
where numEmp = 1
and Numcad = 8998
ORDER BY NumCad


select * from R070ACC
where numcra in ('2931072588')






/* problemas constatados

- cadastros duplicados, triplicados (ex. NumCad = 72,81,84,88)
- os juizes e desembargadores estáo em outra tabela?
- existem funcionarios ja desligados, e alguns que trabalham em comarcas e Não aqui no TJ
- funcionários com mais de um registro, como identifico o numero correto do cracha?

*/

--cadastro provisorio??

--tabela que registra entrada e saida
use senior
select distinct c.numcad, r.numcra, r.datacc, 
  hora = (select min(dbo.fn_inttohora((horacc*60))) 
		  from dbo.R070ACC h 
		  where h.numcra = r.numcra
		  and datacc = '2010-17-03 00:00:00.000' )
from dbo.R070ACC r
inner join dbo.R038HCH c on c.Numcra = r.Numcra
--where datacc = '2010-19-03 00:00:00.000'
and c.numcad in (9302,9330,209,6363,7758,8808,9059,9272,9276,9279,9300,9306,9308,9310,9314,
  9328,9331,9334,9435,9438,9604,9605,11215,11216,11360)
--order by r.datacc desc

select CD_Fnc, NM_Fnc from sad.sgp.t_vfnc001 where cd_cmrscrfnl = 159 and cd_vradpt = 646 and cd_fnc > 0

select min(dbo.fn_inttohora((horacc*60))) 
from dbo.R070ACC h 
where h.numcra = '2931072588'
and datacc = '2010-17-03 00:00:00.000'


use senior
select c.numcad, r.numcra, convert(varchar(10),datacc,103) as data, dbo.fn_inttohora((horacc*60)) as hora, diracc
from dbo.R070ACC r
inner join dbo.R038HCH c on c.Numcra = r.Numcra
where 1=1 --
and datacc > '2011-05-01 00:00:00.000'
and c.numcad in (8998)
order by datacc, dataacc 


select *
from R070ACC
where numcra = '3187'
and datacc > '2011-05-01 00:00:00.000'
order by datacc 

--join com T_Fnc
--apenas cargos isentos do ponto
Select DS_CmrScrFnl, CD_EmpSgp, s.DS_stcfnc, CD_Fnc, NM_Fnc, NumCad, NumCra, cd_nmecrgfnl, ds_crgFnl
from sad.sgp.t_vfnc001 f
join senior.dbo.R038HCH c on f.CD_Fnc = c.NumCad and c.NumEmp = f.CD_EmpSgp
join sad.sgp.t_Stcfnc s on s.cd_stcFnc = f.CD_StcFncFnl
join sad.sgp.T_CrgIsnPnt p on f.cd_nmecrgfnl = p.cd_nmecrg
where 1=1
and f.cd_stcfncfnl = 1
and f.cd_empsgp = 1 
and f.CD_Hrr = 2
order by DS_CmrScrFnl

use sad
--cargos e fnc isentos - apenas sad
Select distinct f.CD_Fnc--, DS_CmrScrFnl, f.CD_EmpSgp, f.CD_stcfncfnl, NM_Fnc, cd_nmecrgfnl, ds_crgFnl
from sgp.T_CrgIsnPnt p 
join sgp.t_vfnc001 f on f.cd_nmecrgfnl = p.cd_nmecrg
where 1=1
and f.cd_stcfncfnl = 1
and f.cd_empsgp = 1 
and f.CD_Hrr = 2
and p.DT_FimIsn is null
union all
Select distinct f.CD_Fnc, DS_CmrScrFnl, f.CD_EmpSgp, f.CD_stcfncfnl, NM_Fnc, cd_nmecrgfnl, ds_crgFnl
from sgp.T_FncAtzRgsprs r 
join sgp.t_vfnc001 f on f.cd_Fnc = r.cd_Fnc
where 1=1
and f.cd_stcfncfnl = 1
and f.cd_empsgp = 1 
and f.CD_Hrr = 2
and r.DT_FimIsn is null
order by 2


select * from sgp.t_CrgIsnPnt

select * from sgp.T_FncAtzRgsprs r 


--autorizados registrar presenca
select ds_cmrscrfnl, f.cd_fnc, NM_Fnc, ds_CrgFnl, cd_stcfnc
from sgp.t_fncatzrgsprs a
inner join sgp.t_vfnc001 f on a.cd_fnc = f.cd_fnc and f.cd_empsgp = 1
where f.cd_hrrfnl = 2
order by 1


--ja importou no dia
select * from sgp.t_horpnt
where cd_fnc = 9302
and nr_ano = 2010
and nr_mes = 03
and nr_dia = 16



select CD_Fnc, nm_fnc, ds_crgfnl, ds_cmrScr, ds_Vradpt 
from sad.sgp.t_vfnc001
where cd_fnc in (6232)
and cd_empsgp = 1

select i.cd_nmecrg, ds_nmecrg
from sad.sgp.T_CrgIsnPnt i
join sad.sgp.t_nmecrg c on i.cd_nmecrg = c.cd_nmecrg
order by ds_nmecrg


--ja importou no dia
select * from sgp.t_horpnt
where cd_fnc = 9302
and nr_ano = 2010
and nr_mes = 03
and nr_dia = 16



select CD_cmrscr, DS_cmrscr, CD_vraDpt, ds_vraDpt
from sgp.t_estadm
where 1 = 1
and cd_hrr = 2
and fl_ultato = 1
and 


select CD_Fnc, NM_Fnc
from sgp.t_vfnc001
where cd_cmrscrfnl = 159
and cd_vradpt = 646
and cd_fnc > 0


select * from sgp.t_fncatzrgsprs

select cd_fnc, nm_fnc, cd_Stcfnc, cd_stcfncacm
from sgp.t_fnc
where cd_fnc in (9272,9306,10697)

select * 
from sgp.t_horpnt
where 1=1
and nr_ano = 2010
and nr_dia = 17
and nr_mes = 3