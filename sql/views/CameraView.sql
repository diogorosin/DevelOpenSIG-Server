/* CALCULO DE HORAS SEM COMUNICACAO
 * 
 * (ABS(EXTRACT(EPOCH from NOW() - (SELECT MAX("T"."receivedAt") FROM "Track" "T" WHERE "T"."camera" = "C"."identifier"))/3600) < 1) AS "active" 
 * */
CREATE OR REPLACE VIEW "CameraView" AS
SELECT
    "C".*,
    (
     SELECT MAX("T"."receivedAt") 
     FROM "Track" "T" 
     WHERE "T"."camera" = "C"."identifier"
    ) AS "lastReceiveAt"
FROM
    "Camera" "C"
ORDER BY
    "C"."identifier";