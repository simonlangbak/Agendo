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

    public Board createBoard(@NonNull String name, @NonNull String description) {
        log.debug("Trying to add new board with name: {} and description: {}", name, description);

        Board board = new Board(name, description);
        board =  boardRepository.save(board);

        log.info("Successfully added new board: {}", board);
        return board;
    }

    @Transactional
    public void deleteBoard(@NonNull Long boardId) {
        log.debug("Trying to delete board with id: {}", boardId);
        boardRepository.deleteById(boardId);
        log.info("Successfully deleted board with id: {}", boardId);
    }

    @Transactional
    public BoardColumn addColumnToBoard(Long boardId, String columnName, String columnDescription) {
        log.debug("Trying to add column to board with id: {}, column name: {} and description: {}", boardId, columnName, columnDescription);

        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
           log.warn("Could not find board with id: {}", boardId);
           return new IllegalArgumentException("Could not find board with id: " + boardId);
        });

        BoardColumn boardColumn = new BoardColumn(board, columnName, columnDescription);
        board.addColumn(boardColumn);

        boardColumn = boardColumnRepository.save(boardColumn);

        log.info("Added new column: {} to board: {}", boardColumn, board);
        return boardColumn;
    }
}
