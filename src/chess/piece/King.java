package chess.piece;

import chess.ChessBoard;
import chess.FileRank;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class King extends ChessPiece {

    public static final List<String> INITIAL_POSITIONS = Arrays.asList("e");
    private static final String SYMBOL = "K";
    public static final String CASTLE = "CASTLE";
    private static final List<String> VALID_MOVES = Arrays.asList(NORTH,
            NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST);
    private static int MAX_STEPS = 1;
    private Boolean isMoved = false;


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
        String toFileRank = toPosition.getFileRank().get(FileRank.FILE_KEY) + toPosition.getFileRank().get(FileRank.RANK_KEY);
        return validateMoveAndUpdatePosition(getValidMoves(VALID_MOVES,
                this.MAX_STEPS, this.currentPosition, toFileRank),
                toFileRank, modifyPosition, toPosition);
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
        for (ChessPiece piece : this.chessBoard.getPieces(this.kind)) {
            for (FileRank fileRank : this.chessBoard.getChessBoard().values()) {
                if (piece.move(fileRank, false)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setIsMoved(Boolean isMoved) {
        this.isMoved = isMoved;
    }

    public Boolean getIsMoved() {
        return this.isMoved;
    }

    public Boolean castleMove(String toFileRank, Boolean modify) {
        if (!this.getIsMoved() && !this.identifyCheck()) {
            List<ChessPiece> rooks = this.chessBoard.getPieces(this.kind).stream().filter(piece -> piece instanceof Rook).collect(Collectors.toList());
            for (ChessPiece rook : rooks) {
                if (!((Rook) rook).getIsMoved()) {
                    if (rook.getCurrentPosition().getFileRankString().equals("a1")) {
                        if (chessBoard.getChessBoard().get("d1").getOccupant() == null &&
                                chessBoard.getChessBoard().get("c1").getOccupant() == null &&
                                chessBoard.getChessBoard().get("b1").getOccupant() == null &&
                                !this.getIsKingChecked("d1") &&
                                !this.getIsKingChecked("c1")) {
                            if (toFileRank.equals("c1")) {
                                if (modify) {
                                    chessBoard.getChessBoard().get("a1").setOccupant(null);
                                    chessBoard.getChessBoard().get("d1").setOccupant(rook);
                                    this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
                                    rook.setCurrentPosition(chessBoard.getChessBoard().get("d1"));
                                }
                                return true;
                            }
                        }
                    } else if (rook.getCurrentPosition().getFileRankString().equals("h1")) {
                        if (chessBoard.getChessBoard().get("f1").getOccupant() == null &&
                                chessBoard.getChessBoard().get("g1").getOccupant() == null &&
                                !this.getIsKingChecked("f1") &&
                                !this.getIsKingChecked("g1")) {
                            if (toFileRank.equals("g1")) {
                                if (modify) {
                                    chessBoard.getChessBoard().get("h1").setOccupant(null);
                                    chessBoard.getChessBoard().get("f1").setOccupant(rook);
                                    this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
                                    rook.setCurrentPosition(chessBoard.getChessBoard().get("f1"));
                                }
                                return true;
                            }
                        }
                    } else if (rook.getCurrentPosition().getFileRankString().equals("a8")) {
                        if (chessBoard.getChessBoard().get("d8").getOccupant() == null &&
                                chessBoard.getChessBoard().get("c8").getOccupant() == null &&
                                chessBoard.getChessBoard().get("b8").getOccupant() == null &&
                                !this.getIsKingChecked("d8") &&
                                !this.getIsKingChecked("c8")) {
                            if (toFileRank.equals("c8")) {
                                if (modify) {
                                    chessBoard.getChessBoard().get("a8").setOccupant(null);
                                    chessBoard.getChessBoard().get("d8").setOccupant(rook);
                                    this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
                                    rook.setCurrentPosition(chessBoard.getChessBoard().get("d8"));
                                }
                                return true;
                            }
                        }
                    } else {
                        if (chessBoard.getChessBoard().get("f8").getOccupant() == null &&
                                chessBoard.getChessBoard().get("g8").getOccupant() == null &&
                                !this.getIsKingChecked("f8") &&
                                !this.getIsKingChecked("g8")) {
                            if (toFileRank.equals("g8")) {
                                if (modify) {
                                    this.currentPosition = chessBoard.getChessBoard().get(toFileRank);
                                    chessBoard.getChessBoard().get("h8").setOccupant(null);
                                    chessBoard.getChessBoard().get("f8").setOccupant(rook);
                                    rook.setCurrentPosition(chessBoard.getChessBoard().get("f8"));
                                }
                                return true;
                            }
                        }
                    }
                }
            }

        }
        return false;
    }
}
