package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chess piece in chess.
 */
public abstract class ChessPiece {
    protected FileRank currentPosition;
    protected String kind;

    //list of possible moves of all chess pieces.
    protected static final String NORTH = "NORTH";
    protected static final String NORTH_EAST = "NORTH_EAST";
    protected static final String EAST = "EAST";
    protected static final String SOUTH_EAST = "SOUTH_EAST";
    protected static final String SOUTH = "SOUTH";
    protected static final String SOUTH_WEST = "SOUTH_WEST";
    protected static final String WEST = "WEST";
    protected static final String NORTH_WEST = "NORTH_WEST";
    protected static final String LSHAPE = "LSHAPE";

    //stores the chess board to which the chess piece belongs.
    protected ChessBoard chessBoard;

    public ChessPiece(String kind, ChessBoard chessBoard) {
        this.kind = kind;
        this.chessBoard = chessBoard;
    }

    /**
     * Perform a specified move and return if the move is valid.
     *
     * @param toPosition
     * @param modifyPosition
     * @return
     */
    public abstract Boolean move(FileRank toPosition, Boolean modifyPosition);

    /**
     * Sets the current position(FileRanK) of a chess piece.
     *
     * @param currentPosition
     */
    public void setCurrentPosition(FileRank currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Retruns the current position(FileRank) of a chess piece.
     *
     * @return
     */
    public FileRank getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Returns the kind of the chess piece.
     *
     * @return
     */
    public String getKind() {
        return this.kind;
    }

    /**
     * Returns the unique symbol of the piece.
     *
     * @return
     */
    public abstract String getSymbol();

    /**
     * Returns all valid moves for a particular ChessPiece object.
     *
     * @param directions   valid moving directions of the piece.
     * @param maxSteps     maximum number of steps the piece can move along any direction.
     * @param fromPosition current position of the piece.
     * @param toFileRank   target/destination position of the piece.
     * @return
     */
    public List<String> getValidMoves(List<String> directions, int maxSteps,
                                      FileRank fromPosition, String toFileRank) {
        //Set of valid moves
        List<String> validMoves = new ArrayList<>();

        String fromFile = fromPosition.getFileRank().get(FileRank.FILE_KEY);
        String fromRank = fromPosition.getFileRank().get(FileRank.RANK_KEY);
        int fileIndex = Arrays.asList(ChessBoard.FILES).indexOf(fromFile);
        int rankIndex = Arrays.asList(ChessBoard.RANKS).indexOf(fromRank);

        //For each valid direction find out possible valid destinations and return the possible destinations.
        for (String direction : directions) {
            int steps;
            switch (direction) {
                case NORTH:
                    steps = 0;
                    for (int rank = rankIndex - 1; rank > -1 && steps < maxSteps; rank--) {
                        String targetFileRank = ChessBoard.FILES[fileIndex] + ChessBoard.RANKS[rank];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case NORTH_EAST:
                    steps = 0;
                    for (int p = 1; rankIndex - p > -1 && fileIndex + p < ChessBoard.FILES.length &&
                            steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex + p] + ChessBoard.RANKS[rankIndex - p];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case EAST:
                    steps = 0;
                    for (int file = fileIndex + 1; file < ChessBoard.FILES.length && steps < maxSteps; file++) {
                        String targetFileRank = ChessBoard.FILES[file] + ChessBoard.RANKS[rankIndex];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH_EAST:
                    steps = 0;
                    for (int p = 1; rankIndex + p < ChessBoard.RANKS.length &&
                            fileIndex + p < ChessBoard.FILES.length && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex + p] + ChessBoard.RANKS[rankIndex + p];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH:
                    steps = 0;
                    for (int rank = rankIndex + 1; rank < ChessBoard.RANKS.length && steps < maxSteps; rank++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex] + ChessBoard.RANKS[rank];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case SOUTH_WEST:
                    steps = 0;
                    for (int p = 1; rankIndex + p < ChessBoard.RANKS.length
                            && fileIndex - p > -1 && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex - p] + ChessBoard.RANKS[rankIndex + p];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case WEST:
                    steps = 0;
                    for (int file = fileIndex - 1; file > -1 && steps < maxSteps; file--) {
                        String targetFileRank = ChessBoard.FILES[file] + ChessBoard.RANKS[rankIndex];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;
                case NORTH_WEST:
                    steps = 0;
                    for (int p = 1; rankIndex - p > -1 && fileIndex - p > -1 && steps < maxSteps; p++) {
                        String targetFileRank = ChessBoard.FILES[fileIndex - p] + ChessBoard.RANKS[rankIndex - p];
                        if (isClearPath(targetFileRank, toFileRank)) {
                            validMoves.add(targetFileRank);
                            steps += 1;
                        } else {
                            break;
                        }
                    }
                    break;

                case LSHAPE:
                    int filesLength = ChessBoard.FILES.length;
                    int ranksLength = ChessBoard.RANKS.length;
                    if (fileIndex + 2 < filesLength && rankIndex + 1 < ranksLength) {
                        validMoves.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex + 1]);
                    }
                    if (fileIndex + 1 < filesLength && rankIndex + 2 < ranksLength) {
                        validMoves.add(ChessBoard.FILES[fileIndex + 1] + ChessBoard.RANKS[rankIndex + 2]);
                    }
                    if (fileIndex - 1 > -1 && rankIndex + 2 < ranksLength) {
                        validMoves.add(ChessBoard.FILES[fileIndex - 1] + ChessBoard.RANKS[rankIndex + 2]);
                    }
                    if (fileIndex - 2 > -1 && rankIndex + 1 < ranksLength) {
                        validMoves.add(ChessBoard.FILES[fileIndex - 2] + ChessBoard.RANKS[rankIndex + 1]);
                    }
                    if (fileIndex - 2 > -1 && rankIndex - 1 > -1) {
                        validMoves.add(ChessBoard.FILES[fileIndex - 2] + ChessBoard.RANKS[rankIndex - 1]);
                    }
                    if (fileIndex - 1 > -1 && rankIndex - 2 > -1) {
                        validMoves.add(ChessBoard.FILES[fileIndex - 1] + ChessBoard.RANKS[rankIndex - 2]);
                    }
                    if (fileIndex + 1 < filesLength && rankIndex - 2 > -1) {
                        validMoves.add(ChessBoard.FILES[fileIndex + 1] + ChessBoard.RANKS[rankIndex - 2]);
                    }
                    if (fileIndex + 2 < filesLength && rankIndex - 1 > -1) {
                        validMoves.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex - 1]);
                    }
                    if (fileIndex + 2 < filesLength && rankIndex + 1 < filesLength) {
                        validMoves.add(ChessBoard.FILES[fileIndex + 2] + ChessBoard.RANKS[rankIndex + 1]);
                    }
                    break;
            }
        }
        return validMoves;
    }

    /**
     * Return if a particular move is obstructed by another piece.
     *
     * @param targetFileRank
     * @param toFileRank
     * @return
     */
    public Boolean isClearPath(String targetFileRank, String toFileRank) {
        Boolean isValid;
        //Check if there is an obstructing ChessPiece on the path to the destination.
        if (this.chessBoard.getChessBoard().get(targetFileRank).getOccupant() != null) {
            //check whether the target is not obstructed  and the King is safe.
            if (targetFileRank.equals(toFileRank)
                    && !((ChessPiece) (this.chessBoard.getChessBoard().
                    get(targetFileRank).getOccupant())).getKind().equals(this.kind)) {
                isValid = true;
            } else {
                isValid = false;
            }
        } else {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Return if a particular move of a chess piece causes a check to its king.
     *
     * @param toFileRank destination.
     * @return
     */
    public Boolean getIsKingChecked(String toFileRank) {
        Boolean isChecked;
        FileRank tempPosition = this.currentPosition;
        Object tempOccupant = chessBoard.getChessBoard().get(toFileRank).getOccupant();
        this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
        this.chessBoard.getChessBoard().get(tempPosition.getFileRankString()).setOccupant(null);
        this.chessBoard.getChessBoard().get(toFileRank).setOccupant(this);
        if (this.chessBoard.getKing(this.kind).identifyCheck()) {
            isChecked = true;
        } else {
            isChecked = false;
        }
        this.currentPosition = tempPosition;
        this.chessBoard.getChessBoard().get(tempPosition.getFileRankString()).setOccupant(this);
        this.chessBoard.getChessBoard().get(toFileRank).setOccupant(tempOccupant);
        return isChecked;
    }

    /**
     * Returns if the specified move is one of the possible moves of the chess
     * piece in the current context.
     *
     * @param targetPositions set of possible moves in the current context.
     * @param toFileRank      destination filerank as a string
     * @param modifyPosition  whether to just check if its a valid move or to modify the position if its a valid move
     * @param toPosition      destination filerank.
     * @return
     */
    public Boolean validateMoveAndUpdatePosition(List<String> targetPositions,
                                                 String toFileRank,
                                                 Boolean modifyPosition,
                                                 FileRank toPosition) {
        Boolean isValid = false;
        for (String position : targetPositions) {
            if (toFileRank.equals(position) && !getIsKingChecked(toFileRank)) {
                isValid = true;
                if (modifyPosition) {
                    if (this instanceof Rook) {
                        ((Rook) this).setIsMoved(true);
                    } else if (this instanceof King) {
                        ((King) this).setIsMoved(true);
                    }
                    this.currentPosition = toPosition;
                }
                break;
            }
        }
        return isValid;
    }
}