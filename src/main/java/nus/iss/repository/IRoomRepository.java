package nus.iss.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import nus.iss.model.Room;

@Repository
public interface IRoomRepository {

    int count();

    Boolean save(Room room);

    List<Room> findAll();

    Room findById(Integer id);

    int update(Room room);

    int deleteById(Integer id);

    
    
}
