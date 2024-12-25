

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

drop table sessionsh1;
create table sessionsh1 (
	session_id int primary key auto_increment,
	day_number int not null,
    session_number int not null,
    movie varchar (50),
    hall int
);

Insert into sessionsh1 (day_number,session_number)
Values
	(1,1),(1,2),(1,3),(1,4),(1,5),
	(2,1),(2,2),(2,3),(2,4),(2,5),
    (3,1),(3,2),(3,3),(3,4),(3,5),
	(4,1),(4,2),(4,3),(4,4),(4,5),
    (5,1),(5,2),(5,3),(5,4),(5,5),
    (6,1),(6,2),(6,3),(6,4),(6,5),
    (7,1),(7,2),(7,3),(7,4),(7,5);


drop table sessionsh2;
create table sessionsh2 (
	session_id int primary key auto_increment,
	day_number int not null,
    session_number int not null,
    movie varchar (50),
    hall int
);

Insert into sessionsh2 (day_number,session_number)
Values
	(1,1),(1,2),(1,3),(1,4),(1,5),
	(2,1),(2,2),(2,3),(2,4),(2,5),
    (3,1),(3,2),(3,3),(3,4),(3,5),
	(4,1),(4,2),(4,3),(4,4),(4,5),
    (5,1),(5,2),(5,3),(5,4),(5,5),
    (6,1),(6,2),(6,3),(6,4),(6,5),
    (7,1),(7,2),(7,3),(7,4),(7,5);

drop table seats;
create table seats (
	seat_id int auto_increment primary key,
    sold boolean,
    hall int,
    c int,
    r varchar(1),
    session_number int,
    day_number int
);

DROP PROCEDURE IF EXISTS loop_h1;

DELIMITER $$

CREATE PROCEDURE loop_h1()
BEGIN
    DECLARE i INT DEFAULT 1;
	DECLARE day_number INT DEFAULT 1;
    DECLARE session_number INT DEFAULT 1;
    loop_label: LOOP
        IF day_number > 7 THEN
            LEAVE loop_label; -- Exit the loop
        END IF;

        -- Do something in the loop
        Insert into seats(sold,hall,c,r,session_number,day_number)
        values(false,1,if(i%8=0,8,i%8),if(i>8,'b','a'),session_number,day_number);

        SET i = i + 1; 
        IF i>16 then
			Set i=1;
            Set session_number = session_number+1;
            IF session_number > 5 then
				Set session_number = 1;
                Set day_number = day_number+1;
            END IF;
		END IF;
    END LOOP loop_label;

END$$

DELIMITER ;

CALL loop_h1();

select * from seats;

UPDATE movies
join Images ON movies.movie_id= Images.image_id
set movies.img_path = Images.paths;

UPDATE sessionsh1 s
join movies m ON if((s.session_id)%10=0,10,s.session_id%10)= m.movie_id
set s.movie = m.movie_name;

UPDATE sessionsh2 s
join movies m ON if(((s.session_id%10)+5)=5,5,(s.session_id+5)%10) = m.movie_id
set s.movie = m.movie_name;

SELECT * FROM movies;
Select * from sessionsh1;
Select * from sessionsh2;
