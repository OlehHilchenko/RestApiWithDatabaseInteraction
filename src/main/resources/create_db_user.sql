CREATE USER 'ds_user'@'localhost' IDENTIFIED BY 'ds_user';
GRANT ALL PRIVILEGES ON * . * TO 'ds_user'@'localhost';
FLUSH PRIVILEGES;