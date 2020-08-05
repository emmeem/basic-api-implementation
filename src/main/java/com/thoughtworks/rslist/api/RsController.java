package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.thoughtworks.rslist.domain.RsEvent;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类", new User("Userl","male",22,"lliao@a.com","16888888888")));
    rsEvents.add(new RsEvent("第二条事件", "无分类", new User("Userj","male",23,"jliao@a.com","15888888888")));
    rsEvents.add(new RsEvent("第三条事件", "无分类", new User("Userb","male",21,"bliao@a.com","14888888888")));

    return rsEvents;
  }

  @GetMapping("/rs/lists")
  public List<RsEvent> getLists() {
    return rsList;
  }

  @GetMapping("/rs/{index}")
  public RsEvent getOneRsEvent(@PathVariable int index) {
    return rsList.get(index - 1);
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventRange(@RequestParam(required = false) Integer start,
                                       @RequestParam(required = false) Integer end){
    if(start == null || end == null){
      return rsList;
    }
    return rsList.subList(start-1, end);
  }

  @PostMapping("/rs/event")
  @ResponseStatus(HttpStatus.CREATED)
  public void addOneRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
    if(!UserController.users.contains(rsEvent.getUser())) {
      UserController.register(rsEvent.getUser());
    }
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
