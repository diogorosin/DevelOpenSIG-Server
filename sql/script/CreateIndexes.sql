CREATE UNIQUE INDEX "UserLoginIdx" ON "User"("login");
CREATE UNIQUE INDEX "OrganizationDenominationIdx" ON "Organization"("denomination");
CREATE UNIQUE INDEX "UserSecretIdx" ON "User"("secret");
CREATE UNIQUE INDEX "CityIBGEIdx" ON "City"("ibge");
CREATE UNIQUE INDEX "StateIBGEIdx" ON "State"("ibge");