create view types as
select c.categoryid, c.categoryname, g.groupid, g.groupname, t.typeid, t.typename, t.description, t.radius, t.mass, t.volume, t.capacity, t.portionsize, t.raceid, t.baseprice, t.marketgroupid, t.chanceofduplicating , at.attributename, coalesce(ta.valuefloat, ta.valueint) value, t.published
from static.invtypes t
join static.dgmtypeattributes ta on ta.typeid = t.typeid
join static.dgmattributetypes at on at.attributeid = ta.attributeid
join static.invgroups g on g.groupid = t.groupid
join static.invcategories c on c.categoryid = g.categoryid
where t.published = true