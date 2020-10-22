package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Queen extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("d");
    private static final String SYMBOL = "Q";
    private static final List<String> directions = Arrays.asList(NORTH,NORTH_EAST,EAST,SOUTH_EAST,SOUTH,SOUTH_WEST,WEST,NORTH_WEST);
    public static final int MAX_STEPS = 8;

    public Queen(String kind, ChessBoard chessBoard) {
        super(kind,chessBoard);
    }

    @Override
    public String getSymbol() {
        if ("BLACK".equals(kind)) {
            return "b" + SYMBOL;
        } else {
            return "w" + SYMBOL;
        }
    }

    @Override
    public Boolean move(FileRank toPosition, Boolean modifyPosition) {
        Boolean isValid = false;
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) + toPosition.getFileRank().get(FileRank.RANK_KEY);
        for (String position : getValidMoves(directions, MAX_STEPS , this.currentPosition,toFileRank)) {
            if (toFileRank.equals(position)) {
                if(modifyPosition && getIsCheck(toFileRank)) {
                    isValid = true;
                    this.currentPosition = toPosition;
                } else if (modifyPosition){
                    isValid = false;
                } else{
                    isValid = true;
                }
                break;
            }
        }
        return isValid;
    }
}
