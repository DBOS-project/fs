CREATE TABLE PartitionInfo (
       p_key INTEGER NOT NULL,
       partition_id INTEGER NOT NULL,
       host_id INTEGER,
       host_name VARCHAR(16),
);

CREATE TABLE UserInfo (
       home_partition INTEGER NOT NULL,
       user_name VARCHAR(16) NOT NULL,
       current_directory VARCHAR(1024) NOT NULL,
);

CREATE TABLE File (
       p_key INTEGER NOT NULL,
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL, -- full path
       block_number INTEGER NOT NULL,
       file_size INTEGER,
       bytes VARBINARY(1048576),
       present TINYINT, -- 0 if not present
       last_access TIMESTAMP,       
       -- CONSTRAINT prim_key PRIMARY KEY (user_name, file_name, block_number)
);
       
CREATE TABLE Big_File (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       file_ptr VARCHAR(1024),
);
       
CREATE TABLE Directory (
       p_key INTEGER NOT NULL,
       directory_name VARCHAR(1024) NOT NULL, -- full path
       content_name VARCHAR(1024), -- relative path
       content_type TINYINT, -- 0 for file, 1 for directory
       user_name VARCHAR(16),
       -- protection_info SMALLINT,
);

CREATE TABLE Permission (
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       access SMALLINT,
);

-- Partition Tables
-- PARTITION TABLE UserInfo ON COLUMN home_partition;
PARTITION TABLE File ON COLUMN p_key;
PARTITION TABLE Big_File ON COLUMN user_name;
PARTITION TABLE Directory ON COLUMN p_key;
PARTITION TABLE Permission ON COLUMN user_name;

-- Create Indices
CREATE INDEX partinf_idx ON PartitionInfo (partition_id);
CREATE UNIQUE INDEX user_unq_idx ON UserInfo (home_partition, user_name);
CREATE UNIQUE INDEX file_unq_idx ON File (p_key, user_name, file_name, block_number);
CREATE INDEX file_idx0 ON File (user_name, file_name);
CREATE INDEX file_idx1 ON File (user_name, file_name, block_number);
CREATE UNIQUE INDEX big_file_unq_idx ON Big_File (user_name, file_name);
CREATE INDEX dir_idx ON Directory (user_name, directory_name);
-- CREATE UNIQUE INDEX permission_unq_idx ON Permission (user_name, file_name);

