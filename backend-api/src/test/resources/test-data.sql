INSERT INTO member (member_id, login_id, password, mail, nickname, profile_image, tendency, role) VALUES (1, 'AAAA','dkddckdcjcdjkckd', 'AAA@naver.com', 'AA', null, 'TURTLE', 'USER');


INSERT INTO appointment (appointment_id, title, appointment_type, appointment_time, place, latitude, longitude)
VALUES (1, 'title1', 'DEFAULT', '2024-09-10 10:00:00', 'Seoul', '37.5665', '126.9780');
INSERT INTO appointment_member (appointment_member_id, member_id, appointment_id) VALUES (1, 1, 1);

INSERT INTO appointment (appointment_id, title, appointment_type, appointment_time, place, latitude, longitude)
VALUES (2, 'title2', 'HOBBY', '2024-09-10 10:00:00', 'Seoul', '37.5665', '126.9780');
INSERT INTO appointment_member (appointment_member_id, member_id, appointment_id) VALUES (2, 1, 2);

INSERT INTO appointment (appointment_id, title, appointment_type, appointment_time, place, latitude, longitude)
VALUES (3, 'title3', 'DRINK', '2024-09-10 10:00:00', 'Seoul', '37.5665', '126.9780');
INSERT INTO appointment_member (appointment_member_id, member_id, appointment_id) VALUES (3, 1, 3);

INSERT INTO appointment (appointment_id, title, appointment_type, appointment_time, place, latitude, longitude)
VALUES (4, 'title4', 'DEFAULT', '2024-09-10 10:00:00', 'Seoul', '37.5665', '126.9780');
INSERT INTO appointment_member (appointment_member_id, member_id, appointment_id) VALUES (4, 1, 4);

INSERT INTO appointment (appointment_id, title, appointment_type, appointment_time, place, latitude, longitude)
VALUES (5, 'title5', 'DEFAULT', '2024-09-10 10:00:00', 'Seoul', '37.5665', '126.9780');
INSERT INTO appointment_member (appointment_member_id, member_id, appointment_id) VALUES (5, 1, 5);