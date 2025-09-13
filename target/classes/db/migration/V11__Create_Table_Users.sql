CREATE TABLE public.users (
  id SERIAL NOT NULL,
  user_name varchar(255) DEFAULT NULL,
  full_name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  account_non_expired boolean DEFAULT false,
  account_non_locked boolean DEFAULT false,
  credentials_non_expired boolean DEFAULT false,
 enabled boolean DEFAULT false,
  PRIMARY KEY (id)
);
  ALTER TABLE public.users
    ADD CONSTRAINT  uk_user_name UNIQUE (user_name);
