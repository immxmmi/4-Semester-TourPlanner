create table tour
(
    "tourId"     text not null
        constraint tour_pk
            primary key,
    name         text not null,
    transporter  text not null,
    "from"       text not null,
    "to"         text not null,
    description  text not null,
    "routeImage" text not null
);

alter table tour
    owner to swe2user;

create unique index tour_tourid_uindex
    on tour ("tourId");


