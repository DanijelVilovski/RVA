drop table if exists nacionalnost cascade;
drop table if exists igrac cascade;
drop table if exists tim cascade;
drop table if exists liga cascade;
/*ukoliko brisemo neki podatak koji se nalazi u nekoj drugoj tabeli, nece nam dozvoliti,
zato koristimo CASCADE*/

drop sequence if exists nacionalnost_seq cascade;
drop sequence if exists igrac_seq cascade;
drop sequence if exists tim_seq cascade;
drop sequence if exists liga_seq cascade;
/*brisanje sekvenci ako postoje, da bismo bili sigurni da svaki put pocinje sa jedinicom*/

create table nacionalnost (
id integer not null,
naziv varchar(100),
skracenica varchar(50)
);
create table igrac (
id integer not null,
ime varchar(50),
prezime varchar(50),
broj_reg varchar(50),
datum_rodjenja date,
nacionalnost integer not null,
tim integer not null
);
Create table tim (
Id integer not null,
Naziv varchar(50),
Osnovan date,
Sediste varchar(100),
Liga integer not null
);
Create table liga(
Id integer not null,
Naziv varchar(100),
Oznaka varchar(50)
);

Alter table nacionalnost add constraint pk_nacionalnost primary key(id);
Alter table igrac add constraint pk_igrac primary key(id);
Alter table tim add constraint pk_tim primary key(id);
Alter table liga add constraint pk_liga primary key(id);
/*ogranicenja primarnog kljuca*/

Alter table tim add constraint fk_tim_liga foreign key(liga) references liga(id);
Alter table igrac add constraint fk_igrac_tim foreign key(tim) references tim(id);
Alter table igrac add constraint fk_igrac_nacionalnost foreign key(nacionalnost) references nacionalnost(id);
/*ogranicenja stranog kljuca*/

Create index idxpk_nacionalnost ON nacionalnost(id);
Create index idxpk_igrac ON igrac(id);
Create index idxpk_tim ON tim(id);
Create index idxpk_liga ON liga(id);
/*indexi za primarne kljuceve*/

Create index idxfk_tim_liga on tim(liga);
Create index idxfk_igrac_tim on igrac(tim);
Create index idxfk_igrac_nacionalnost on igrac(nacionalnost);
/*indexi za strane kljuceve*/

Create sequence if not exists nacionalnost_seq increment 1 start with 1;
Create sequence if not exists igrac_seq increment 1 start with 1;
Create sequence if not exists tim_seq increment 1 start with 1;
Create sequence if not exists liga_seq increment 1 start with 1;
/*dodavanje sekvenci ako ne postoje*/

-- da vise ne moramo da prosledjujemo id vrednost pri insertu, jer postavljamo default-nu vrednost
alter table nacionalnost alter column id set default nextval('nacionalnost_seq');
alter table igrac alter column id set default nextval('igrac_seq');
alter table tim alter column id set default nextval('tim_seq');
alter table liga alter column id set default nextval('liga_seq');
