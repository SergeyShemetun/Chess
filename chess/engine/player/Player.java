package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public abstract class Player {
    protected final Board board;
    protected final King king;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;


    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.king = establishKing();
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,calculateKingCastles(legalMoves,opponentMoves)));    // concatinate with calculateKingCastles(legalMoves,opponentMoves)
        this.isInCheck=!Player.calculateAttacksOnTile(this.king.getPiecePosition(), opponentMoves).isEmpty();
    }




    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentsMoves) {
        final List<Move> attackMoves=new ArrayList<>();
        for(final Move move:opponentsMoves){
            if(piecePosition==move.getDestinationCoordinate()){
                attackMoves.add(move);
            }

        }

        return Collections.unmodifiableList(attackMoves);
    }
    public King getPlayerKing(){
        return this.king;
    }
    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
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
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,Collection<Move> opponentsLegals);
    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }



    public boolean isInCheck(){
        return this.isInCheck;
    }

    public boolean isInCheckmate(){
        return this.isInCheck&& !hasEscapeMoves();
    }

    protected boolean hasEscapeMoves(){
        for(final Move move:this.legalMoves){
            final MoveTransition transition=makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isInStalemate(){
        return !this.isInCheck&&!hasEscapeMoves();
    }

    //TODO implement those
    public boolean isCastled(){
        return false;
    }
    public MoveTransition makeMove(final Move move){
        if(!isMoveLegal(move)){
            return new MoveTransition(this.board,move,MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard=move.execute();
        final Collection<Move> kingAttacks=Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                transitionBoard.currentPlayer().getLegalMoves());
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard,move,MoveStatus.DONE);
    }
}
