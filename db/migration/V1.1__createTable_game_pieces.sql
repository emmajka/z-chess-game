create TABLE game_pieces (
    id INT NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(255) NOT NULL,
    piece_id INT NOT NULL,
    piece_type CHAR(1) NOT NULL,
    x_coordinate INT NOT NULL,
    y_coordinate INT NOT NULL,
    `active` BOOLEAN NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY(game_id) REFERENCES game(game_id),
    CONSTRAINT uq_game_pieces_game_id_piece_id_piece_type_id UNIQUE (game_id, piece_id, piece_type_id),
    CONSTRAINT uq_game_pieces_piece_id_piece_type UNIQUE (piece_id, piece_type)
)