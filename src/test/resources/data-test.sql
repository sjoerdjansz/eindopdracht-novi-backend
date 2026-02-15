-- EXERCISES
INSERT INTO exercises (name, muscles, movement)
VALUES ('bench press', 'pectoralis major, triceps brachii, anterior deltoid', 'horizontal press');

INSERT INTO exercises (name, muscles, movement)
VALUES ('squat', 'quadriceps, gluteus maximus, adductor magnus', 'knee dominant');

INSERT INTO exercises (name, muscles, movement)
VALUES ('deadlift', 'gluteus maximus, hamstrings, erector spinae', 'hip dominant');

INSERT INTO exercises (name, muscles, movement)
VALUES ('overhead press', 'deltoideus, triceps brachii, upper trapezius', 'vertical press');

INSERT INTO exercises (name, muscles, movement)
VALUES ('pull-up', 'latissimus dorsi, biceps brachii, rhomboids', 'vertical pull');

INSERT INTO exercises (name, muscles, movement)
VALUES ('barbell row', 'latissimus dorsi, rhomboids, posterior deltoid', 'horizontal pull');

INSERT INTO exercises (name, muscles, movement)
VALUES ('lunge', 'quadriceps, gluteus maximus, hamstrings', 'unilateral knee dominant');

INSERT INTO exercises (name, muscles, movement)
VALUES ('hip thrust', 'gluteus maximus, hamstrings', 'hip dominant');

INSERT INTO exercises (name, muscles, movement)
VALUES ('lat pulldown', 'latissimus dorsi, biceps brachii, teres major', 'vertical pull');

INSERT INTO exercises (name, muscles, movement)
VALUES ('push-up', 'pectoralis major, triceps brachii, anterior deltoid', 'horizontal press');


-- WORKOUTS
INSERT INTO workouts (name, created_at, created_by, notes)
VALUES (
           'Full Body Strength',
           NOW(),
           'Sjoerd',
           'Focus on compound lifts, moderate volume'
       );

INSERT INTO workouts (name, created_at, created_by, notes)
VALUES (
           'Upper Body Push',
           NOW(),
           'Sjoerd',
           'Chest, shoulders and triceps emphasis'
       );

INSERT INTO workouts (name, created_at, created_by, notes)
VALUES (
           'Upper Body Pull',
           NOW(),
           'Sjoerd',
           'Back and biceps focus'
       );

INSERT INTO workouts (name, created_at, created_by, notes)
VALUES (
           'Lower Body Strength',
           NOW(),
           'Coach Henk',
           'Heavy squat and hip dominant movements'
       );

INSERT INTO workouts (name, created_at, created_by, notes)
VALUES (
           'Glutes & Legs',
           NOW(),
           'Coach Boy',
           'Glute-focused hypertrophy session'
       );

-- WORKOUT_EXERCISES
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (1, 2);  -- squat
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (1, 1);  -- bench press
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (1, 3);  -- deadlift
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (1, 6);  -- barbell row

INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (2, 1);  -- bench press
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (2, 4);  -- overhead press
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (2, 10); -- push-up

INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (3, 5);  -- pull-up
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (3, 6);  -- barbell row
INSERT INTO workout_exercises (workout_id, exercise_id) VALUES (3, 9);  -- lat pulldown

-- CLIENTS

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Test', 'Client', 'testclient@sweatdaddy.com', '1970-12-20', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Sjoerd', 'Jansz', 'sjoerd.jansz@example.com', '1989-04-12', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Lisa', 'de Vries', 'lisa.devries@example.com', '1993-09-22', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Mark', 'van Dijk', 'mark.vandijk@example.com', '1987-01-05', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Noor', 'Bakker', 'noor.bakker@example.com', '1998-06-18', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Jeroen', 'Visser', 'jeroen.visser@example.com', '1991-11-30', NOW());

INSERT INTO clients (first_name, last_name, email, birthday, created_at)
VALUES ('Fatima', 'El Amrani', 'fatima.elamrani@example.com', '1995-03-08', NOW());

-- CLIENT_WORKOUTS

INSERT INTO client_workouts (client_id, workout_id)
VALUES (1, 1);

INSERT INTO client_workouts (client_id, workout_id)
VALUES (1, 2);

INSERT INTO client_workouts (client_id, workout_id)
VALUES (2, 3);

INSERT INTO client_workouts (client_id, workout_id)
VALUES (2, 4);

INSERT INTO client_workouts (client_id, workout_id)
VALUES (3, 5);

-- WORKOUT_SESSIONS

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (1, 1, '2026-02-01T09:00:00', TRUE, 'Felt strong today. Focused on form.', 55);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (1, 2, '2026-02-04T18:30:00', TRUE, 'Shoulders a bit tight, reduced volume.', 45);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (1, 1, '2026-02-08T10:15:00', FALSE, 'Started late, had to stop early.', 25);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (2, 3, '2026-02-03T07:30:00', TRUE, 'Good pull session. Pull-ups improving.', 50);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (2, 4, '2026-02-06T07:20:00', TRUE, 'Heavy squats. RPE high.', 60);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (3, 5, '2026-02-02T17:45:00', TRUE, 'Glute burn, added extra hip thrust sets.', 65);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (3, 5, '2026-02-09T17:50:00', TRUE, 'Second time this week. Less rest.', 58);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (4, 1, '2026-02-05T19:00:00', TRUE, 'Full body felt good. Deadlift technique focus.', 52);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (5, 2, '2026-02-07T11:00:00', FALSE, 'Energy low, stopped after warm-up + bench.', 20);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (6, 3, '2026-02-10T08:10:00', TRUE, 'Back felt great. Added lat pulldown.', 48);

INSERT INTO workout_sessions (client_id, workout_id, session_date, completed, notes, duration_in_minutes)
VALUES (7, 4, '2026-02-11T16:30:00', TRUE, 'Leg day. Kept it controlled, no pain.', 57);