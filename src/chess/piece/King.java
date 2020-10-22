package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class King extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("e");
    private static final String SYMBOL = "K";
    private static final List<String> VALID_MOVES = Arrays.asList(NORTH,
            NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST);
    private static int MAX_STEPS = 1;

    public King(String kind, ChessBoard chessBoard) {
        super(kind, chessBoard);
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
        for (String position : getValidMoves(VALID_MOVES, this.MAX_STEPS, this.currentPosition, toFileRank)) {
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

    public Boolean identifyCheck() {
        for (Map.Entry entry : this.chessBoard.getChessBoard().entrySet()) {
            Object occupant = ((FileRank) entry.getValue()).getOccupant();
            if (occupant != null && !((ChessPiece) occupant).getKind().equals(this.kind)) {
                ChessPiece piece = (ChessPiece) ((FileRank) entry.getValue()).getOccupant();
                if (piece.move(this.currentPosition, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean getIsCheckMate() {
//        if(getValidMoves(VALID_MOVES, MAX_STEPS, this.currentPosition,null).size() == 0) {
//            return true;
//        }
        return false;
    }
}
