CREATE KEYSPACE IF NOT EXISTS blocadmin with replication={'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS blocadmin.users (
    user_id UUID,
    username TEXT,
    password TEXT,
    first_name TEXT,
    last_name TEXT,
    user_type smallint,
    building_nr int,
    appartment_nr int,
	details text,
    PRIMARY KEY((user_id))
);
