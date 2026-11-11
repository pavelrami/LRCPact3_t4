-- Ejecutar como administrador de MySQL. Sustituye la clave antes de usarlo.
CREATE DATABASE IF NOT EXISTS lrcp_act3 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'lrcp_act3'@'localhost' IDENTIFIED BY 'CAMBIA_POR_UNA_CLAVE_SEGURA';
CREATE USER IF NOT EXISTS 'lrcp_act3'@'127.0.0.1' IDENTIFIED BY 'CAMBIA_POR_UNA_CLAVE_SEGURA';
GRANT ALL PRIVILEGES ON lrcp_act3.* TO 'lrcp_act3'@'localhost';
GRANT ALL PRIVILEGES ON lrcp_act3.* TO 'lrcp_act3'@'127.0.0.1';
