create view jumps(fromsolarsystemid, tosolarsystemid, tosecurity, cost) as
select j.fromsolarsystemid, j.tosolarsystemid, round(d.security, 1) tosecurity, 1.0 as cost
from static.mapsolarsystemjumps j
join static.mapsolarsystems d on d.solarsystemid = j.tosolarsystemid
