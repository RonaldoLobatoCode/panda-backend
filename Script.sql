CREATE DATABASE coursesDb;
select *from courses 
create database productosspring

create database panda;

CREATE TYPE genero_enum AS ENUM ('Masculino', 'Femenino', 'Otro');
CREATE TYPE estado_civil_enum AS ENUM ('Soltero/a', 'Casado/a', 'Divorciado/a', 'Viudo/a');
CREATE TYPE nacionalidades_enum AS ENUM ('Argentina', 'Brasileña', 'Chilena', 'Colombiana', 'Mexicana', 'Peruana', 'Española', 'Estadounidense', 'Otra');
CREATE TYPE cargo_enum AS ENUM (
   'Conductor de Camión',
   'Coordinador de Logística',
   'Mecánico de Vehículos',
   'Gerente de Flota',
   'Especialista en Seguridad del Transporte',
   'Representante de Servicio al Cliente',
   'Ingeniero de Sistemas',
   'Gerente de Recursos Humanos',
   'Representante de Ventas',
   'CEO (Director Ejecutivo)'
);
CREATE TYPE tipo_licencia_enum AS ENUM (
   'A3',
   'A4',
   'A5',
   'B1',
   'B2',
   'B3',
   'B4',
   'C1',
   'C2',
   'C3',
   'C4',
   'D1',
   'D2',
   'D3',
   'D4'
);

CREATE TABLE IF NOT EXISTS generos (
    id SERIAL PRIMARY KEY,
    nombre_genero genero_enum NOT NULL
);


CREATE TABLE IF NOT EXISTS estados_civiles (
    id SERIAL PRIMARY KEY,
    nombre_est_civil estado_civil_enum NOT NULL
);


CREATE TABLE IF NOT EXISTS nacionalidades (
    id SERIAL PRIMARY KEY,
    nombre_nacionalidad nacionalidades_enum NOT null
);


  
CREATE TABLE IF NOT EXISTS cargos (
    id SERIAL PRIMARY KEY,
    nombre_cargo cargo_enum NOT null
);
INSERT INTO generos (nombre_genero) VALUES
   ('Masculino'),
   ('Femenino'),
   ('Otro');

-- Insertar valores en la tabla de estados civiles
INSERT INTO estados_civiles (nombre_est_civil) VALUES
   ('Soltero/a'),
   ('Casado/a'),
   ('Divorciado/a'),
   ('Viudo/a');

-- Insertar valores en la tabla de nacionalidades
INSERT INTO nacionalidades (nombre_nacionalidad) VALUES
   ('Argentina'),
   ('Brasileña'),
   ('Chilena'),
   ('Colombiana'),
   ('Mexicana'),
   ('Peruana'),
   ('Española'),
   ('Estadounidense'),
   ('Otra');

-- Insertar valores en la tabla de cargos
INSERT INTO cargos (nombre_cargo) VALUES
   ('Conductor de Camión'),
   ('Coordinador de Logística'),
   ('Mecánico de Vehículos'),
   ('Gerente de Flota'),
   ('Especialista en Seguridad del Transporte'),
   ('Representante de Servicio al Cliente'),
   ('Ingeniero de Sistemas'),
   ('Gerente de Recursos Humanos'),
   ('Representante de Ventas'),
   ('CEO (Director Ejecutivo)');
 INSERT INTO tipos_licencia (tipo_licencia) VALUES
   ('A3'),
   ('A4'),
   ('A5'),
   ('B1'),
   ('B2'),
   ('B3'),
   ('B4'),
   ('C1'),
   ('C2'),
   ('C3'),
   ('C4'),
   ('D1'),
   ('D2'),
   ('D3'),
   ('D4');
CREATE TABLE IF NOT EXISTS trabajadores (
    id SERIAL PRIMARY KEY,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    num_identidad VARCHAR(20) UNIQUE NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    id_genero INT,
    estado_civil_id INT,
    nacionalidad_id INT,
    direccion_residencia VARCHAR(255),
    telefono VARCHAR(15),
    email VARCHAR(255),
    cargo_id INT,
    fecha_ingreso DATE,
    num_cuenta_bancaria VARCHAR(14),
    CONSTRAINT uq_trab_num_iden UNIQUE (num_identidad),
    constraint uq_trab_email unique (email),
    constraint uq_num_cuenta_banc unique (num_cuenta_bancaria),
    CONSTRAINT fk_trab_genero FOREIGN KEY (id_genero) REFERENCES generos(id) ON DELETE SET NULL,
    CONSTRAINT fk_trab_est_civil FOREIGN KEY (estado_civil_id) REFERENCES estados_civiles(id) ON DELETE SET NULL,
    CONSTRAINT fk_trab_nacionalidad FOREIGN KEY (nacionalidad_id) REFERENCES nacionalidades(id) ON DELETE SET NULL,
    CONSTRAINT fk_trab_cargo_puesto FOREIGN KEY (cargo_id) REFERENCES cargos(id) ON DELETE SET NULL
);


  
-- Crear la tabla de carretas
CREATE TABLE IF NOT EXISTS carretas (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    capacidad_carga DECIMAL(15, 2) NOT NULL,
    año_fabricacion INT NOT NULL
);

CREATE TABLE IF NOT EXISTS camiones (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    año_fabricacion INT NOT NULL,
    placa VARCHAR(15) NOT NULL,
    carreta_id INT,
    constraint uq_cam_placa unique (placa),
    constraint uq_cam_carreta_id unique (carreta_id),
    CONSTRAINT fk_camiones_carreta FOREIGN KEY (carreta_id) REFERENCES carretas(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS tipos_licencia (
    id SERIAL PRIMARY KEY,
    tipo_licencia tipo_licencia_enum NOT null
);

  
CREATE TABLE IF NOT EXISTS conductores_camion (
    id SERIAL PRIMARY KEY,
    trabajador_id INT,
    tipo_licencia_id INT,
    camion_id INT,
    cert_conducir_camion BOOLEAN,
    cert_psicofisico BOOLEAN,
    cert_mecanica_basica BOOLEAN,
    cert_primeros_auxilios BOOLEAN,
    cert_seguridad_vial BOOLEAN,
    constraint uq_conduct_trab_id unique (trabajador_id),
    constraint uq_conduct_licencia unique (tipo_licencia_id),
    constraint uq_conduct_camion_id unique (camion_id),
    
    CONSTRAINT fk_cond_trabajador FOREIGN KEY (trabajador_id) REFERENCES trabajadores(id) ON DELETE CASCADE,
    CONSTRAINT fk_cond_tipo_licencia FOREIGN KEY (tipo_licencia_id) REFERENCES tipos_licencia(id) ON DELETE SET NULL,
    CONSTRAINT fk_cond_camion FOREIGN KEY (camion_id) REFERENCES camiones(id) ON DELETE SET NULL
);




CREATE TABLE IF NOT EXISTS citas_viaje (
    id SERIAL PRIMARY KEY,
    fecha_cita TIMESTAMP NOT NULL,
    origen VARCHAR(100) NOT NULL,
    destino VARCHAR(100) NOT NULL,
    conductor_id INT,
    CONSTRAINT fk_cit_conduc_cam FOREIGN KEY (conductor_id) REFERENCES conductores_camion(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS guias_transportista (
    id SERIAL PRIMARY KEY,
    serie_guia VARCHAR(10) NOT NULL,
    numero_guia INT NOT NULL,
    partida VARCHAR(100) NOT NULL,
    llegada VARCHAR(100) NOT NULL,
    fecha_hora_emision TIMESTAMP NOT NULL,
    fecha_traslado DATE NOT NULL,
    remitente_ruc VARCHAR(15) not null,
    remitente_nombre VARCHAR(100) NOT NULL,
    remitente_direccion VARCHAR(100),
    remitente_telefono VARCHAR(15),
    destinatario_ruc VARCHAR(15) not null,
    destinatario_nombre VARCHAR(100) NOT NULL,
    destinatario_direccion VARCHAR(100),
    destinatario_telefono VARCHAR(15),
    peso_carga DECIMAL(10, 2) NOT NULL,
    num_doc_chofer VARCHAR(20) NOT NULL,
    nombre_chofer VARCHAR(50) NOT NULL,
    placa_vehiculo VARCHAR(15) NOT NULL,
    ruc_pagador_del_flete VARCHAR(15) not null
    
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(15, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS facturas (
    id SERIAL PRIMARY KEY,
    serie_guia VARCHAR(10) NOT NULL,
    numero_factura INT NOT NULL,
    fecha_emision TIMESTAMP NOT NULL,
    monto_total DECIMAL(15, 2) NOT NULL,
    cliente_nombre VARCHAR(255) NOT NULL,
    cliente_direccion VARCHAR(255),
    cliente_telefono VARCHAR(15),
    guia_id INT,
    item_id int,
    foreign key (item_id) references items(id) ON DELETE CASCADE,
    CONSTRAINT fk_fact_GT FOREIGN KEY (guia_id) REFERENCES guias_transportista(id) ON DELETE SET NULL
);
CREATE INDEX idx_trab_genero ON trabajadores (id_genero);
CREATE INDEX idx_trab_est_civil ON trabajadores (estado_civil_id);
CREATE INDEX idx_trab_nacionalidad ON trabajadores (nacionalidad_id);
CREATE INDEX idx_trab_cargo_puesto ON trabajadores (cargo_puesto_id);

select *from generos;

INSERT INTO trabajadores (nombres, apellidos, num_identidad, fecha_nacimiento, id_genero, estado_civil_id, nacionalidad_id, direccion_residencia, telefono, email, cargo_puesto_id, fecha_ingreso, num_cuenta_bancaria)
VALUES
    ('Juan', 'Pérez', '123456789', '1990-05-15', 1, 1, 1, 'Calle 123', '123456789', 'juan.perez@example.com', 1, '2022-01-01', '12345678901234567890'),
    ('María', 'Gómez', '987654321', '1985-08-20', 2, 2, 2, 'Avenida XYZ', '987654321', 'maria.gomez@example.com', 2, '2022-02-15', '09876543210987654321'),
    ('Pedro', 'López', '456789012', '1988-12-10', 1, 3, 1, 'Calle Principal', '456789012', 'pedro.lopez@example.com', 3, '2022-03-20', '1111222233334444'),
    ('Ana', 'Martínez', '789012345', '1992-03-25', 2, 2, 3, 'Carrera 567', '789012345', 'ana.martinez@example.com', 2, '2022-04-05', '5555666677778888'),
    ('Carlos', 'Rodríguez', '234567890', '1980-06-18', 1, 1, 2, 'Avenida Principal', '234567890', 'carlos.rodriguez@example.com', 4, '2022-05-10', '9999000011112222');
   
INSERT INTO carretas (marca, capacidad_carga, año_fabricacion)
VALUES
    ('Carreta1', 20000.50, 2020),
    ('Carreta2', 25000.75, 2019),
    ('Carreta3', 18000.25, 2021),
    ('Carreta4', 22000.30, 2018),
    ('Carreta5', 19000.45, 2022);
   
 INSERT INTO camiones (marca, modelo, año_fabricacion, placa, carreta_id)
VALUES
    ('Marca1', 'Modelo1', 2019, 'ABC123', 6),
    ('Marca2', 'Modelo2', 2020, 'DEF456', 7),
    ('Marca3', 'Modelo3', 2018, 'GHI789', 8),
    ('Marca4', 'Modelo4', 2021, 'JKL012', 9),
    ('Marca5', 'Modelo5', 2017, 'MNO345', 10);
   
   
 INSERT INTO conductores_camion (trabajador_id, tipo_licencia_id, camion_id, cert_conducir_camion, cert_psicofisico, cert_mecanica_basica, cert_primeros_auxilios, cert_seguridad_vial)
values
	(11, 1, 11, true, true, false, true, true),
    (12, 2, 12, true, true, false, true, true),
    (13, 3, 13, true, false, true, false, true),
    (14, 4, 14, false, true, true, true, true),
    (15, 5, 15, true, true, true, true, false);
   
INSERT INTO citas_viaje (fecha_cita, origen, destino, conductor_id)
VALUES
    ('2024-02-10 10:30:00', 'Origen2', 'Destino2', 16),
    ('2024-03-15 12:45:00', 'Origen3', 'Destino3', 17),
    ('2024-04-20 14:00:00', 'Origen4', 'Destino4', 18),
    ('2024-05-25 16:30:00', 'Origen5', 'Destino5', 19);

delete from conductores_camion where id=4;
select *from trabajadores;
select *from conductores_camion;
delete from 
delete from trabajadores where id=11
select *from citas_viaje; 
select *from carretas;
select *from camiones;
