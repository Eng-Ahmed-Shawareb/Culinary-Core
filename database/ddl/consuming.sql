
create table Consume(
    FK_BatchID int , 
    FK_WorkshopID int , 
    qantity int not null , 
    [consuming date] date not null

    constraint PK_Consume primary key (FK_BatchID , FK_WorkshopID) , 
    foreign key (FK_BatchID) references Ingredient_batch(ID) , 
    foreign key(FK_WorkshopID) references Workshop(ID)
)
