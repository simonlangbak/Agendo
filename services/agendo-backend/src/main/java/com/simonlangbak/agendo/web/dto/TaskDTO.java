package com.simonlangbak.agendo.web.dto;

import com.simonlangbak.agendo.domain.todo.Task;

public record TaskDTO(Long id, String name, Long boardId, Long boardColumnId) {

    /**
     * NOTE: This method must be invoked in a transaction that is still attached to the task entity!
     */
    public static TaskDTO of(Task task) {
        return new TaskDTO(task.getId(), task.getName(), task.getColumn().getBoard().getId(), task.getColumn().getId());
    }

}
