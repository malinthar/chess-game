package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;

/**
 * Queen chess piece.
 */
public class Queen extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("d");
    private static final String SYMBOL = "Q";
    private static final List<String> VALID_MOVES = Arrays.asList(NORTH,NORTH_EAST,EAST,SOUTH_EAST,SOUTH,SOUTH_WEST,WEST,NORTH_WEST);
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
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) + toPosition.getFileRank().get(FileRank.RANK_KEY);
        return validateMoveAndUpdatePosition( getValidMoves(VALID_MOVES, MAX_STEPS,
                this.currentPosition, toFileRank),toFileRank,modifyPosition,toPosition);
    }
}
