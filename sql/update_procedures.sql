DROP PROCEDURE CreateFile IF EXISTS;
load classes stored_procedures/CreateFile/CreateFile.jar;
CREATE PROCEDURE FROM CLASS CreateFile;

DROP PROCEDURE ReadFile IF EXISTS;
load classes stored_procedures/ReadFile/ReadFile.jar;
CREATE PROCEDURE FROM CLASS ReadFile;

DROP PROCEDURE WriteFile IF EXISTS;
load classes stored_procedures/WriteFile/WriteFile.jar;
CREATE PROCEDURE FROM CLASS WriteFile;

DROP PROCEDURE OverwriteFile IF EXISTS;
load classes stored_procedures/OverwriteFile/OverwriteFile.jar;
CREATE PROCEDURE FROM CLASS OverwriteFile;
