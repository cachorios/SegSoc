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
create table documento (id int8 not null, version int4 not null, descripcion varchar(255) not null, nombre_archivo varchar(255) not null, tipo int4 not null, movimiento_detalle_id int8, persona_id int8, primary key (id))
create table movimiento (id int8 not null, version int4 not null, fecha timestamp not null, persona_id int8 not null, usuario_id int8 not null, primary key (id));
create table movimiento_detalle (id int8 not null, version int4 not null, producto_descripcion varchar(255), movimiento_id int8 not null, producto_id int8, primary key (id));
create table parametro (id int8 not null, version int4 not null, clase varchar(32) not null, nombre varchar(32) not null, orden int4 not null, tipo smallint default 0, valorbol boolean default false not null, valorchr char, valordat timestamp, valordob float8, valorint int8, valorstr varchar(255), primary key (id));
create table persona (id int8 not null, version int4 not null, cabeza_id int8, descripcion_direccion varchar(255) not null, direccion varchar(255) not null, documento varchar(255) not null, factor int4 not null, grupo_sanguineo int4 not null, nombre varchar(255) not null, numero_partida int4 not null, parentesco int4, sexo int4, primary key (id));
create table plan (id int8 not null, version int4 not null, monto float8 not null, nombre_id int8 not null, persona_id int8, primary key (id));
create table producto (id int8 not null, version int4 not null, descripcion varchar(255) not null, primary key (id));
create table usuario (id int8 not null, version int4 not null, apellido varchar(255), email varchar(255), foto_url varchar(2083), locked boolean not null, nombre varchar(255), password_hash varchar(255) not null, role varchar(255), primary key (id));
alter table if exists usuario add constraint uk_usuario_email unique (email);
alter table if exists documento add constraint fk_documento_movimientodetalle foreign key (movimiento_detalle_id) references movimiento_detalle;
alter table if exists documento add constraint fk_documento_persona foreign key (persona_id) references persona;
alter table if exists movimiento add constraint fk_movimiento_persona foreign key (persona_id) references persona;
alter table if exists movimiento add constraint fk_movimiento_usuario foreign key (usuario_id) references usuario;
alter table if exists movimiento_detalle add constraint fk_movimientodetalle_movimiento foreign key (movimiento_id) references movimiento;
alter table if exists movimiento_detalle add constraint fk_movimientodetalle_producto foreign key (producto_id) references producto;
alter table if exists plan add constraint fk_plan_persona foreign key (persona_id) references persona;
alter table if exists plan add constraint fk_plan_nombre foreign key (nombre_id) references parametro;