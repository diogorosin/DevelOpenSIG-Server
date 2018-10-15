CREATE OR REPLACE FUNCTION ClearExpiredTokenJob() RETURNS void AS $$
BEGIN
   DELETE FROM "Token" WHERE expire < now();
END;
$$ LANGUAGE 'plpgsql';
