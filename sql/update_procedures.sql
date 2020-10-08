DROP PROCEDURE Create IF EXISTS;
load classes stored_procedures/Create/Create.jar;
CREATE PROCEDURE FROM CLASS Create;

DROP PROCEDURE Read IF EXISTS;
load classes stored_procedures/Read/Read.jar;
CREATE PROCEDURE FROM CLASS Read;

DROP PROCEDURE Write IF EXISTS;
load classes stored_procedures/Write/Write.jar;
CREATE PROCEDURE FROM CLASS Write;

DROP PROCEDURE Overwrite IF EXISTS;
load classes stored_procedures/Overwrite/Overwrite.jar;
CREATE PROCEDURE FROM CLASS Overwrite;

DROP PROCEDURE Populate IF EXISTS;
load classes stored_procedures/Populate/Populate.jar;
CREATE PROCEDURE FROM CLASS Populate;

