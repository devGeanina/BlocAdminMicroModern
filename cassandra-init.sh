CQL="DROP keyspace blocadmin;
CREATE KEYSPACE blocadmin WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'} AND durable_writes = true;
CREATE TABLE blocadmin.users(user_id UUID,username TEXT,password TEXT,first_name TEXT,last_name TEXT,user_type smallint,building_nr int,appartment_nr int,details text,PRIMARY KEY((user_id))); CREATE TABLE blocadmin.requests(request_id UUID,due_date TIMESTAMP,request_type smallint,name TEXT,is_resolved boolean,details TEXT,PRIMARY KEY((request_id))); CREATE TABLE blocadmin.expenses(expense_id UUID,due_date TIMESTAMP,expense_type smallint,total_sum double,leftover_sum double,payed_in_full boolean,details TEXT,PRIMARY KEY((expense_id))); CREATE TABLE blocadmin.households(household_id UUID,building_nr int,appartment_nr int,owner_name TEXT,rooms_nr int,nr_curr_occupants int,total_capacity int,details TEXT,PRIMARY KEY((household_id))); CREATE TABLE blocadmin.expenses_by_household(household_id UUID,expense_id UUID,apartment_nr int,building_nr int,owner TEXT,total_sum double,leftover_sum double,payed_in_full boolean,PRIMARY KEY((household_id), expense_id)) WITH CLUSTERING ORDER BY (expense_id DESC); CREATE TABLE blocadmin.requests_by_household(household_id UUID,request_id UUID,name TEXT,request_type smallint,address TEXT,resolved boolean,due_date TIMESTAMP,PRIMARY KEY((household_id), request_id) ) WITH CLUSTERING ORDER BY (request_id DESC); CREATE TABLE blocadmin.budgets(budget_id UUID,budget_type smallint,total_sum double,leftover_sum double,details text,PRIMARY KEY((budget_id)));"

until echo $CQL | cqlsh; do
  echo "cqlsh: Cassandra is unavailable to initialize - will retry later"
  sleep 2
done &

exec /docker-entrypoint.sh "$@"