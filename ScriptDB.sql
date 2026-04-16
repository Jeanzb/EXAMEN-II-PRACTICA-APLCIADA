CREATE DATABASE IF NOT EXISTS examen2_zambrano;
USE examen2_zambrano;

CREATE TABLE IF NOT EXISTS propietario (
                                           id VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS inmueble (
                                        numero VARCHAR(20) NOT NULL PRIMARY KEY,
    fecha_compra VARCHAR(20) NOT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    propietario_id VARCHAR(20) NOT NULL,
    CONSTRAINT fk_inmueble_propietario
    FOREIGN KEY (propietario_id) REFERENCES propietario(id)
    );

CREATE TABLE IF NOT EXISTS apartamento (
                                           numero VARCHAR(20) NOT NULL PRIMARY KEY,
    numero_piso INT NOT NULL,
    CONSTRAINT fk_apartamento_inmueble
    FOREIGN KEY (numero) REFERENCES inmueble(numero)
    );

CREATE TABLE IF NOT EXISTS casa (
                                    numero VARCHAR(20) NOT NULL PRIMARY KEY,
    cantidad_pisos INT NOT NULL,
    CONSTRAINT fk_casa_inmueble
    FOREIGN KEY (numero) REFERENCES inmueble(numero)
    );


