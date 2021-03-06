package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(Board board, Collection<Move> blackStandardLegalMoves, Collection<Move> whiteStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.getPlayerKing().isFirstMove() && !this.isInCheck()) {
            //blacks Kings Side Castle
            if (!this.board.getTile(5).isOccupied() && !this.board.getTile(6).isOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingsSideCastleMove(this.board,this.getPlayerKing(),
                                6,(Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),5));
                    }


                }
            }

            if (!this.board.getTile(1).isOccupied() && !this.board.getTile(2).isOccupied() &&
                    !this.board.getTile(3).isOccupied()) {
                final Tile rookTile = this.board.getTile(0);

                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()&& Player.calculateAttacksOnTile(2,opponentsLegals).isEmpty()&&Player.calculateAttacksOnTile(3,opponentsLegals).isEmpty()) {
                    kingCastles.add(new Move.QueensSideCastleMove(this.board,this.getPlayerKing(),
                            2,(Rook)rookTile.getPiece(),
                            rookTile.getTileCoordinate(),3));
                }


            }
        }
        return Collections.unmodifiableList(kingCastles);

    }
}
