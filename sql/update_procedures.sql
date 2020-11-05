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

DROP PROCEDURE PopulateDummy IF EXISTS;
load classes stored_procedures/PopulateDummy/PopulateDummy.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN user_name FROM CLASS PopulateDummy;

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

