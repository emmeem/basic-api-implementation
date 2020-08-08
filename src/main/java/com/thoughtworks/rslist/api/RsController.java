package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initRsEventList();

  private List<RsEvent> initRsEventList() {
    List<RsEvent> rsEvents = new ArrayList<>();
    rsEvents.add(new RsEvent("第一条事件", "无分类"));
    rsEvents.add(new RsEvent("第二条事件", "无分类"));
    rsEvents.add(new RsEvent("第三条事件", "无分类"));

    return rsEvents;
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getRsEventRange(@RequestParam(required = false) Integer start,
                                       @RequestParam(required = false) Integer end){
    if(start == null || end == null){
      return rsList;
    }
    return rsList.subList(start-1, end);
  }

  @GetMapping("/rs/{index}")
  public RsEvent getRsEvent(@PathVariable int index){
    return rsList.get(index-1);
  }

  @PostMapping("/rs/event")
  public void addOneRsEvent(@RequestBody RsEvent rsEvent) {
    rsList.add(rsEvent);
  }

  @PutMapping("/rs/{index}")
  public void changeOneRsEvent(@PathVariable int index,
                               @RequestBody RsEvent newrsEvent) {
    RsEvent rsEvent = rsList.get(index-1);
    if(newrsEvent.getEventName() !=null ){
      rsEvent.setEventName(newrsEvent.getEventName());

    }
    if(newrsEvent.getKeyWord() != null) {
      rsEvent.setKeyWord(newrsEvent.getKeyWord());
    }
  }

  @DeleteMapping("/rs/{index}")
  public void deleteRsEvent(@PathVariable("index") int index) {
    rsList.remove(index-1);
  }
}
