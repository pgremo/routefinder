create view types(category, type, attribute, value) as
select s.category, s.type, s.attribute, value
from (
  select c.categoryname category, t.typename type, at.attributename attribute, coalesce(ta.valuefloat, ta.valueint) value, t.published
  from static.invtypes t
  join static.invgroups g on g.groupid = t.groupid
  join static.invcategories c on c.categoryid = g.categoryid
  join static.dgmtypeattributes ta on ta.typeid = t.typeid
  join static.dgmattributetypes at on at.attributeid = ta.attributeid
  union
  select c.categoryname category,  t.typename type, 'mass' attribute, t.mass value, t.published
  from static.invtypes t
  join static.invgroups g on g.groupid = t.groupid
  join static.invcategories c on c.categoryid = g.categoryid
) s
where s.published = true