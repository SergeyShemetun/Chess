package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDINATES={-9,-8,-7,-1,1,7,9};
    public King(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.KING, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves=new ArrayList<>();

        for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
            if(ifFirstColomnExclusion(this.piecePosition,currentCandidateOffset)||ifEigthColomnExclusion(this.piecePosition,currentCandidateOffset))
            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                final Tile candidateDestinationTile= board.getTile(candidateDestinationCoordinate);
                if (candidateDestinationTile.isOccupied()){
                    legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate));
                }
                else{
                    final Piece pieceAtDestination=candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance=pieceAtDestination.getPieceAlliance();
                    if(this.pieceAlliance!=pieceAlliance){
                        legalMoves.add(new Move.AttackMove( board,this, candidateDestinationCoordinate,pieceAtDestination));
                    }
                }


            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
    private static boolean ifFirstColomnExclusion(final  int currentPosition, final  int  candidateOffset){
        return BoardUtils.FIRST_COLLOMN[currentPosition] &&((candidateOffset==-9)||(candidateOffset==-1)||(candidateOffset==7));

    }
    private static boolean ifEigthColomnExclusion(final  int currentPosition, final  int  candidateOffset){
        return BoardUtils.EIGTH_COLLOMN[currentPosition] &&((candidateOffset==-7)||(candidateOffset==1)||(candidateOffset==9));

    }
    public King movePiece (final Move move) {
        return new King(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }
    @Override
    public String toString(){
        return PieceType.KING.toString();
    }
}
