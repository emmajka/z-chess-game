create TABLE chess_game_state (
    id INT NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(255) NOT NULL,
    piece_id INT NOT NULL,
    x_coordinate INT NOT NULL,
    y_coordinate INT NOT NULL,
    `active` BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_chess_game_state_game_id_piece_id UNIQUE (game_id, piece_id)
)