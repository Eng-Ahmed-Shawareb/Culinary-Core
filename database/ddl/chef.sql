
create table chef(
    ID int identity(1 , 1) primary key , 
    [first name] nvarchar(50) , 
    [last name] nvarchar(50) , 
    bio nvarchar(250) , 
    expertise nvarchar(50) not null
)
