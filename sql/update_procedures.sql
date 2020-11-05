DROP PROCEDURE Create IF EXISTS;
load classes stored_procedures/Create/Create.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Create;

DROP PROCEDURE Read IF EXISTS;
load classes stored_procedures/Read/Read.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Read;

DROP PROCEDURE Write IF EXISTS;
load classes stored_procedures/Write/Write.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Write;

DROP PROCEDURE Overwrite IF EXISTS;
load classes stored_procedures/Overwrite/Overwrite.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Overwrite;

DROP PROCEDURE Populate IF EXISTS;
load classes stored_procedures/Populate/Populate.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Populate;

DROP PROCEDURE Create_Big IF EXISTS;
load classes stored_procedures/Create_Big/Create_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Create_Big;

DROP PROCEDURE Populate_Big IF EXISTS;
load classes stored_procedures/Populate_Big/Populate_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Populate_Big;

DROP PROCEDURE Read_Big IF EXISTS;
load classes stored_procedures/Read_Big/Read_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Read_Big;

DROP PROCEDURE Empty IF EXISTS;
load classes stored_procedures/Empty/Empty.jar;
CREATE PROCEDURE FROM CLASS Empty;

