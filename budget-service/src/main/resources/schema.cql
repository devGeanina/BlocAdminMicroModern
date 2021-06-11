CREATE KEYSPACE IF NOT EXISTS blocadmin with replication={'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS blocadmin.budgets (
    budget_id UUID,
    budget_type smallint,
    total_sum double,
    leftover_sum double,
	details text,
	status smallint,
	PRIMARY KEY((budget_id))
);