--RAISE NOTICE 'MESSAGE';

CREATE OR REPLACE FUNCTION ImportModifiedAddress() 
RETURNS VOID AS $$
DECLARE

	v_subject INTEGER;
	v_address INTEGER;
	v_edification INTEGER;
	v_dweller INTEGER;
	v_transition TIMESTAMP;
	
	ma RECORD;
	mae RECORD;
	maed RECORD;

BEGIN

	--BUSCA TODA AS MODIFICACOES DE ENDERECOS
	--QUE NAO POSSUEM DATA/HORARIO DE IMPORTACAO
	FOR ma IN
	SELECT *
	FROM "ModifiedAddress" "MA"
	WHERE "MA"."importedAt" IS NULL
	LOOP

		IF (ma."address" IS NULL) THEN

			--CRIA O NOVO ENDERECO
			INSERT INTO "Address" ("denomination", "number", "reference", "district", "postalCode", "city", "latitude", "longitude", "verifiedBy", "verifiedAt")
			VALUES (ma."denomination", ma."number", ma."reference", ma."district", ma."postalCode", ma."city", ma."latitude", ma."longitude", ma."modifiedBy", NOW())
			RETURNING "identifier" INTO v_address;

			--PERCORRE AS EDIFICACOES DO NOVO ENDERECO
			FOR mae IN
			SELECT * 
			FROM "ModifiedAddressEdification" "MAE"
			WHERE "MAE"."modifiedAddress" = ma."identifier"
			LOOP

				--BUSCA O NUMERO DA ULTIMA EDIFICACAO
				SELECT COALESCE(MAX("AE"."edification"), 0) 
				INTO v_edification
				FROM "AddressEdification" "AE" 
				WHERE "AE"."address" = v_address; 

				--ACRESCENTA MAIS UM
				v_edification := v_edification + 1;

				--INSERE A EDIFICACAO
				INSERT INTO "AddressEdification" ("address", "edification", "type", "reference", "from", "to")
				VALUES (v_address, v_edification, mae."type", mae."reference", NOW(), NULL);

				--PERCORRE OS MORADORES DA EDIFICACAO
				FOR maed IN
				SELECT *
				FROM "ModifiedAddressEdificationDweller" "MAED"
				WHERE "MAED"."modifiedAddress" = mae."modifiedAddress" AND "MAED"."edification" = mae."edification"
				LOOP

					--VERIFICA SE FOR PESSOA FISICA
					IF (maed."type" = 'F') THEN

						--VERIFICA SE POSSUI VINCULO A DETERMINADO INDIVIDUO
						IF(maed."subject" IS NULL) THEN

							--BUSCA INDIVIDUO PELOS DOCUMENTOS
							SELECT FindIndividualByDocuments(maed."cpf", maed."rgNumber", maed."rgAgency", maed."rgState") 
							INTO v_subject;

							--SE NAO LOCALIZOU O INDIVIDUO, REALIZA A INCLUSAO NO BANCO DE DADOS
							IF (v_subject IS NULL) THEN

								INSERT INTO "Subject" ("active")
								VALUES (TRUE)
								RETURNING "identifier" INTO v_subject;

								INSERT INTO "Individual" ("subject", "name", "motherName", "fatherName", "cpf", "rgNumber", "rgAgency", "rgState", "birthPlace", "birthDate", "gender")
								VALUES (v_subject, maed."nameOrDenomination", maed."motherName", maed."fatherName", maed."cpf", maed."rgNumber", maed."rgAgency", maed."rgState", maed."birthPlace", maed."birthDate", maed."gender");

							END IF;

						ELSE

							v_subject := maed."subject";

						END IF;

						--DEFINE A DATA/HORA DA TRANSICAO
						v_transition = NOW();

						--DEFINE A DATA DE SAIDA DA ANTIGA MORADIA
						UPDATE "AddressEdificationDweller" 
						SET "to" = v_transition
						WHERE "to" IS NULL AND "subject" = v_subject;

						--BUSCA O NUMERO DA ULTIMA EDIFICACAO
						SELECT COALESCE(MAX("AED"."dweller"), 0) 
						INTO v_dweller
						FROM "AddressEdificationDweller" "AED" 
						WHERE "AED"."address" = v_address AND "AED"."edification" = v_edification; 

						--ACRESCENTA MAIS UM
						v_dweller := v_dweller + 1;

						--INSERE NOVO MORADOR
						INSERT INTO "AddressEdificationDweller" ("address", "edification", "dweller", "subject", "from", "to") 
						VALUES (v_address, v_edification, v_dweller, v_subject, v_transition, null);

					END IF;

				END LOOP;

			END LOOP;

		/* ELSE

			UPDATE "Address" SET 
			"denomination" = ma."denomination", 
			"number" = ma."number", 
 			"reference" = ma."reference", 
			"district" = ma."district", 
			"postalCode" = ma."postalCode", 
			"city" = ma."city", 
			"latitude" = ma."latitude", 
			"longitude" = ma."longitude"; */

		END IF;

		--DEFINE A MODIFICACAO DO ENDERECO 
		--COMO IMPORTADA
		UPDATE "ModifiedAddress"
		SET "importedAt" = NOW()
		WHERE "identifier" = ma."identifier";

	END LOOP;

END;
$$ LANGUAGE 'plpgsql';