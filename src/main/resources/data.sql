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
