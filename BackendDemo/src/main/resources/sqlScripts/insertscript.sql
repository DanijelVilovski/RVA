/*Truncate table nacionalnost cascade;
Truncate table igrac cascade;
Truncate table tim cascade;
Truncate table liga cascade;*/

/*select * from liga
select * from tim
select * from nacionalnost
select * from igrac*/

Insert into liga(id, naziv, oznaka)
Values (-100, 'Test Liga', 'TEST');
Insert into nacionalnost (id, naziv, skracenica)
Values (-100, 'Test nacionalnost', 'TEST');
Insert into tim(id, naziv, osnovan, sediste, liga)
Values(-100, 'Test team', to_date('01.01.1990.', 'dd.mm.yyyy.'), 'Test', -100);
Insert into igrac(id, ime, prezime, broj_reg, datum_rodjenja, nacionalnost, tim)
Values (-100, 'Test ime', 'Test prezime', '000', to_date('01.01.1990.', 'dd.mm.yyyy.'), -100, -100);

Insert into liga(id, naziv, oznaka)
Values (nextval('liga_seq'), 'Premier League', 'PRL'),
(nextval('liga_seq'), 'League 1', 'LEA1'),
(nextval('liga_seq'), 'Bundesliga', 'BUL'),
(nextval('liga_seq'), 'LaLeague', 'LAL'),
(nextval('liga_seq'), 'Serie A', 'SERA');

Insert into tim(id, naziv, osnovan, sediste, liga)
 Values(nextval('tim_seq'), 'Paris Saint-Germain', to_date('24.06.1923.', 'dd.mm.yyyy.'), 'Paris', 2),
 (nextval('tim_seq'), 'Manchester United', to_date('19.04.1955.', 'dd.mm.yyyy.'), 'Manchester', 1),
 (nextval('tim_seq'), 'Barcelona', to_date('14.02.1933.', 'dd.mm.yyyy.'), 'Barcelona', 4),
 (nextval('tim_seq'), 'Bayern Munich', to_date('01.02.1899.', 'dd.mm.yyyy.'), 'Munich', 3),
 (nextval('tim_seq'), 'Real Madrid', to_date('30.11.1943.', 'dd.mm.yyyy.'), 'Madrid', 4),
 (nextval('tim_seq'), 'Liverpool', to_date('27.09.1943..', 'dd.mm.yyyy.'), 'Liverpool', 1),
 (nextval('tim_seq'), 'Juventus', to_date('03.12.1922.', 'dd.mm.yyyy.'), 'Turin', 5);

Insert into nacionalnost (id, naziv, skracenica)
Values (nextval('nacionalnost_seq'), 'Argentine', 'ARG'),
(nextval('nacionalnost_seq'), 'Portuguese', 'POR'),
(nextval('nacionalnost_seq'), 'Brazilian', 'BRA' ),
(nextval('nacionalnost_seq'), 'Polish', 'POL'),
(nextval('nacionalnost_seq'), 'French', 'FRA'),
(nextval('nacionalnost_seq'), 'Egyptian', 'EGY'),
(nextval('nacionalnost_seq'), 'Belgian', 'BEL'),
(nextval('nacionalnost_seq'), 'Serbian', 'SRB');

Insert into igrac(id, ime, prezime, broj_reg, datum_rodjenja, nacionalnost, tim)
Values (nextval('igrac_seq'), 'Lionel', 'Messi', '323', to_date('24.06.1987.', 'dd.mm.yyyy.'), 1, 1),
(nextval('igrac_seq'), 'Cristiano', 'Ronaldo', '433', to_date('24.06.1987.', 'dd.mm.yyyy.'), 2, 2),
(nextval('igrac_seq'), 'Ronaldinho', 'Gaucho', '653', to_date('24.06.1987.', 'dd.mm.yyyy.'), 3, 3),
(nextval('igrac_seq'), 'Robert', 'Lewandowski', '234', to_date('24.06.1987.', 'dd.mm.yyyy.'), 4, 4),
(nextval('igrac_seq'), 'Eden', 'Hazard', '861', to_date('24.06.1987.', 'dd.mm.yyyy.'), 7, 5),
(nextval('igrac_seq'), 'Kylian', 'Mbappe', '753', to_date('24.06.1987.', 'dd.mm.yyyy.'), 5, 1),
(nextval('igrac_seq'), 'Mohamed', 'Salah', '912', to_date('24.06.1987.', 'dd.mm.yyyy.'), 6, 6),
(nextval('igrac_seq'), 'Dusan', 'Vlahovic', '22', to_date('24.06.1987.', 'dd.mm.yyyy.'), 8, 7);

