package com.simonlangbak.agendo.repository;

import com.simonlangbak.agendo.domain.todo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.SortedSet;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = "SELECT t FROM Task AS t WHERE t.column.board.id = ?1")
    SortedSet<Task> findByBoardId(Long boardId);
}
