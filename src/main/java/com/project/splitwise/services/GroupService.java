package com.project.splitwise.services;

import com.project.splitwise.dtos.RegisterGroupRequestDto;
import com.project.splitwise.dtos.RegisterGroupResponseDto;
import com.project.splitwise.exceptions.GroupDoesntExistException;
import com.project.splitwise.exceptions.UserDoesntExistException;
import com.project.splitwise.models.Group;
import com.project.splitwise.models.User;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class GroupService {
    GroupRepository groupRepository;
    UserRepository userRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, UserRepository userRepository){
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public RegisterGroupResponseDto registerGroup(String name, Long createdById, List<Long> memberIds, String desc) throws UserDoesntExistException {
        System.out.println("getting user who is the owner of group");
        Optional<User> optionalCreatedBy = userRepository.findById(createdById);
        if(optionalCreatedBy.isEmpty()){
            throw new UserDoesntExistException("User with userId: "+createdById+" doesn't exist. Create the user first");
        }
        User createdBy = optionalCreatedBy.get();

        HashSet<Long> members_set = new HashSet<>();
        members_set.add(createdById);
        members_set.addAll(memberIds);

        List<User> members = new ArrayList<>();
        List<String> added = new ArrayList<>();
        List<Long> missing = new ArrayList<>();

        System.out.println("creating members list");
        for(long id:members_set){
            Optional<User> user = userRepository.findById(id);
            if(user.isPresent()){
                members.add(user.get());
                added.add(user.get().getName());
            }
            else{
                missing.add(id);
            }
        }

        System.out.println("creating new group");
        Group group = new Group();
        group.setName(name);
        group.setCreatedBy(createdBy);
        group.setDescription(desc);
        group.setMembers(members);
        Group groupSaved = groupRepository.save(group);

        System.out.println("creating the response");
        RegisterGroupResponseDto response = new RegisterGroupResponseDto();

        System.out.println("groupSaved.getId(): "+groupSaved.getId());
        System.out.println("groupSaved.getName(): "+groupSaved.getName());
        System.out.println("groupSaved.getMembers(): "+groupSaved.getMembers());
        System.out.println("missing: "+missing);
        response.setGroupId(groupSaved.getId());
        response.setName(groupSaved.getName());
        response.setAddedUsers(added);
        response.setMissingIds(missing);

        System.out.println("returning the response");
        return response;
    }

    public void addUsersToGroup(long groupId, List<Long> userIds) throws GroupDoesntExistException {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new GroupDoesntExistException("Group with id= "+groupId+" doesn't exist in database");
        }
        Group group = optionalGroup.get();
        List<User> usersPresent = group.getMembers();
        Set<User> usersPresentSet = new HashSet<>();
        usersPresentSet.addAll(usersPresent);

        List<Long> notFoundIds = new ArrayList<>();
        List<User> usersToBeAdded = new ArrayList<>();
        for(long id:userIds){
            Optional<User> optionalUser = userRepository.findById(id);
            if(optionalUser.isEmpty()){
                notFoundIds.add(id);
            }
            if(!usersPresentSet.contains(optionalUser.get())){
                usersToBeAdded.add(optionalUser.get());
            }
        }

        group.addUsers(usersToBeAdded);
        groupRepository.save(group);

    }
}

/*
private String description;

    @ManyToMany
    private List<User> members;

    @ManyToOne
    private User createdBy;
    private String name;
 */