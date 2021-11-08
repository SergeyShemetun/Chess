package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Knight extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATES={-17,-15,-10,-6,6,10,15,17};
    public Knight(int piecePosition, Alliance pieceAlliance) {
        super(PieceType.KNIGHT, piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {


       final List<Move> legalMoves=new ArrayList<>();

        for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;

            if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
                if(ifFirstColomnExclusion(this.piecePosition,currentCandidateOffset)||
                        ifSecondColomnExclusion(this.piecePosition,currentCandidateOffset)||
                        ifSeventhColomnExclusion(this.piecePosition,currentCandidateOffset)||
                        ifEigthColomnsExclusion(this.piecePosition,currentCandidateOffset))
                {
                    continue;
                }
                final Tile candidateDestinationTile= board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isOccupied()){
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
        return BoardUtils.FIRST_COLLOMN[currentPosition] &&((candidateOffset==-17)||(candidateOffset==-10)||(candidateOffset==6)||(candidateOffset==15));

    }
    private static boolean ifSecondColomnExclusion(final  int currentPosition, final  int  candidateOffset){
        return BoardUtils.SECOND_COLLOMN[currentPosition] &&((candidateOffset==-10)||(candidateOffset==6));

    }
    private static boolean ifSeventhColomnExclusion(final  int currentPosition, final  int  candidateOffset){
        return BoardUtils.SEVENTH_COLLOMN[currentPosition] &&((candidateOffset==10)||(candidateOffset==-6));

    }
    private static boolean ifEigthColomnsExclusion(final  int currentPosition, final  int  candidateOffset){
        return BoardUtils.EIGTH_COLLOMN[currentPosition] &&((candidateOffset==-15)||(candidateOffset==-6)||(candidateOffset==10)||(candidateOffset==17));

    }
    public Knight movePiece (final Move move) {
        return new Knight(move.getDestinationCoordinate(), move.getMovedPiece().getPieceAlliance());
    }
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

}
