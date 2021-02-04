CREATE TABLE File (
       p_key INTEGER NOT NULL,
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       block_number INTEGER NOT NULL,
       file_size INTEGER,
       bytes VARBINARY(1048576),
       present INTEGER,
       -- CONSTRAINT prim_key PRIMARY KEY (user_name, file_name, block_number)
       last_access TIMESTAMP,
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

CREATE TABLE PartitionInfo (
       p_key INTEGER NOT NULL,
       partition_id INTEGER NOT NULL,
       host_id INTEGER NOT NULL,
       host_name VARCHAR(16),
);

-- Partition Tables
PARTITION TABLE File ON COLUMN p_key;
PARTITION TABLE Big_File ON COLUMN user_name;
PARTITION TABLE Directory ON COLUMN user_name;
PARTITION TABLE Permission ON COLUMN user_name;

-- Create Indices
CREATE UNIQUE INDEX file_unq_idx ON File (p_key, user_name, file_name, block_number);
CREATE INDEX file_idx0 ON File (user_name, file_name);
CREATE INDEX file_idx1 ON File (user_name, file_name, block_number);
CREATE UNIQUE INDEX big_file_idx ON Big_File (user_name, file_name);
CREATE UNIQUE INDEX directory_unq_idx ON Directory (user_name, directory_name);
CREATE UNIQUE INDEX permission_unq_idx ON Permission (user_name, file_name);

CREATE INDEX partinf_pkey_idx ON PartitionInfo (p_key);
CREATE INDEX partinf_pid_idx ON PartitionInfo (partition_id);
CREATE INDEX partinf_hst_idx ON PartitionInfo (host_id);

