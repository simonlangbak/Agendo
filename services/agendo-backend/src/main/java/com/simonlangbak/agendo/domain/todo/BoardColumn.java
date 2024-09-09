package com.simonlangbak.agendo.domain.todo;

import com.simonlangbak.agendo.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Getter
@Setter
public class BoardColumn extends BaseEntity implements Comparable<BoardColumn> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @ManyToOne
    private Board board;

    @OneToMany(mappedBy = "column")
    private SortedSet<Task> tasks = new TreeSet<>();

    public BoardColumn() {
    }

    public BoardColumn(Board board, String name, String description) {
        this.board = board;
        this.name = name;
        this.description = description;
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (task.getColumn() != null && !task.getColumn().equals(this)) {
            throw new IllegalArgumentException("Task is already associated to a different column");
        }

        tasks.add(task);
    }

    public SortedSet<Task> getTasks() {
        return Collections.unmodifiableSortedSet(tasks);
    }

    private void setTasks(SortedSet<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Use the entity's database ID to sort the columns.
     */
    @Override
    public int compareTo(BoardColumn o) {
        return this.id == null || o.id == null ? 1 : this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardColumn that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "BoardColumn{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
