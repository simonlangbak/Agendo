package com.simonlangbak.agendo.domain.todo;

import com.simonlangbak.agendo.domain.BaseEntity;
import com.simonlangbak.agendo.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Getter
@Setter
public class Task extends BaseEntity implements Comparable<Task> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private SortedSet<User> assignees = new TreeSet<>();

    @ManyToOne
    @NotNull
    private BoardColumn column;

    protected Task() {
    }

    public Task(String name, BoardColumn column) {
        this.name = name;
        this.column = column;
    }

    public void setColumn(BoardColumn column) {
        if (column == null) {
            throw new IllegalArgumentException("Board column cannot be null");
        }

        BoardColumn oldColumn = this.column;
        this.column = column;

        // Remove task for another column
        if (oldColumn != null && !oldColumn.equals(column)) {
            column.removeTask(this);
        }

        column.addTask(this);
    }

    /**
     * Use the entity's database ID to sort the columns.
     */
    @Override
    public int compareTo(Task o) {
        return this.id == null || o.id == null ? 1 : this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task task)) return false;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignees=" + assignees +
                ", pilar=" + column +
                '}';
    }
}
