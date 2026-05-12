
SELECT 
    s.[first name],
    s.[last name], 
    s.phone, 
    COUNT(r.FK_WorkshopID) AS [Workshop Count] 
FROM Register r 
INNER JOIN student s ON s.ID = r.FK_StudentID 
GROUP BY 
    s.[first name], 
    s.[last name], 
    s.phone;