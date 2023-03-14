package nus.iss.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nus.iss.model.Room;
import nus.iss.services.RoomService;

@RequestMapping("/api/rooms")
@RestController
public class RoomController {
    
    @Autowired
    RoomService roomService;

    /*int count();

    Boolean save(Room room);

    List<Room> findAll();

    Room findById(Integer id);

    int update(Room room);

    int deleteById(Integer id); */

    @GetMapping("/count")
    public ResponseEntity<Integer> getRoomCount(){

        Integer roomCount = roomService.count();

        return ResponseEntity.ok().body(roomCount);

    }

    @PutMapping
    public ResponseEntity<Integer> updateRoom(@RequestBody Room room) throws Exception {
        int updated = 0;

        updated = roomService.update(room);

        if (updated == 1){
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updated, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteRoom(@PathVariable("id") Integer id) throws Exception {
        int deleted = 0;

        deleted = roomService.deleteById(id);

        if (deleted == 1){
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
