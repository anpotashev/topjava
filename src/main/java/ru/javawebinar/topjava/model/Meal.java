package ru.javawebinar.topjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
@NamedQueries(
        {
                @NamedQuery(name = "MEAL.findByIdAndUserID"
                        , query = "SELECT m from Meal m WHERE m.id=:id AND m.user.id=:userId")
                , @NamedQuery(name = "MEAL.findAllUserID"
                , query = "SELECT m from Meal m WHERE m.user.id=:userId order by m.dateTime desc ")
                , @NamedQuery(name = "MEAL.findAllBetweenDatesAndUserID"
                , query = "SELECT m from Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate order by m.dateTime desc ")
                , @NamedQuery(name = "MEAL.update"
                , query = "UPDATE Meal m SET m.dateTime=:dateTime, m.description=:description, m.calories=:calories WHERE m.id=:id AND m.user.id=:userId")
                , @NamedQuery(name = "MEAL.delete"
                , query = "DELETE from Meal m WHERE m.id=:id AND m.user.id=:userId")

        }

)
public class Meal extends AbstractBaseEntity {

    @Column(name = "date_time", nullable = false)
    @NotNull(message = "date_time must not be empty")
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    @NotEmpty(message = "description must not be empty")
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull(message = "calories must not be empty")
    @Min(value = 1, message = "calories must be positive")
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
