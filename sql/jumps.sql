create view public.jumps(fromsystem, tosystem, tosecurity, cost) as
select s.solarsystemname fromsystem, d.solarsystemname tosystem, round(d.security, 1) tosecurity, 1.0 as cost
from static.mapsolarsystemjumps j
join static.mapsolarsystems s on s.solarsystemid = j.fromsolarsystemid
join static.mapsolarsystems d on d.solarsystemid = j.tosolarsystemid
