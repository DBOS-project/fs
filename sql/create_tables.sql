CREATE TABLE File (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       block_number INTEGER,
	   file_size INTEGER,
       bytes VARBINARY(1048576),
);
       
CREATE TABLE Big_File (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       file_ptr VARCHAR(1024),
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
       
PARTITION TABLE File ON COLUMN user_name;
PARTITION TABLE Big_File ON COLUMN user_name;
PARTITION TABLE Directory ON COLUMN user_name;
PARTITION TABLE Permission ON COLUMN user_name;

CREATE UNIQUE INDEX file_unq_idx ON File (user_name, file_name, block_number);
CREATE INDEX file_idx ON File (user_name, file_name);
CREATE UNIQUE INDEX big_file_idx ON Big_File (user_name, file_name);
CREATE UNIQUE INDEX directory_unq_idx ON Directory (user_name, directory_name);
CREATE UNIQUE INDEX permission_unq_idx ON Permission (user_name, file_name);

