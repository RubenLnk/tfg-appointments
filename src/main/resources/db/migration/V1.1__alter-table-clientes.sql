ALTER TABLE appointments.clientes
    ADD COLUMN alta bool NOT NULL DEFAULT false,
    ADD COLUMN primera_conexion bool NOT NULL DEFAULT false;