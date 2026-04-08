### Легкие

**task 74**:
```sql
SELECT 
    id, 
    CASE 
        WHEN has_internet = TRUE THEN 'YES'
        ELSE 'NO'
    END AS has_internet
FROM Rooms
```

**task 56**:
```sql
DELETE FROM TRIP WHERE town_from = 'Moscow';
```

**task 114**:
```sql
SELECT p.name
FROM Flights f
JOIN Pilots p on f.second_pilot_id = p.pilot_id
WHERE flight_date >= '2023-08-01' 
    AND flight_date < '2023-09-01'
    AND destination = 'New York';
```

**task 19**
```sql
SELECT DISTINCT fm.status
FROM Goods g
JOIN Payments p ON g.good_id = p.good
JOIN FamilyMembers fm ON p.family_member = fm.member_id
WHERE g.good_name = 'potato';
```

### Средние

**task 21**
```sql
SELECT g.good_name
FROM Goods g
JOIN Payments p on g.good_id = p.good
GROUP BY g.good_name 
HAVING COUNT(g.good_name) > 1
```

**task 32**
```sql
SELECT FLOOR(AVG(EXTRACT(YEAR FROM AGE(NOW(), birthday)))) as age
FROM FamilyMembers
```