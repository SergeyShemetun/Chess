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

public class WhitePlayer extends Player{
    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
        final List<Move> kingCastles=new ArrayList<>();

        if(this.getPlayerKing().isFirstMove()&&!this.isInCheck()){
            //whites Kings Side Castle
            if(!this.board.getTile(61).isOccupied()&&!this.board.getTile(62).isOccupied()){
                final Tile rookTile=this.board.getTile(63);
                if(rookTile.isOccupied()&& rookTile.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61,opponentsLegals).isEmpty()&&
                            Player.calculateAttacksOnTile(62,opponentsLegals).isEmpty()&&
                    rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.KingsSideCastleMove(this.board,this.getPlayerKing(),
                                62,(Rook)rookTile.getPiece(),
                                rookTile.getTileCoordinate(),61));
                    }



                }
            }

            if(!this.board.getTile(59).isOccupied()&&!this.board.getTile(58).isOccupied()&&
                    !this.board.getTile(57).isOccupied()){
                final Tile rookTile =this.board.getTile(56);

                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()&&Player.calculateAttacksOnTile(58,opponentsLegals).isEmpty()&&Player.calculateAttacksOnTile(59,opponentsLegals).isEmpty()){
                    kingCastles.add(new Move.QueensSideCastleMove(this.board,this.getPlayerKing(),
                            58,(Rook)rookTile.getPiece(),
                            rookTile.getTileCoordinate(),59));
                }


            }

        }

        return Collections.unmodifiableList(kingCastles);
    }
}
