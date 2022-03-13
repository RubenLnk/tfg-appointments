CREATE OR REPLACE FUNCTION appointments.update_fecha_modificacion()
    RETURNS trigger
    LANGUAGE plpgsql
AS $$
begin
    if new.fecha_modificacion = old.fecha_modificacion then
        new.fecha_modificacion = now();
    end if;
    return new;
end;
$$
;

CREATE TRIGGER clientes_update
    BEFORE UPDATE ON appointments.clientes
    FOR EACH ROW EXECUTE PROCEDURE update_fecha_modificacion();

CREATE TRIGGER profesionales_update
    BEFORE UPDATE ON appointments.profesionales
    FOR EACH ROW EXECUTE PROCEDURE update_fecha_modificacion();

CREATE TRIGGER servicios_update
    BEFORE UPDATE ON appointments.servicios
    FOR EACH ROW EXECUTE PROCEDURE update_fecha_modificacion();

CREATE TRIGGER citas_update
    BEFORE UPDATE ON appointments.citas
    FOR EACH ROW EXECUTE PROCEDURE update_fecha_modificacion();