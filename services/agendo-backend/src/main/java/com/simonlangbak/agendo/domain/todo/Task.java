package com.simonlangbak.agendo.domain.todo;

import com.simonlangbak.agendo.domain.BaseEntity;
import com.simonlangbak.agendo.domain.user.User;
import jakarta.persistence.*;
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

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "task_assignees",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private SortedSet<User> assignees = new TreeSet<>();

    @ManyToOne
    private BoardColumn column;

    public Task() {
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
        return Objects.equals(id, task.id) && Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                ", assignees=" + assignees +
                ", pilar=" + column +
                '}';
    }
}
