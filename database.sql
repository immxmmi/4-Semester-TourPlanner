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
    "routeImage" text not null,
    distance     text not null,
    time         text not null
);

alter table tour
    owner to swe2user;

create unique index tour_tourid_uindex
    on tour ("tourId");

create table city
(
    "cityId" text not null
        constraint city_pk
            primary key
);

alter table city
    owner to swe2user;

create unique index city_cityid_uindex
    on city ("cityId");

create table image
(
    "imageId" text not null
        constraint image_pk
            primary key
);

alter table image
    owner to swe2user;

create unique index image_imageid_uindex
    on image ("imageId");

create table "tourLog"
(
    "tourLogId" text not null
        constraint tourlog_pk
            primary key
);

alter table "tourLog"
    owner to swe2user;

create unique index tourlog_tourlogid_uindex
    on "tourLog" ("tourLogId");

