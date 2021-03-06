package com.chess.engine.board;

import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.Random;

public abstract class Move {
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    public static final Move NULL_MOVE=new NullMove();

     Move(final Board board,
         final Piece movedPiece,
         final int destinationCoordinate){
        this.board=board;
        this.movedPiece =movedPiece;
        this.destinationCoordinate=destinationCoordinate;
    }

    @Override
    public int hashCode(){
         final int prime=31;
         int result=1;
         result=prime+result+this.destinationCoordinate;
         result=prime+result+this.movedPiece.hashCode();
         return result;
    }
    @Override
    public boolean equals(final Object other){
        if(this==other){
            return true;
        }
        if(!(other instanceof Move)){
            return false;

        }
        final Move otherMove=(Move) other;
        return  getCurrentCoordinate()==otherMove.destinationCoordinate&&
                getMovedPiece()==otherMove.getMovedPiece();
     }

    public int getCurrentCoordinate() {
        return this.getMovedPiece().getPiecePosition();
    }

    public int getDestinationCoordinate() {
         return this.destinationCoordinate;
    }
    public Piece getMovedPiece(){
         return this.movedPiece;
    }
    public boolean isAttack(){
         return false;
    }
    public boolean isCastlingMove(){
         return false;
    }
    public Piece getAttackedPiece(){
         return null;
    }

    public Board execute() {
        final Board.Builder builder=new Board.Builder();
        for (final Piece piece:this.board.currentPlayer().getActivePieces()){
            //TODO hashcode and equals for pieces
            if(! this.movedPiece.equals(piece))
            {
                builder.setPiece(piece);
            }
        }
        for(final Piece piece:this.board.currentPlayer().getActivePieces()){
            builder.setPiece(piece);
        }
        //Move the moved Piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

        return builder.build();
    }


    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

    }




    public static class AttackMove extends Move{
        final Piece attackedPiece;
        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate,final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece=attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack(){
        return true;
        }
        @Override
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }
        @Override
        public int hashCode(){
            return this.attackedPiece.hashCode()+super.hashCode();
        }
        @Override
        public boolean equals(final Object other){
            if(this==other){
                return true;
            }
            if (!(other instanceof Move)){
                return false;
            }
            final AttackMove otherAttackMove=(AttackMove) other;
            return super.equals(otherAttackMove)&&getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }




    }

        public static final class PawnMove extends Move{

            PawnMove(Board board, Piece movedPiece, int destinationCoordinate) {
                super(board, movedPiece, destinationCoordinate);
            }
        }

    public static  class PawnAttackMove extends AttackMove{

        PawnAttackMove(Board board, Piece movedPiece, int destinationCoordinate,final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate,attackedPiece);
        }
    }
    public static final class PawnEnPassantMove extends PawnAttackMove{

        PawnEnPassantMove(Board board, Piece movedPiece, int destinationCoordinate,final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate,attackedPiece);
        }
    }
    public static final class PawnJump extends Move{

        PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
        public Board execute(){
            final Board.Builder builder= new Board.Builder();
            for(final Piece piece:this.board.currentPlayer().getActivePieces()){
                if(!(this.movedPiece.equals(piece))){
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece :this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn=(Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    static abstract class CastleMove extends Move {
         protected final Rook castleRook;
         protected final int castleRookStart;
         protected final int castleRookDestination;


        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate);
            this.castleRook=castleRook;
            this.castleRookStart=castleRookStart;
            this.castleRookDestination=castleRookDestination;

        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove(){
            return true;
        }

        @Override
        public Board execute(){
            final Board.Builder builder= new Board.Builder();
            for(final Piece piece:this.board.currentPlayer().getActivePieces()){
                if(!(this.movedPiece.equals(piece)) && !this.castleRook.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece :this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPieceAlliance()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

    }

    public static class KingsSideCastleMove extends CastleMove {


        public KingsSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        public String toString(){
            return "O-O";
        }
    }
    public static  class QueensSideCastleMove extends CastleMove {


        public QueensSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook, int castleRookStart, int castleRookDestination) {
            super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
        }
        public String toString(){
            return "O-O-O";
        }
    }

    static class NullMove extends Move {

        public NullMove() {
            super(null,null,-1);

        }
        @Override
        public Board execute(){
            throw new RuntimeException("Cannot execute null move");
        }

    }

    public static class MoveFactory{
         private MoveFactory(){
             throw  new RuntimeException("Not Instansiable");
         }
         public static Move createMove(final Board board, final int currentCoordinate,final int destinationCoordinate){
             for(final Move move:board.currentPlayer().getLegalMoves()){
                 if(move.getCurrentCoordinate()==currentCoordinate && move.getDestinationCoordinate()==destinationCoordinate){
                     return move;
                 }
             }
             return NULL_MOVE;

         }
    }



}
