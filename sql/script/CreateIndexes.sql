CREATE UNIQUE INDEX "IndividualCpfIdx" ON "Individual"("cpf");
CREATE UNIQUE INDEX "IndividualRgIdx" ON "Individual"("rgNumber", "rgAgency", "rgState");
CREATE UNIQUE INDEX "OrganizationDenominationIdx" ON "Organization"("denomination");
CREATE UNIQUE INDEX "UserLoginIdx" ON "User"("login");