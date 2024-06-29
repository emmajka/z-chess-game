insert into game(game_id) values (1),(2),(3);
insert into game_pieces(game_id, piece_id, piece_type, x_coordinate, y_coordinate, active)
values
('1', 1, 'P', 1, 1, TRUE),
('1', 2, 'B', 2, 2, TRUE),
('1', 3, 'B', 2, 2, FALSE);