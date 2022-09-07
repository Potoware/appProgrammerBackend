insert into clientes (nombre, apellido, email, create_at) values ('Cheri', 'Pembry', 'cpembry0@mit.edu', '2022-01-13');
insert into clientes (nombre, apellido, email, create_at) values ('Hyacinth', 'Hawkeswood', 'hhawkeswood1@archive.org', '2022-01-02');
insert into clientes (nombre, apellido, email, create_at) values ('Bree', 'D''Alessio', 'bdalessio2@arstechnica.com', '2021-07-12');
insert into clientes (nombre, apellido, email, create_at) values ('Tyrus', 'Chevalier', 'tchevalier3@stumbleupon.com', '2021-12-10');
insert into clientes (nombre, apellido, email, create_at) values ('Cathrine', 'Elizabeth', 'celizabeth4@illinois.edu', '2021-09-26');
insert into clientes (nombre, apellido, email, create_at) values ('Prudy', 'Chadwell', 'pchadwell5@pinterest.com', '2022-03-29');
insert into clientes (nombre, apellido, email, create_at) values ('Corbett', 'Bernardot', 'cbernardot6@angelfire.com', '2021-12-10');
insert into clientes (nombre, apellido, email, create_at) values ('Orin', 'Thunnercliff', 'othunnercliff7@bloglovin.com', '2022-04-06');
insert into clientes (nombre, apellido, email, create_at) values ('Dorree', 'Beaushaw', 'dbeaushaw8@istockphoto.com', '2022-04-21');
insert into clientes (nombre, apellido, email, create_at) values ('Brig', 'Pelos', 'bpelos9@biblegateway.com', '2021-06-08');
insert into clientes (nombre, apellido, email, create_at) values ('Patin', 'Worlock', 'pworlocka@rediff.com', '2021-07-26');
insert into clientes (nombre, apellido, email, create_at) values ('Townsend', 'Harfleet', 'tharfleetb@state.tx.us', '2021-07-02');
insert into clientes (nombre, apellido, email, create_at) values ('Dacy', 'Stidworthy', 'dstidworthyc@deviantart.com', '2022-05-14');
insert into clientes (nombre, apellido, email, create_at) values ('Ebonee', 'Ors', 'eorsd@smugmug.com', '2021-11-09');
insert into clientes (nombre, apellido, email, create_at) values ('Tannie', 'Reightley', 'treightleye@theguardian.com', '2021-08-30');
insert into clientes (nombre, apellido, email, create_at) values ('Melisande', 'Bearn', 'mbearnf@ebay.co.uk', '2021-06-18');
insert into clientes (nombre, apellido, email, create_at) values ('Celestina', 'Bereford', 'cberefordg@cbslocal.com', '2022-02-27');
insert into clientes (nombre, apellido, email, create_at) values ('Cassius', 'Fatscher', 'cfatscherh@noaa.gov', '2021-06-21');
insert into clientes (nombre, apellido, email, create_at) values ('Jillayne', 'Tomicki', 'jtomickii@cnn.com', '2022-05-12');
insert into clientes (nombre, apellido, email, create_at) values ('Forrester', 'Tresise', 'ftresisej@techcrunch.com', '2021-08-28');
insert into clientes (nombre, apellido, email, create_at) values ('Lazaro', 'Ivanilov', 'livanilovk@miitbeian.gov.cn', '2022-03-26');
insert into clientes (nombre, apellido, email, create_at) values ('Kariotta', 'Wittleton', 'kwittletonl@slashdot.org', '2021-10-30');
insert into clientes (nombre, apellido, email, create_at) values ('Brigid', 'Matyasik', 'bmatyasikm@elegantthemes.com', '2021-08-06');
insert into clientes (nombre, apellido, email, create_at) values ('Brody', 'Carney', 'bcarneyn@google.cn', '2021-10-28');
insert into clientes (nombre, apellido, email, create_at) values ('Bing', 'Court', 'bcourto@github.io', '2021-08-14');
insert into clientes (nombre, apellido, email, create_at) values ('Hanny', 'Whatley', 'hwhatleyp@cornell.edu', '2021-07-15');
insert into clientes (nombre, apellido, email, create_at) values ('Levon', 'Nother', 'lnotherq@com.com', '2022-04-13');
insert into clientes (nombre, apellido, email, create_at) values ('Nickie', 'Blyth', 'nblythr@statcounter.com', '2022-04-21');
insert into clientes (nombre, apellido, email, create_at) values ('Tomkin', 'Biner', 'tbiners@hugedomains.com', '2022-03-10');
insert into clientes (nombre, apellido, email, create_at) values ('Ramonda', 'Cubley', 'rcubleyt@twitter.com', '2021-06-17');

INSERT INTO usuarios (apellido,contrasenia,create_at,email,nombre) VALUES ('Potosi','Alejandro','2022-09-06','apotosi@uninpahu.edu.co','Alejandro');
INSERT INTO usuarios (apellido,contrasenia,create_at,email,nombre) VALUES ('Moreno','Alejandro','2022-09-06','apotosia@uninpahu.edu.co','Aleja');

INSERT INTO roles (nombre) values ("ROLE_USER");
INSERT INTO roles (nombre) values ("ROLE_ADMIN");
INSERT INTO roles (nombre) values ("ROLE_READ");

INSERT INTO usuarios_roles (usuario_id,rol_id) values ((SELECT id from usuarios where apellido='Potosi'),(select id from roles where nombre = 'ROLE_ADMIN'));
INSERT INTO usuarios_roles (usuario_id,rol_id) values ((SELECT id from usuarios where apellido='Potosi'),(select id from roles where nombre = 'ROLE_USER'));
INSERT INTO usuarios_roles (usuario_id,rol_id) values ((SELECT id from usuarios where apellido='Moreno'),(select id from roles where nombre = 'ROLE_READ'));

INSERT INTO permisos (nombre) values ("CLIENTES");
INSERT INTO permisos (nombre) values ("USUARIOS");
INSERT INTO permisos (nombre) values ("ROLES");

INSERT INTO roles_permisos (rol_id,permiso_id) values ((select id from roles where nombre = 'ROLE_ADMIN'),(select id from permisos where nombre = 'CLIENTES'));
INSERT INTO roles_permisos (rol_id,permiso_id) values ((select id from roles where nombre = 'ROLE_ADMIN'),(select id from permisos where nombre = 'USUARIOS'));
INSERT INTO roles_permisos (rol_id,permiso_id) values ((select id from roles where nombre = 'ROLE_USER'),(select id from permisos where nombre = 'ROLES'));
