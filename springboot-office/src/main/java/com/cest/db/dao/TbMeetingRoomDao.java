package com.cest.db.dao;

import com.cest.db.pojo.TbMeetingRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

@Mapper
public interface TbMeetingRoomDao {
    public ArrayList<HashMap> searchAllMeetingRoom();
    
    public HashMap searchById(int id);
    
    public ArrayList<String> searchFreeMeetingRoom(HashMap param);

}
