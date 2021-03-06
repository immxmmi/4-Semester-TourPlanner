create table tour
(
    "tourID"     text not null
        constraint tour_pk
            primary key,
    title        text not null,
    transporter  text not null,
    "from"       text not null,
    "to"         text not null,
    description  text not null,
    "routeImage" text not null,
    distance     text not null,
    time         text not null,
    date         text
);


create unique index tour_tourid_uindex
    on tour ("tourID");

create table "tourLog"
(
    "tourLogID" text not null
        constraint tourlog_pk
            primary key,
    "tourID"    text not null,
    comment     text,
    "totalTime" text not null,
    level       text not null,
    stars       text not null,
    date        text not null
);



create unique index tourlog_tourlogid_uindex
    on "tourLog" ("tourLogID");

create table image
(
    "imageID"       text not null
        constraint image_pk
            primary key,
    width           text not null,
    height          text not null,
    zoom            text,
    "from"          text not null,
    "to"            text not null,
    "downloadURL"   text,
    local           text not null,
    "defaultMarker" text not null,
    "filePath"      text,
    data            bytea
);



create unique index image_imagename_uindex
    on image ("imageID");


