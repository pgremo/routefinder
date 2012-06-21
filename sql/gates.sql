create view gates(sourceid, sourcesystemid, destinationid, destinationsystemid, security, cost) as
select s.itemid sourceid, s.solarsystemid sourcesystemid,
    case when s.itemid = d.itemid then o.itemid else d.itemid end destinationid,
    case when s.itemid = d.itemid then o.solarsystemid else d.solarsystemid end destinationsystemid,
    round(case when s.itemid = d.itemid then o.security else d.security end, 1) security,
    case
        when s.itemid = d.itemid then 1
        else sqrt(power(s.x - d.x, 2) + power(s.y - d.y, 2) + power(s.z - d.z, 2)) / 149598000000
    end cost
from static.mapdenormalize s
join static.mapjumps sj on s.itemid = sj.stargateid
join static.mapdenormalize d on s.solarsystemid = d.solarsystemid
join static.mapjumps dj on d.itemid = dj.stargateid
join static.mapdenormalize o on sj.celestialid = o.itemid
