CREATE TABLE game (
    id INT NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_game_game_id UNIQUE (game_id)
)