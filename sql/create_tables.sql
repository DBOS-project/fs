CREATE TABLE File (
       p_key INTEGER NOT NULL,
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       block_number INTEGER,
       file_size INTEGER,
       bytes VARBINARY(1048576),
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
       partition_key INTEGER NOT NULL,
       partition_id INTEGER NOT NULL,
       host_id INTEGER NOT NULL,
       host_name VARCHAR(16),
);

CREATE TABLE Distribution (
       p_key INTEGER NOT NULL,
       user_name VARCHAR(16) NOT NULL,
       file_name VARCHAR(1024) NOT NULL,
       block_number INTEGER,
);

-- Partition Tables
PARTITION TABLE File ON COLUMN p_key;
PARTITION TABLE Big_File ON COLUMN user_name;
PARTITION TABLE Directory ON COLUMN user_name;
PARTITION TABLE Permission ON COLUMN user_name;
-- PARTITION TABLE Distribution ON COLUMN p_key; -- Should we partition? measure it

-- Create Indices
CREATE UNIQUE INDEX file_unq_idx ON File (p_key, user_name, file_name, block_number);
CREATE INDEX file_idx ON File (user_name, file_name);
CREATE UNIQUE INDEX big_file_idx ON Big_File (user_name, file_name);
CREATE UNIQUE INDEX directory_unq_idx ON Directory (user_name, directory_name);
CREATE UNIQUE INDEX permission_unq_idx ON Permission (user_name, file_name);

CREATE INDEX partinf_pkey_idx ON PartitionInfo (partition_key);
CREATE INDEX partinf_pid_idx ON PartitionInfo (partition_id);
CREATE INDEX partinf_hst_idx ON PartitionInfo (host_id);
CREATE INDEX distr_idx ON Distribution (user_name, file_name);
CREATE INDEX distr_pkey_idx ON Distribution (p_key);

