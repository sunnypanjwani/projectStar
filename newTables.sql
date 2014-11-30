create table starstories.passwords (password_id INT AUTO_INCREMENT PRIMARY KEY,
users_user_id INT,
password_hash varchar(50) NOT NULL,
password_salt varchar(30) NOT NULL,
change_required varchar(1) DEFAULT 'N',
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
modified TIMESTAMP,
FOREIGN KEY (users_user_id) references users (user_id));
