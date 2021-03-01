DROP PROCEDURE PartitionInfoSelect IF EXISTS;
load classes stored_procedures/PartitionInfoSelect/PartitionInfoSelect.jar;
CREATE PROCEDURE FROM CLASS PartitionInfoSelect;

DROP PROCEDURE PartitionInfoInsert IF EXISTS;
load classes stored_procedures/PartitionInfoInsert/PartitionInfoInsert.jar;
CREATE PROCEDURE FROM CLASS PartitionInfoInsert;

DROP PROCEDURE PartitionInfoUpdate IF EXISTS;
load classes stored_procedures/PartitionInfoUpdate/PartitionInfoUpdate.jar;
CREATE PROCEDURE FROM CLASS PartitionInfoUpdate;

DROP PROCEDURE GetPartitionInfo IF EXISTS;
load classes stored_procedures/GetPartitionInfo/GetPartitionInfo.jar;
CREATE PROCEDURE FROM CLASS GetPartitionInfo;

DROP PROCEDURE GetPartitionRange IF EXISTS;
load classes stored_procedures/GetPartitionRange/GetPartitionRange.jar;
CREATE PROCEDURE FROM CLASS GetPartitionRange;

DROP PROCEDURE CreateUser IF EXISTS;
load classes stored_procedures/CreateUser/CreateUser.jar;
-- CREATE PROCEDURE PARTITION ON TABLE UserInfo COLUMN home_partition FROM CLASS CreateUser;
CREATE PROCEDURE FROM CLASS CreateUser;

DROP PROCEDURE GetUserPartition IF EXISTS;
load classes stored_procedures/GetUserPartition/GetUserPartition.jar;
CREATE PROCEDURE FROM CLASS GetUserPartition;

DROP PROCEDURE Create IF EXISTS;
load classes stored_procedures/Create/Create.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Create;

DROP PROCEDURE CreateAt IF EXISTS;
load classes stored_procedures/CreateAt/CreateAt.jar;
CREATE PROCEDURE FROM CLASS CreateAt;

DROP PROCEDURE CreateBlock IF EXISTS;
load classes stored_procedures/CreateBlock/CreateBlock.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS CreateBlock;

DROP PROCEDURE CreateP IF EXISTS;
load classes stored_procedures/CreateP/CreateP.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS CreateP;

DROP PROCEDURE CreateBlockAt IF EXISTS;
load classes stored_procedures/CreateBlockAt/CreateBlockAt.jar;
CREATE PROCEDURE FROM CLASS CreateBlockAt;

DROP PROCEDURE Delete IF EXISTS;
load classes stored_procedures/Delete/Delete.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Delete;

DROP PROCEDURE CreateDir IF EXISTS;
load classes stored_procedures/CreateDir/CreateDir.jar;
-- CREATE PROCEDURE PARTITION ON TABLE UserInfo COLUMN home_partition FROM CLASS CreateDir;
CREATE PROCEDURE FROM CLASS CreateDir;

DROP PROCEDURE ChangeDir IF EXISTS;
load classes stored_procedures/ChangeDir/ChangeDir.jar;
-- CREATE PROCEDURE PARTITION ON TABLE UserInfo COLUMN home_partition FROM CLASS ChangeDir;
CREATE PROCEDURE FROM CLASS ChangeDir;

DROP PROCEDURE List IF EXISTS;
load classes stored_procedures/List/List.jar;
CREATE PROCEDURE FROM CLASS List;

-- DROP PROCEDURE Create_Big IF EXISTS;
-- load classes stored_procedures/Create_Big/Create_Big.jar;
-- CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Create_Big;

DROP PROCEDURE Read IF EXISTS;
load classes stored_procedures/Read/Read.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Read;

DROP PROCEDURE Read1FileNBlocks IF EXISTS;
load classes stored_procedures/Read1FileNBlocks/Read1FileNBlocks.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Read1FileNBlocks;

DROP PROCEDURE Write1FileNBlocks IF EXISTS;
load classes stored_procedures/Write1FileNBlocks/Write1FileNBlocks.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS Write1FileNBlocks;

DROP PROCEDURE ReadNFiles1Block IF EXISTS;
load classes stored_procedures/ReadNFiles1Block/ReadNFiles1Block.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS ReadNFiles1Block;

DROP PROCEDURE WriteNFiles1Block IF EXISTS;
load classes stored_procedures/WriteNFiles1Block/WriteNFiles1Block.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS WriteNFiles1Block;

-- DROP PROCEDURE Read_Big IF EXISTS;
-- load classes stored_procedures/Read_Big/Read_Big.jar;
-- CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Read_Big;

DROP PROCEDURE PopulateWithSize IF EXISTS;
load classes stored_procedures/PopulateWithSize/PopulateWithSize.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS PopulateWithSize;

DROP PROCEDURE PopulateWithBuffer IF EXISTS;
load classes stored_procedures/PopulateWithBuffer/PopulateWithBuffer.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS PopulateWithBuffer;

-- DROP PROCEDURE Populate_Big IF EXISTS;
-- load classes stored_procedures/Populate_Big/Populate_Big.jar;
-- CREATE PROCEDURE PARTITION ON TABLE Big_File COLUMN user_name FROM CLASS Populate_Big;

-- DROP PROCEDURE Write IF EXISTS;
-- load classes stored_procedures/Write/Write.jar;
-- CREATE PROCEDURE FROM CLASS Write;

-- DROP PROCEDURE Write_Big IF EXISTS;
-- load classes stored_procedures/Write_Big/Write_Big.jar;
-- CREATE PROCEDURE FROM CLASS Write_Big;

-- DROP PROCEDURE CheckStorage IF EXISTS;
-- load classes stored_procedures/CheckStorage/CheckStorage.jar;
-- CREATE PROCEDURE FROM CLASS CheckStorage;

-- DROP PROCEDURE SendToDisk IF EXISTS;
-- load classes stored_procedures/SendToDisk/SendToDisk.jar;
-- CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS SendToDisk;

DROP PROCEDURE SumLargerThan IF EXISTS;
load classes stored_procedures/SumLargerThan/SumLargerThan.jar;
CREATE PROCEDURE PARTITION ON TABLE File COLUMN p_key FROM CLASS SumLargerThan;

-- DROP PROCEDURE CountFiles IF EXISTS;
-- load classes stored_procedures/CountFiles/CountFiles.jar;
-- CREATE PROCEDURE FROM CLASS CountFiles;

-- DROP PROCEDURE CountBytes IF EXISTS;
-- load classes stored_procedures/CountBytes/CountBytes.jar;
-- CREATE PROCEDURE FROM CLASS CountBytes;

-- DROP PROCEDURE CountLargerThan IF EXISTS;
-- load classes stored_procedures/CountLargerThan/CountLargerThan.jar;
-- CREATE PROCEDURE FROM CLASS CountLargerThan;

-- DROP PROCEDURE CountLargestK IF EXISTS;
-- load classes stored_procedures/CountLargestK/CountLargestK.jar;
-- CREATE PROCEDURE FROM CLASS CountLargestK;
