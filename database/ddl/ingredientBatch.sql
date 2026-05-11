
create table Ingredient_batch(
    ID int primary key , 
    FK_SupplierID int not null , 
    name nvarchar(50) , 
    units int not null , 
    [expiration date] date not null , 
    [delivery date] date not null , 
    state nvarchar(20) not null , 
    constraint valid_batch check ([expiration date] > [delivery date]) , 
    foreign key(FK_SupplierID) references Supplier(ID) on delete cascade
)
