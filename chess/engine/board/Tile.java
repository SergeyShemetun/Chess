package com.chess.engine.board;

import com.chess.engine.pieces.Piece;


import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//immutability
public abstract class Tile {
  protected int  tileCoordinate;
 private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE =createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final  Map<Integer,EmptyTile>emptyTileMap=new HashMap<>();
        for (int i=0;i<BoardUtils.NUM_TILES;i++){
            emptyTileMap.put(i,new EmptyTile(i));
        }
        return Collections.unmodifiableMap(emptyTileMap);
    }
public static Tile createTile(final int tileCoordinate,final Piece piece){
        return piece!=null? new OccupiedTile(tileCoordinate,piece): EMPTY_TILES_CACHE.get(tileCoordinate);
}
    public  Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isOccupied();
        public abstract Piece getPiece();

        public static final class EmptyTile extends Tile{

            private EmptyTile(final int coordinate){
                super(coordinate);
            }
            @Override
            public String toString(){
                return "-";
            }
            @Override
            public boolean isOccupied() {
                return false;
            }

            @Override
            public Piece getPiece() {
                return null;
            }
        }

    public static final class OccupiedTile extends Tile{
            private final Piece pieceOnTile;

        private OccupiedTile(int coordinate,Piece piece){
            super(coordinate);
            this.pieceOnTile=piece;
        }
        @Override
        public String toString(){

            return this.getPiece().getPieceAlliance().isBlack() ? this.getPiece().toString().toLowerCase(Locale.ROOT): this.getPiece().toString();
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}