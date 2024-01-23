package com.project.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "groups")
public class Group extends BaseModel {
    private String description;

    @ManyToMany
    private List<User> members;

    @OneToMany(mappedBy = "group")
    private List<Expense> expenses;

    @ManyToOne
    private User createdBy;
    private String name;

    public void addUsers(List<User> users){
        members.addAll(users);
    }

}
