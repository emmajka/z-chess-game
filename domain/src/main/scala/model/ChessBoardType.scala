package model

enum ChessBoardType(val width: Int, val height: Int) {
  case Standard extends ChessBoardType(8, 8)
}