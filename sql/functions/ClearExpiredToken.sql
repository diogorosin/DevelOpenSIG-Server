CREATE OR REPLACE FUNCTION ClearExpiredToken() 
RETURNS VOID AS $$
BEGIN
	
   DELETE FROM "Token" WHERE expire < now();
   
END;
$$ LANGUAGE 'plpgsql';