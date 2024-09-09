package com.simonlangbak.agendo.web;

import com.simonlangbak.agendo.domain.todo.Board;
import com.simonlangbak.agendo.domain.todo.BoardColumn;
import com.simonlangbak.agendo.service.todo.BoardService;
import com.simonlangbak.agendo.web.dto.BoardColumnDTO;
import com.simonlangbak.agendo.web.dto.BoardCreationDTO;
import com.simonlangbak.agendo.web.dto.BoardDTO;
import com.simonlangbak.agendo.web.dto.ColumnCreationDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/board")
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    @Transactional
    public List<BoardDTO> getBoards() {
        return boardService.getAllBoards().stream().map(BoardDTO::of).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardDTO createBoard(@Valid @RequestBody BoardCreationDTO boardCreationDTO) {
        Board createdBoard = boardService.createBoard(boardCreationDTO.name(), boardCreationDTO.description());
        return BoardDTO.of(createdBoard);
    }

    @PostMapping("/{id}/column")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardColumnDTO addColumnToBoard(@Valid @PathVariable Long id, @RequestBody ColumnCreationDTO columnCreationDTO) {
        BoardColumn addedColumn = boardService.addColumnToBoard(id, columnCreationDTO.name(), columnCreationDTO.description());
        return BoardColumnDTO.of(addedColumn);
    }
}
