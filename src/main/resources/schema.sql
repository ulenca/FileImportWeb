create TABLE if not exists Person (

	id identity,
	first_name varchar(50) not null,
	last_name varchar(50) not null,
	date_of_birth date not null,
	phone int(9) unique 
	
);