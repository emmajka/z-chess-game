CREATE TABLE chess_game (
    id INT NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(255) NOT NULL,
    board_width INT NOT NULL,
    board_height INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_chess_game_game_id UNIQUE (game_id)
)