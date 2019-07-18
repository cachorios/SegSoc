drop table if exists documento cascade;
drop table if exists movimiento cascade;
drop table if exists movimiento_detalle cascade;
drop table if exists parametro cascade;
drop table if exists persona cascade;
drop table if exists plan cascade;
drop table if exists producto cascade;
drop table if exists usuario cascade;
drop sequence hibernate_sequence;

create sequence hibernate_sequence start 1 increment 1;
create table documento (id int8 not null, version int4 not null, descripcion varchar(255) not null, tipo int4 not null, movimiento_detalle_id int8, persona_id int8, primary key (id));
create table movimiento (id int8 not null, version int4 not null, fecha timestamp not null, persona bytea not null, usuario bytea not null, primary key (id));
create table movimiento_detalle (id int8 not null, version int4 not null, producto_descripcion varchar(255), movimiento_id int8 not null, producto_id int8, primary key (id));
create table parametro (id int8 not null, version int4 not null, clase varchar(32) not null, nombre varchar(32) not null, orden int4 not null, tipo smallint default 0, valorbol boolean default false not null, valorchr char, valordat timestamp, valordob float8, valorint int8, valorstr varchar(255), primary key (id));
create table persona (id int8 not null, version int4 not null, cabeza_id bytea, descripcion_direccion varchar(255) not null, direccion varchar(255) not null, documento varchar(255) not null, factor bytea not null, grupo_sanguineo bytea not null, nombre varchar(255) not null, numero_partida int4 not null, parentesco bytea, sexo varchar(1) not null, primary key (id));
create table plan (id int8 not null, version int4 not null, monto float8 not null, nombre bytea not null, persona_id int8, primary key (id));
create table producto (id int8 not null, version int4 not null, descripcion varchar(255) not null, primary key (id));
create table usuario (id int8 not null, version int4 not null, apellido varchar(255), email varchar(255), foto_url varchar(2083), locked boolean not null, nombre varchar(255), password_hash varchar(255) not null, role varchar(255), primary key (id));
alter table if exists usuario add constraint UK_5171l57faosmj8myawaucatdw unique (email);
alter table if exists documento add constraint FKjovc11s82bv3n0n6kukg1x5sq foreign key (movimiento_detalle_id) references movimiento_detalle;
alter table if exists documento add constraint FKat001rbb01f5l1uwju9v85gd9 foreign key (persona_id) references persona;
alter table if exists movimiento_detalle add constraint FKqu4iv8vmju352or7esvdo5iym foreign key (movimiento_id) references movimiento;
alter table if exists movimiento_detalle add constraint FKpwsnvyf7ppe2r1c0iebg2jc0d foreign key (producto_id) references producto;
alter table if exists plan add constraint FKmdafk8jvfyeea4ww0eeu6pswu foreign key (persona_id) references persona;

----------------------------------------------------------
----------------------- PARAMETROS -----------------------
----------------------------------------------------------

INSERT INTO parametro (id, clase, nombre, orden, tipo, valorbol, valordat, valordob, valorint, version)
VALUES
  (1, 'GRUPO_SANGUINEO', 'A', 1, 2, FALSE, NULL,  0,  0, 1),
  (2, 'GRUPO_SANGUINEO', 'B', 2, 2, FALSE, NULL,  0,  0, 1),
  (3, 'GRUPO_SANGUINEO', 'AB', 3, 2, FALSE, NULL,  0,  0, 1),
  (4, 'GRUPO_SANGUINEO', '0', 4, 2, FALSE, NULL,  0,  0, 1),
  (5, 'FACTOR_RH', 'POSTIVO', 1, 1, FALSE, NULL,  0,  0, 1),
  (6, 'FACTOR_RH', 'NEGATIVO', 2, 1, FALSE, NULL,  0,  0, 1);