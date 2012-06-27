create view types(category, typeid, type, attribute, value) as
select c.categoryname category, s.typeid, s.type, s.attribute, value
from (
  select t.groupid, t.typeid, t.typename type, at.attributename attribute, coalesce(ta.valuefloat, ta.valueint) value, t.published
  from static.invtypes t
  join static.dgmtypeattributes ta on ta.typeid = t.typeid
  join static.dgmattributetypes at on at.attributeid = ta.attributeid
  union
  select t.groupid, t.typeid, t.typename type, 'mass' attribute, t.mass value, t.published
  from static.invtypes t
  union
  select t.groupid, t.typeid, t.typename type, 'capacity' attribute, t.capacity value, t.published
  from static.invtypes t
  union
  select t.groupid, t.typeid, t.typename type, 'radius' attribute, t.radius value, t.published
  from static.invtypes t
  union
  select t.groupid, t.typeid, t.typename type, 'volume' attribute, t.volume value, t.published
  from static.invtypes t
) s
join static.invgroups g on g.groupid = s.groupid
join static.invcategories c on c.categoryid = g.categoryid
where s.published = true