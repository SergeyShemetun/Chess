package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece piece;
    final int destinationCoordinate;

     Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board=board;
        this.piece=movedPiece;
        this.destinationCoordinate=destinationCoordinate;
    }
    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }



    public static final class AttackMove extends Move{
        final Piece attackedPiece;
        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate,final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece=attackedPiece;
        }
    }
}
