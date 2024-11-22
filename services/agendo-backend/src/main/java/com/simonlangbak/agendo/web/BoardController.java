package com.simonlangbak.agendo.web;

import com.simonlangbak.agendo.domain.todo.Board;
import com.simonlangbak.agendo.domain.todo.BoardColumn;
import com.simonlangbak.agendo.domain.todo.Task;
import com.simonlangbak.agendo.service.todo.BoardService;
import com.simonlangbak.agendo.service.todo.TaskService;
import com.simonlangbak.agendo.web.dto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.SortedSet;

@RestController
@RequestMapping("api/v1/board")
public class BoardController {

    private final BoardService boardService;

    private final TaskService taskService;

    public BoardController(BoardService boardService, TaskService taskService) {
        this.boardService = boardService;
        this.taskService = taskService;
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoard(@RequestParam Long boardId) {
        boardService.deleteBoard(boardId);
    }

    @PostMapping("/{id}/column")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardColumnDTO addColumnToBoard(@Valid @PathVariable Long id, @RequestBody ColumnCreationDTO columnCreationDTO) {
        BoardColumn addedColumn = boardService.addColumnToBoard(id, columnCreationDTO.name(), columnCreationDTO.description());
        return BoardColumnDTO.of(addedColumn);
    }

    @GetMapping("{id}/task")
    public List<TaskDTO> getTasksByBoardId(@PathVariable Long id) {
        SortedSet<Task> tasksByBoard = taskService.getTasksByBoard(id);
        return tasksByBoard.stream().map(TaskDTO::of).toList();
    }

    @PostMapping("/column/{id}/task")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO addTaskToColumn(@Valid @PathVariable Long id, @RequestBody TaskCreationDTO taskCreationDTO) {
        Task addedTask = taskService.addTaskToBoardColumn(id, taskCreationDTO.name());
        return TaskDTO.of(addedTask);
    }
}
