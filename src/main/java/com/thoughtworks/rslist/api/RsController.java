package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.thoughtworks.rslist.domain.RsEvent;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  /*
  public static List<RsEvent> rsList = initRsEventList();

  public static List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类", new User("Userl","male",22,"lliao@a.com","16888888888",10)));
    rsEvents.add(new RsEvent("第二条事件", "无分类", new User("Userj","male",23,"jliao@a.com","15888888888",10)));
    rsEvents.add(new RsEvent("第三条事件", "无分类", new User("Userb","male",21,"bliao@a.com","14888888888",10)));

    return rsEvents;
  }
  */
  private List<RsEvent> rsList = createInitialList();
  private final RsEventRepository rsEventRepository;
  private final UserRepository userRepository;

  public RsController(RsEventRepository rsEventRepository, UserRepository userRepository) {
    this.rsEventRepository = rsEventRepository;
    this.userRepository = userRepository;
  }

  private static List<RsEvent> createInitialList() {
    List<RsEvent> initList = new ArrayList<>();
    return initList;
  }

  @GetMapping("/rs/lists")
  public ResponseEntity<List<RsEvent>> getLists() {

    return ResponseEntity.ok(rsList);
  }

  @GetMapping("/rs/{index}")
  public  ResponseEntity<RsEvent> getOneRsEvent(@PathVariable int index) throws InvalidIndexException {
    if(index > rsList.size()) {
      throw new InvalidIndexException("invalid index");
    }
    return ResponseEntity.ok((rsList.get(index - 1)));
  }

  @GetMapping("/rs/list")
  public ResponseEntity<List<RsEvent>> getRsEventRange(@RequestParam(required = false) Integer start,
                                       @RequestParam(required = false) Integer end){
    if(start == null || end == null){
      return ResponseEntity.ok(rsList);
    }
    return ResponseEntity.ok(rsList.subList(start-1, end));
  }

  @PostMapping("/rs/event")
  public ResponseEntity rsEventRepository(@RequestBody @Valid RsEvent rsEvent) {
    Integer userId = Integer.valueOf(rsEvent.getUserId());
    if (!userRepository.existsById(Integer.valueOf(rsEvent.getUserId()))) {
      return ResponseEntity.badRequest().build();
    }

    RsEventEntity event = RsEventEntity.builder()
            .eventName(rsEvent.getEventName())
            .keyWord(rsEvent.getKeyWord())
            .userId(String.valueOf(userId))
            .build();
    rsEventRepository.save(event);
    return ResponseEntity.created(null).build();
  }

  @PutMapping("/rs/{index}")
  public void changeOneRsEvent(@PathVariable int index, @RequestBody RsEvent newrsEvent){
    RsEvent rsEvent = rsList.get(index-1);
    if(newrsEvent.getEventName() !=null && newrsEvent.getKeyWord() != null){
        rsEvent.setEventName(newrsEvent.getEventName());
        rsEvent.setKeyWord(newrsEvent.getKeyWord());
    }
  }

  @DeleteMapping("/rs/{index}")
  public void deleteRsEvent(@PathVariable int index){
    rsList.remove(index-1);
  }
}
