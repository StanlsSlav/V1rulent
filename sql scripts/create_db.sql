CREATE SEQUENCE id_count START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE TYPE cards_arr AS VARRAY(6) OF NUMBER(1);
CREATE OR REPLACE TYPE cures_arr AS VARRAY(4) OF NUMBER(1);

CREATE OR REPLACE TYPE city AS OBJECT (
    name    VARCHAR2(20),
    viruses NUMBER(1)
) INSTANTIABLE NOT FINAL;

CREATE OR REPLACE TYPE city_arr AS VARRAY(49) OF city;

CREATE OR REPLACE TYPE player AS OBJECT (
    name         VARCHAR2(10),
    actions_left NUMBER
);

CREATE TABLE game_states (
    player          player,
    character       VARCHAR2(40) NOT NULL,
    cities          city,
    total_outbreaks NUMBER
);


CREATE TABLE match_results (
    player          player,
    survived_rounds NUMBER,
    end_date        DATE,

    result          VARCHAR2(10)
        CHECK (LOWER(result) IN ('victory', 'loss'))
);


CREATE TRIGGER new_match_results
    BEFORE INSERT
    ON match_results
    FOR EACH ROW
BEGIN
    -- TODO: All new match results have these defaults
    :new.end_date := SYSDATE;
    :new.player.actions_left := 0;
END;


-- Used for making life fairly easier
CREATE OR REPLACE VIEW player_points
AS
SELECT mr.player.name AS player_name, COUNT(*) AS points
  FROM match_results mr
 WHERE result = 'victory'
 GROUP BY mr.player.name;


-- Used for getTop10Players()
CREATE OR REPLACE VIEW leaderboard AS
SELECT player_name, points
  FROM (
           SELECT DISTINCT player_name, points
             FROM player_points
            ORDER BY points DESC, player_name
       );
