select s.itemid source, s.solarsystemid,
    case when s.itemid = d.itemid then sj.celestialid else d.itemid end destination,
    case when s.itemid = d.itemid then o.security else d.security end security,
    case
        when s.itemid = d.itemid then 1
        when s.solarsystemid= d.solarsystemid then sqrt(power(s.x - d.x, 2) + power(s.y - d.y, 2) + power(s.z - d.z, 2)) / 149598000000
    end cost
from static.mapdenormalize s
join static.mapjumps sj on s.itemid = sj.stargateid
join static.mapdenormalize d on s.solarsystemid = d.solarsystemid
join static.mapjumps dj on d.itemid = dj.stargateid
join static.mapdenormalize o on dj.celestialid = o.itemid
