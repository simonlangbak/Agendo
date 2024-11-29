package com.simonlangbak.agendo.service.todo;

import com.simonlangbak.agendo.domain.todo.Board;
import com.simonlangbak.agendo.domain.todo.BoardColumn;
import com.simonlangbak.agendo.repository.BoardColumnRepository;
import com.simonlangbak.agendo.repository.BoardRepository;
import com.simonlangbak.agendo.service.AbstractService;
import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BoardService extends AbstractService {

    private final BoardRepository boardRepository;

    private final BoardColumnRepository boardColumnRepository;

    public BoardService(BoardRepository boardRepository, BoardColumnRepository boardColumnRepository) {
        this.boardRepository = boardRepository;
        this.boardColumnRepository = boardColumnRepository;
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Transactional
    public Board createBoard(@NonNull String name, @NonNull String description) {
        log.debug("Trying to add new board with name: {} and description: {}", name, description);

        Board board = new Board(name, description);
        board =  boardRepository.save(board);

        // For now a board is created with 4 default columns
        addColumnToBoard(board, "TODO", "For tasks pending to be started");
        addColumnToBoard(board, "In-progress", "For tasks were work is in-progress");
        addColumnToBoard(board, "Review", "For tasks waiting review");
        addColumnToBoard(board, "Done", "For tasks that are done");

        Optional<Board> boardOptional = boardRepository.findById(board.getId());
        if (boardOptional.isEmpty()) {
            // Unhandled exceptions return an HTTP 500 response
            throw new NoSuchElementException("Board with id: " + board.getId() + " not found");
        }

        log.info("Successfully added new board: {}", board);
        return board;
    }

    @Transactional
    public void deleteBoard(@NonNull Long boardId) {
        log.debug("Trying to delete board with id: {}", boardId);
        boardRepository.deleteById(boardId);
        log.info("Successfully deleted board with id: {}", boardId);
    }

    private void addColumnToBoard(Board board, String columnName, String columnDescription) {
        log.debug("Trying to add column to board with id: {}, column name: {} and description: {}", board.getId(), columnName, columnDescription);

        BoardColumn boardColumn = new BoardColumn(board, columnName, columnDescription);
        board.addColumn(boardColumn);

        boardColumn = boardColumnRepository.save(boardColumn);

        log.info("Added new column: {} to board: {}", boardColumn, board);
    }
}
