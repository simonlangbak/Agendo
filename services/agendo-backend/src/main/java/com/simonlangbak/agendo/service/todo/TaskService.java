package com.simonlangbak.agendo.service.todo;

import com.simonlangbak.agendo.domain.todo.BoardColumn;
import com.simonlangbak.agendo.domain.todo.Task;
import com.simonlangbak.agendo.repository.BoardColumnRepository;
import com.simonlangbak.agendo.repository.TaskRepository;
import com.simonlangbak.agendo.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.SortedSet;

@Service
public class TaskService extends AbstractService {

    private final BoardColumnRepository boardColumnRepository;

    private final TaskRepository taskRepository;

    public TaskService(BoardColumnRepository boardColumnRepository, TaskRepository taskRepository) {
        this.boardColumnRepository = boardColumnRepository;
        this.taskRepository = taskRepository;
    }

    public SortedSet<Task> getTasksByBoard(Long boardId) {
        log.debug("Fetching tasks for board with id: {}", boardId);
        SortedSet<Task> tasksByBoard = taskRepository.findByBoardId(boardId);
        log.debug("Board with id: {} contains: {}", boardId, tasksByBoard);
        return tasksByBoard;
    }

    public Task addTaskToBoardColumn(Long boardColumnId, String name) {
        log.debug("Trying to add task with name: {} to board column with id: {}", name, boardColumnId);

        BoardColumn column = boardColumnRepository.findById(boardColumnId).orElseThrow(() -> {
            log.error("Could not find board column with the provided id: {}", boardColumnId);
            return new NoSuchElementException("No board column found with id: " + boardColumnId);
        });

        Task task = taskRepository.save(new Task(name, column));
        log.debug("Added task: {}", task);
        return task;
    }

    public void deleteTaskById(Long id) {
        log.debug("Trying to delete task by id: {}", id);
        taskRepository.deleteById(id);
        log.debug("Task with id deleted: {}", id);
    }
}
