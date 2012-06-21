select s.type, s.attribute, value
from (
  select t.typename type, at.attributename attribute, coalesce(ta.valuefloat, ta.valueint) value, t.published
  from static.invtypes t
  join static.dgmtypeattributes ta on ta.typeid = t.typeid
  join static.dgmattributetypes at on at.attributeid = ta.attributeid
  union
  select t.typename type, 'mass' attribute, t.mass value, t.published
  from static.invtypes t
) s
where s.published = true
