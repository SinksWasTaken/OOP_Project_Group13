create database Group13;

use group13;
CREATE TABLE images (
	image_id INT auto_increment primary key,
    paths varchar (50) NOT NULL
);

Insert into images (paths)
Values
	("Images\cars poster.webp"),
    ('Images\cars 2 poster.webp'),
    ('Images\toyStory poster.webp'),
    ("Images\toyStory 2 poster.webp"),
    ("Images\aladdin.webp"),
    ("Images\piratesOfTheCaribbean 1 poster.webp"),
    ("Images\piratesOfTheCaribbean 2 poster.webp"),
    ("Images\piratesOfTheCaribbean 3 poster.webp"),
    ("Images\piratesOfTheCaribbean 4 poster.webp"),
    ("Images\piratesOfTheCaribbean 5 poster.webp");

drop table movies;
create table movies (
	movie_id INT auto_increment primary key,
    movie_name varchar (50),
    img_path varchar (50)
);

INSERT INTO movies(movie_name)
VALUES
    ("Pirates of the Caribbean 1"),
    ("Pirates of the Caribbean 2"),
    ("Pirates of the Caribbean 3"),
    ("Pirates of the Caribbean 4"),
    ("Pirates of the Caribbean 5"),
    ("Cars"),
    ("Cars2"),
    ("ToyStory"),
    ("ToyStory2"),
    ("Aladdin");

drop table seats;
create table seats (
	seat_id int auto_increment primary key,
    sold boolean,
    hall int,
    c int,
    r varchar(1),
    session_id int,
    foreign key (session_id) references sessions(session_id)
);

insert into seats(sold,hall,c,r,session_id)
values(false,1,1,'c',2);

drop table sessions;
create table sessions(
	session_id int auto_increment primary key,
    session_date date not null,
    session_time time not null,
    hall_number int not null,
    movie_name varchar (50)
);




DROP PROCEDURE IF EXISTS fillSeats;

DELIMITER $$
CREATE PROCEDURE fillSeats(s_id int)
BEGIN
    DECLARE i INT DEFAULT 1;
    Declare hall INT default (select hall_number from sessions where s_id= session_id);
    loop_label: LOOP
        

        -- Do something in the loop
        Insert into seats(sold,hall,c,r,session_id)
        values(false,hall,if(i%8=0,8,i%8),if(i>8,'b','a'),s_id);

        SET i = i + 1; 
        IF i > if(hall=1,16,48)
		THEN
			LEAVE loop_label; 
		END IF;
    END LOOP loop_label;

END$$
DELIMITER ;


DROP PROCEDURE IF EXISTS addSession;

DELIMITER $$
CREATE PROCEDURE addSession(sdate date,stime time,shall int)
Begin
	insert into sessions(session_date,session_time,hall_number)
    values(sdate,stime,shall);

End$$
DELIMITER ;

CALL fillSeats(1);
CALL addsession('1999-12-09','7:15',2);


select * from seats;
select * from sessions;

UPDATE movies
join Images ON movies.movie_id= Images.image_id
set movies.img_path = Images.paths;

UPDATE sessions s
join movies m ON if(s.hall_Number=1,if((s.session_id)%10=0,10,s.session_id%10),if(((s.session_id%10)+5)=5,5,(s.session_id+5)%10))= m.movie_id
set s.movie_name = m.movie_name;


SELECT * FROM movies;
