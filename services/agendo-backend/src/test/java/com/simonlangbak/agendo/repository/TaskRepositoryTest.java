package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.todo.Board;
import com.simonlangbak.agendo.domain.todo.BoardColumn;
import com.simonlangbak.agendo.domain.todo.Task;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// do not replace the testcontainer data source
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class TaskRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.3");

    @Test
    @Transactional
    void testCanFindTasksByBoardId() {
        // We create two boards with columns and tasks to ensure that the query is able to only find tasks by specified
        // board id
        Board boardA = createBoardWithThreeColumnsAndATaskInEach("boardA");
        Board boardB = createBoardWithThreeColumnsAndATaskInEach("boardB");

        // Verify find tasks by board id (id=1) finds the tasks in board A
        List<Task> tasksInBoardA = boardA.getColumns().stream().flatMap(c -> c.getTasks().stream()).toList();
        assertIterableEquals(tasksInBoardA, taskRepository.findByBoardId(boardA.getId()));

        // Verify find tasks by board id (id=2) finds the tasks in board B
        List<Task> tasksInBoardB = boardB.getColumns().stream().flatMap(c -> c.getTasks().stream()).toList();
        assertIterableEquals(tasksInBoardB, taskRepository.findByBoardId(boardB.getId()));

    }

    private Board createBoardWithThreeColumnsAndATaskInEach(String boardName) {
        Board board = new Board(boardName, null);
        for (int i = 1; i <= 3; i++) {
            board.addColumn(createColumnWithTask(board, i));
        }
        Board persistedBoard = boardRepository.save(board);

        // Verify that the data was persisted as we expected
        assertAll(
                () -> assertNotNull(persistedBoard),
                () -> assertNotNull(persistedBoard.getId()),
                () -> assertEquals(3, persistedBoard.getColumns().size()),
                () -> {
                    for (BoardColumn column : persistedBoard.getColumns()) {
                        assertNotNull(column);
                        assertNotNull(column.getId());
                        assertNotNull(column.getTasks());
                        for (Task task : column.getTasks()) {
                            assertNotNull(task);
                            assertNotNull(task.getId());
                            assertNotNull(task.getColumn());
                        }
                    }
                }
        );

        return persistedBoard;
    }

    private BoardColumn createColumnWithTask(Board board, int number) {
        BoardColumn column = new BoardColumn(board, board.getName() + " - column " + number, null);
        column.addTask(new Task(board.getName() + " - column " + number + " - task " + number, column));
        return column;
    }

}