DROP PROCEDURE Create IF EXISTS;
load classes stored_procedures/Create/Create.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Create;

DROP PROCEDURE CreateDummy IF EXISTS;
load classes stored_procedures/CreateDummy/CreateDummy.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS CreateDummy;

DROP PROCEDURE Read IF EXISTS;
load classes stored_procedures/Read/Read.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Read;

DROP PROCEDURE Populate IF EXISTS;
load classes stored_procedures/Populate/Populate.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS Populate;

<<<<<<< HEAD
DROP PROCEDURE PopulateDummy IF EXISTS;
load classes stored_procedures/PopulateDummy/PopulateDummy.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS PopulateDummy;
=======
DROP PROCEDURE Create_Big IF EXISTS;
load classes stored_procedures/Create_Big/Create_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Create_Big;

DROP PROCEDURE Populate_Big IF EXISTS;
load classes stored_procedures/Populate_Big/Populate_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Populate_Big;

DROP PROCEDURE Read_Big IF EXISTS;
load classes stored_procedures/Read_Big/Read_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Read_Big;
>>>>>>> 461e19c14a0c6dad4e71708c4d4b8939286d5870

DROP PROCEDURE Empty IF EXISTS;
load classes stored_procedures/Empty/Empty.jar;
CREATE PROCEDURE FROM CLASS Empty;

DROP PROCEDURE CountFiles IF EXISTS;
load classes stored_procedures/CountFiles/CountFiles.jar;
CREATE PROCEDURE FROM CLASS CountFiles;

DROP PROCEDURE CountBytes IF EXISTS;
load classes stored_procedures/CountBytes/CountBytes.jar;
CREATE PROCEDURE FROM CLASS CountBytes;

DROP PROCEDURE CountLargerThan IF EXISTS;
load classes stored_procedures/CountLargerThan/CountLargerThan.jar;
CREATE PROCEDURE FROM CLASS CountLargerThan;

DROP PROCEDURE CountLargestK IF EXISTS;
load classes stored_procedures/CountLargestK/CountLargestK.jar;
CREATE PROCEDURE FROM CLASS CountLargestK;

