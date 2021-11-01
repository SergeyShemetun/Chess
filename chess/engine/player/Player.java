package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;
import java.util.Collections;

public abstract class Player {
    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.king = establishKing();
        this.legalMoves = legalMoves;
    }

    private King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("illegal Position");
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();
    public abstract Player getOpponent();

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }


    //TODO implement those
    public boolean isInCheck(){
        return false;
    }
    public boolean isInCheckmate(){
        return false;
    }
    public boolean isInStalemate(){
        return false;
    }
    public boolean isCastled(){
        return false;
    }
    public MoveTransition makeMove(final Move move){
        return null;
    }
}
