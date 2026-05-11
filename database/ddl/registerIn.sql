
create table Register(
    FK_StudentID int , 
    FK_WorkshopID int , 
    [register date] date not null , 
    state nvarchar(20) not null , 

    constraint PK_Register primary key(FK_StudentID , FK_WorkshopID) , 
    foreign key(FK_StudentID) references student(ID) , 
    foreign key(FK_WorkshopID) references Workshop(ID)
)
