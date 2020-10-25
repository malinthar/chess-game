package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;


/**
 * Bishop chess piece.
 */
public class Bishop extends ChessPiece {
    public static final List<String> INITIAL_POSITIONS = Arrays.asList("c", "f");
    private static final String SYMBOL = "B";
    private static final List<String> VALID_MOVES = Arrays.asList(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);
    public static final int MAX_STEPS = 8;

    public Bishop(String kind, ChessBoard chessBoard) {
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
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) + toPosition.getFileRank().get(FileRank.RANK_KEY);
        return validateMoveAndUpdatePosition( getValidMoves(VALID_MOVES, MAX_STEPS,
                this.currentPosition, toFileRank),toFileRank,modifyPosition,toPosition);
    }
}
