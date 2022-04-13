ALTER TABLE appointments.servicios
    ADD COLUMN activo bool NOT NULL DEFAULT true,
    ADD COLUMN fecha_anulacion TIMESTAMP;