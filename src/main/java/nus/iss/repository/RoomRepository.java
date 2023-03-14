package nus.iss.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import nus.iss.model.Room;

@Repository
public class RoomRepository implements IRoomRepository{

    private final String countSQL = "select count(*) from room";
    private final String findAllSQL = "select * from room";
    private final String findByIdSQL = "select * from room where id = ?";
    private final String deleteByIdSQL = "delete from room where id = ?";
    private final String insertSQL = "insert into room (room_type, price) values (?, ?)";
    private final String updateSQL = "update room set price = ? where id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        // TODO Auto-generated method stub
        Integer result = 0;
        result = jdbcTemplate.queryForObject(countSQL, Integer.class);

        if (result == null){
            return 0;
        } else {
            return result;
        }
    }

    @Override
    public Boolean save(Room room) {
        return jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>(){
            

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException{
                ps.setString(1, room.getRoomType());
                ps.setFloat(2, room.getPrice());
                Boolean rslt = ps.execute();
                return rslt;
            }
        });
    }

    @Override
    public List<Room> findAll() {
        // TODO Auto-generated method stub

        final List<Room> roomList = new ArrayList<>();

        final SqlRowSet rs = jdbcTemplate.queryForRowSet(findAllSQL);

        while (rs.next()){
            Room room = new Room();
            room.setId(rs.getInt("id"));
            room.setRoomType(rs.getString("room_type"));
            room.setPrice(rs.getFloat("price"));


            roomList.add(room);
        }
        System.out.println(roomList);

        return Collections.unmodifiableList(roomList);
        
    }

    @Override
    public Room findById(Integer id) {
        // TODO Auto-generated method stub
        return jdbcTemplate.queryForObject(findByIdSQL, BeanPropertyRowMapper.newInstance(Room.class), id);
    }

    @Override
    public int update(Room room) {
        // TODO Auto-generated method stub
        int updated = 0;

        updated = jdbcTemplate.update(updateSQL, new PreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setFloat(1, room.getPrice());
                    ps.setInt(2, room.getId());
                }
        });
        return updated;
    }

    @Override
    public int deleteById(Integer id) {
        // TODO Auto-generated method stub
        int deleted = 0;

        PreparedStatementSetter pss = new PreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, id);
                }
        };

        deleted = jdbcTemplate.update(deleteByIdSQL, pss);

        return deleted;
    }
    
}
