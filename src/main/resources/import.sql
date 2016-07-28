INSERT INTO account (id, email, name, phone_number, sid, hash)
VALUES (DEFAULT, 'juhan@risti.eu', 'Juhan Test', '+37253974840', 'm46gq9t2331gh54rvp02nn2a0r', '1000:be79c21c1d3bc2af548d222e4eeccfe9222e073d6ecf0b83:691db50968d360b6063ca54ae2a4bf672038c5892e96e4f7');

INSERT INTO meeting (id, title, description, end_date_time, start_date_time, leader_id, location_type, location)
VALUES (DEFAULT, 'Future meeting', 'Every meeting needs to have a description, doesn´t it?', now() + (interval '3 days'), now() + (interval '3 days 1 hour'), 1, 0, '\254\355\000\005sr\000:ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate\000\000\000\000\000\000\000\001\002\000\002L\000\010latitudet\000\022Ljava/lang/Double;L\000\011longitudeq\000~\000\001xpsr\000\020java.lang.Double\200\263\302J)k\373\004\002\000\001D\000\005valuexr\000\020java.lang.Number\206\254\225\035\013\224\340\213\002\000\000xp@M\267~\241\021\230\234sq\000~\000\003@8\300\310@\000\000\000');

INSERT INTO meeting (id, title, description, end_date_time, start_date_time, leader_id, location_type, location)
VALUES (DEFAULT, 'Past meeting', 'Every meeting needs to have a description, doesn´t it?', now() - (interval '2 days'), now() - (interval '2 days'), 1, 0, '\254\355\000\005sr\000:ee.juhan.meetingorganizer.server.rest.domain.MapCoordinate\000\000\000\000\000\000\000\001\002\000\002L\000\010latitudet\000\022Ljava/lang/Double;L\000\011longitudeq\000~\000\001xpsr\000\020java.lang.Double\200\263\302J)k\373\004\002\000\001D\000\005valuexr\000\020java.lang.Number\206\254\225\035\013\224\340\213\002\000\000xp@M\267~\241\021\230\234sq\000~\000\003@8\300\310@\000\000\000');

INSERT INTO participant (id, email, location,name, participation_answer, phone_number, account_id, meeting_id)
VALUES (DEFAULT, 'juhan@risti.eu', null, 'Juhan Test', 0, '+37253974840', 1, 1);

INSERT INTO participant (id, email, location,name, participation_answer, phone_number, account_id, meeting_id)
VALUES (DEFAULT, 'juhan@risti.eu', null, 'Juhan Test', 0, '+37253974840', 1, 2);