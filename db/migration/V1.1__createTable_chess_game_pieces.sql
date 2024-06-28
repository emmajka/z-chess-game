create TABLE chess_game_pieces (
    id INT NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(255) NOT NULL,
    piece_id INT NOT NULL,
    piece_type CHAR(1) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_chess_game_pieces_game_id_piece_id UNIQUE (game_id, piece_id)
    CONSTRAINT uq_chess_game_pieces_piece_id_piece_type UNIQUE (piece_id, piece_type)
)