package com.chess.engine.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    private static final  Dimension OUTER_FRAME_DIMENSION= new Dimension(600,600);
    private static String defaultPieceImages="art/simple/";
    private final JFrame game_Frame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;




    public Table(){
        final JMenuBar tableMenuBar= createTableMenuBar();
        this.game_Frame=new JFrame("JChess");
        this.game_Frame.setLayout(new BorderLayout());
        this.game_Frame.setVisible(true);
        this.game_Frame.setSize(OUTER_FRAME_DIMENSION);
        this.game_Frame.setJMenuBar(tableMenuBar);
        this.chessBoard=Board.createStandartBoard();
        this.boardPanel=new BoardPanel();
        this.game_Frame.add(this.boardPanel,BorderLayout.CENTER);




    }
    private JMenuBar createTableMenuBar(){
        final  JMenuBar tableMenuBar= new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;

    }
    private JMenu createFileMenu(){
    final JMenu fileMenu=new JMenu("File");
    final JMenu openPGN=new JMenu("Load PGN File");
    openPGN.addActionListener( new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Its open)");
        }
    });

    fileMenu.add(openPGN);



    final JMenuItem exitMenuItem=new JMenuItem("Exit");
    exitMenuItem.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    });
    fileMenu.add(exitMenuItem);
    return fileMenu;
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;
        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardTiles=new ArrayList<>();
            for(int i=0;i< BoardUtils.NUM_TILES;i++){
                final TilePanel tilePanel= new TilePanel(this,i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);

            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel{


        private final int tileID;
        TilePanel(final BoardPanel boardPanel, final int tileID){
            super(new GridLayout());

            this.tileID=tileID;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);
            validate();


        }

        private void assignTilePieceIcon(final Board board){
            this.removeAll();
            if(board.getTile(this.tileID).isOccupied()){
                try {
                    final BufferedImage image=
                            ImageIO.read(new File(defaultPieceImages+board.getTile(this.tileID).getPiece().getPieceAlliance().toString().substring(0,1)+
                                    board.getTile(this.tileID).toString()+".gif"));
                            add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void assignTileColor() {
            if(BoardUtils.EIGHTH_RANK[this.tileID]
                    ||BoardUtils.SIXTH_RANK[this.tileID]
                    ||BoardUtils.FOURTH_RANK[this.tileID]
                    ||BoardUtils.SECOND_RANK[this.tileID]){
                setBackground(this.tileID%2==0? Color.white:Color.black);


            }
            else if(BoardUtils.SEVENTH_RANK[this.tileID]
                    ||BoardUtils.FIFTH_RANK[this.tileID]
                    ||BoardUtils.THIRD_RANK[this.tileID]
                    ||BoardUtils.FIRST_RANK[this.tileID]){
                setBackground(this.tileID%2!=0? Color.white:Color.black);
            }


        }

    }

}
