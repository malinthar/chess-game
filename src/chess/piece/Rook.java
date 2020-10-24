package chess.piece;

import chess.ChessBoard;
import chess.FileRank;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Arrays;
import java.util.List;

public class Rook extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("a", "h");
    private static final String SYMBOL = "R";
    private static final List<String> VALID_MOVES = Arrays.asList(NORTH, EAST, SOUTH, WEST);
    public static final int MAX_STEPS = 8;
    private Boolean isMoved = false;

    public Rook(String kind, ChessBoard chessBoard) {
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
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) +
                toPosition.getFileRank().get(FileRank.RANK_KEY);
        return validateMoveAndUpdatePosition( getValidMoves(VALID_MOVES, MAX_STEPS,
                this.currentPosition, toFileRank),toFileRank,modifyPosition,toPosition);
    }

    public void setIsMoved(Boolean isMoved) {
        this.isMoved = isMoved;
    }
    public Boolean getIsMoved() {
        return this.isMoved;
    }

}
