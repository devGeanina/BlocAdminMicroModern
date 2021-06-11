CREATE KEYSPACE IF NOT EXISTS blocadmin with replication={'class':'SimpleStrategy', 'replication_factor':1};

CREATE TABLE IF NOT EXISTS blocadmin.expenses(
  expense_id UUID,
  due_date TIMESTAMP,
  expense_type smallint,
  total_sum double,
  leftover_sum double,
  payed_in_full boolean,
  details TEXT,
  PRIMARY KEY((expense_id))
);

CREATE TABLE IF NOT EXISTS blocadmin.requests(
  request_id UUID,
  due_date TIMESTAMP,
  request_type smallint,
  name TEXT,
  is_resolved boolean,
  details TEXT,
  PRIMARY KEY((request_id))
);

CREATE TABLE IF NOT EXISTS blocadmin.households(
  household_id UUID,
  building_nr int,
  appartment_nr int,
  owner_name TEXT,
  rooms_nr int,
  nr_curr_occupants int,
  total_capacity int,
  details TEXT,
  PRIMARY KEY((household_id))
);

CREATE TABLE blocadmin.expenses_by_household(
  household_id UUID,
  expense_id UUID,
  apartment_nr int,
  building_nr int,
  owner TEXT,
  total_sum double,
  leftover_sum double,
  payed_in_full boolean,
  PRIMARY KEY((household_id), expense_id)
) WITH CLUSTERING ORDER BY (expense_id DESC);

CREATE TABLE blocadmin.requests_by_household(
  household_id UUID,
  request_id UUID,
  name TEXT,
  request_type smallint,
  address TEXT,
  resolved boolean,
  due_date TIMESTAMP,
  PRIMARY KEY((household_id), request_id)
) WITH CLUSTERING ORDER BY (request_id DESC);
	