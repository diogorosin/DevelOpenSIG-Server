CREATE TABLE "Token" (

    "identifier" VARCHAR(64) NOT NULL,

    "user" INTEGER,

    "expire" TIMESTAMP,

    "note" TEXT

);