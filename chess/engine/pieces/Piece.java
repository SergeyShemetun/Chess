package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final  int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    public final int cachedHashCode;

    Piece(PieceType pieceType, final int piecePosition, final Alliance pieceAlliance){
        this.pieceType=pieceType;
        this.piecePosition=piecePosition;
        this.pieceAlliance=pieceAlliance;
        this.cachedHashCode = computeHashCode();
        this.isFirstMove=false;

    }

    public int computeHashCode(){
        return this.cachedHashCode;
    }

    @Override
    public boolean equals(final Object other){
    if(this==other){
        return true;
    }
    if(!(other instanceof Piece)){
        return false;
    }
    final Piece otherPiece=(Piece) other;
        return piecePosition==otherPiece.getPiecePosition() && pieceType==otherPiece.getPieceType()&&
                pieceAlliance==otherPiece.getPieceAlliance()&&isFirstMove==otherPiece.isFirstMove;
    }

    @Override
    public int hashCode(){
        int result =pieceType.hashCode();
        result=31*result+pieceAlliance.hashCode();
        result=31*result+piecePosition;
        result=31*result+(isFirstMove? 1:0);
        return result;
    }
    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public abstract Piece movePiece(Move move);



    public enum PieceType{


        PAWN("P"){
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }
        };

        private final String pieceName;
        PieceType(final String pieceName){
            this.pieceName=pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }
        public abstract boolean isKing();

        public abstract boolean isRook();
    }
}
