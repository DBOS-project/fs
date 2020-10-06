CREATE TABLE File (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       block_number INTEGER,
       bytes VARBINARY(1048576),
);
       
CREATE TABLE Directory (
       user_name VARCHAR(16) NOT NULL,
       directory_name VARCHAR(1024) NOT NULL,
       sub_directory VARCHAR(1024),
);
       
CREATE TABLE Permission (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       access SMALLINT,
);
       
CREATE UNIQUE INDEX file_idx ON File (user_name, file_name, block_number);
CREATE UNIQUE INDEX directory_idx ON Directory (user_name, directory_name);
CREATE UNIQUE INDEX permission_idx ON Permission (user_name, file_name);

PARTITION TABLE File ON COLUMN user_name;
PARTITION TABLE Directory ON COLUMN user_name;
PARTITION TABLE Permission ON COLUMN user_name;

load classes stored_procedures/createfile/CreateFile.jar;
load classes stored_procedures/readfile/ReadFile.jar;
load classes stored_procedures/writefile/WriteFile.jar;
CREATE PROCEDURE FROM CLASS CreateFile;
CREATE PROCEDURE FROM CLASS ReadFile;
CREATE PROCEDURE FROM CLASS WriteFile;
