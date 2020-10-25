package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;

/**
 * Knight chess piece.
 */
public class Knight extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("b", "g");
    private static final String SYMBOL = "N";
    private static final List<String> VALID_MOVES = Arrays.asList(LSHAPE);

    public Knight(String kind, ChessBoard chessBoard) {
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
        return validateMoveAndUpdatePosition(getValidMoves(VALID_MOVES, 3, this.currentPosition, toFileRank),
                toFileRank, modifyPosition, toPosition);
    }
}
