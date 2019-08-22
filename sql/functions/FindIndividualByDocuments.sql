CREATE OR REPLACE FUNCTION FindIndividualByDocuments(i_cpf BIGINT, i_rgNumber INTEGER, i_rgAgency INTEGER, i_rgState INTEGER) 
RETURNS INTEGER AS $$
DECLARE

	v_identifier INTEGER;

BEGIN

	--BUSCA O INDIVIDUO PELO CPF
	SELECT "subject" INTO v_identifier 
	FROM "Individual" "I" 
	WHERE "I"."cpf" = i_cpf;

	--SE NAO ENCONTROU BUSCA PELO RG
	IF (v_identifier IS NULL) THEN

		SELECT "subject" INTO v_identifier
		FROM "Individual" "I"
		WHERE "I"."rgNumber" = i_rgNumber AND "I"."rgAgency" = i_rgAgency AND "I"."rgState" = i_rgState;

	END IF;
	
	RETURN v_identifier;

END;
$$ LANGUAGE 'plpgsql';