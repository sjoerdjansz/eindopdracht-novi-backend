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
