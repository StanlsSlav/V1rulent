CREATE SEQUENCE game_saves_id START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE TYPE city AS OBJECT (
    name    VARCHAR2(20),
    viruses NUMBER(1)
) INSTANTIABLE NOT FINAL;

CREATE OR REPLACE TYPE player AS OBJECT (
    name         VARCHAR2(10),
    actions_left NUMBER
);


-- Each card is represented by its colour name
CREATE OR REPLACE TYPE cards_arr AS VARRAY(6) OF VARCHAR2(7);

-- Each cure is represented by its binary, 1 unlocked
CREATE OR REPLACE TYPE cures_arr AS VARRAY(4) OF NUMBER(1);

CREATE OR REPLACE TYPE city_arr AS VARRAY(49) OF city;


CREATE TABLE game_saves (
    id              NUMBER PRIMARY KEY,
    save_date       DATE,
    player          player    DEFAULT player('Unknown', 0),
    character       VARCHAR2(40) NOT NULL,
    cities          city_arr     NOT NULL,
    cards           cards_arr DEFAULT cards_arr(0, 0, 0, 0, 0, 0),
    cures           cures_arr DEFAULT cures_arr(),
    total_outbreaks NUMBER    DEFAULT 0
);

CREATE TABLE match_results (
    player          player NOT NULL,
    survived_rounds NUMBER NOT NULL,
    end_date        DATE   NOT NULL,

    result          VARCHAR2(10)
        CHECK (LOWER(result) IN ('victory', 'loss'))
);


CREATE OR REPLACE TRIGGER new_match_results
    BEFORE INSERT
    ON match_results
    FOR EACH ROW
BEGIN
    :new.end_date := SYSDATE;
END;

CREATE OR REPLACE TRIGGER new_game_save
    BEFORE INSERT
    ON game_saves
    FOR EACH ROW
BEGIN
    :new.id := game_saves_id.nextval;
    :new.save_date := SYSDATE;
END;


-- Make life fairly easier
CREATE OR REPLACE VIEW player_points
AS
SELECT mr.player.name AS player_name, COUNT(*) AS points
  FROM match_results mr
 WHERE result = 'victory'
 GROUP BY mr.player.name;

-- getTop10Players()
CREATE OR REPLACE VIEW leaderboard AS
SELECT player_name, points
  FROM (
           SELECT DISTINCT player_name, points
             FROM player_points
            ORDER BY points DESC, player_name
       );
