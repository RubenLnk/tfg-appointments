set search_path=appointments;

create table profesionales (
    profesional_id serial primary key,
    uid varchar(20) not null,
    password bytea not null,
    nombre varchar(30) not null,
    apellido1 varchar(30) not null,
    apellido2 varchar(30),
    fecha_creacion timestamp not null,
    fecha_modificacion timestamp not null
);

create table clientes (
    cliente_id serial primary key,
    nombre varchar(30) not null,
    apellido1 varchar(30) not null,
    apellido2 varchar(30),
    telefono varchar(30) unique not null,
    correo varchar(50) unique,
    password bytea not null,
    fecha_creacion timestamp not null,
    fecha_modificacion timestamp not null
);

create table servicios (
    servicio_id serial primary key,
    nombre varchar(30) not null,
    descripcion text,
    precio numeric(6,2) not null,
    duracion int not null,
    fecha_creacion timestamp not null,
    fecha_modificacion timestamp not null
);

create table citas (
    cita_id serial primary key,
    fecha_cita timestamp not null,
    precio numeric(6,2) not null,
    observaciones text,
    cliente_id int not null,
    profesional_creador int not null,
    activo bool not null default true,
    fecha_creacion timestamp not null,
    fecha_modificacion timestamp not null,
    fecha_anulacion timestamp,
    foreign key (cliente_id)
        references clientes (cliente_id),
    foreign key (profesional_creador)
        references profesionales (profesional_id)
);

create table servicio_cita (
    cita_id serial not null,
    servicio_id serial not null,
    primary key (cita_id, servicio_id),
    foreign key (cita_id)
        references citas (cita_id),
    foreign key (servicio_id)
        references servicios (servicio_id)
);
