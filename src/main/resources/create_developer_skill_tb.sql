CREATE TABLE developer_skill(
  develiper_id integer not null ,
  skill_id integer not null ,
  primary key (develiper_id, skill_id)
);

ALTER TABLE developer_skill RENAME COLUMN develiper_id TO developer_id;