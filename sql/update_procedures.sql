DROP PROCEDURE Create IF EXISTS;
load classes stored_procedures/Create/Create.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Create;

DROP PROCEDURE Declare IF EXISTS;
load classes stored_procedures/Declare/Declare.jar;
CREATE PROCEDURE FROM CLASS Declare;

DROP PROCEDURE CreateDummy IF EXISTS;
load classes stored_procedures/CreateDummy/CreateDummy.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS CreateDummy;

DROP PROCEDURE Create_Big IF EXISTS;
load classes stored_procedures/Create_Big/Create_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Create_Big;

DROP PROCEDURE Read IF EXISTS;
load classes stored_procedures/Read/Read.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Read;

DROP PROCEDURE Read_Big IF EXISTS;
load classes stored_procedures/Read_Big/Read_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Read_Big;

DROP PROCEDURE Populate IF EXISTS;
load classes stored_procedures/Populate/Populate.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Populate;

DROP PROCEDURE Populate_Big IF EXISTS;
load classes stored_procedures/Populate_Big/Populate_Big.jar;
CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Populate_Big;

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

