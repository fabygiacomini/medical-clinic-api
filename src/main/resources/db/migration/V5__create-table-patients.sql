CREATE TABLE patients (
    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(20) not null,
    cpf varchar(20) not null,
    street varchar(100) not null,
    number varchar(20),
    zip varchar(9) not null,
    state varchar(2) not null,
    city varchar(100) not null,
    complement varchar(100),

    primary key(id)
);