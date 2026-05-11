
create table Workshop(
    ID int primary key , 
    FK_KitchenID int not null , 
    FK_ChefID int not null , 
    title nvarchar(50) , 
    price decimal(10 , 2) not null , 
    [start date] date not null , 
    [end date] date not null , 
    state nvarchar(20) not null ,
    constraint date_constraint check ([start date] <= [end date]) , 
    foreign key (FK_KitchenID) references kitchen(ID) , 
    foreign key (FK_ChefID) references chef(ID)
)
