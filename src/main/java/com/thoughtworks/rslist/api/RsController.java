package com.thoughtworks.rslist.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");

  @GetMapping("/rs/{index}")
  public String getOneRsEvent(@PathVariable int index){
    return rsList.get(index);
  }

  @GetMapping("/rs/list")
  public String getRsEventRange(Integer start, Integer end){
    if(start == null || end == null){
      return rsList.toString();
    }
    return rsList.subList(start-1, end).toString();
  }

  @PostMapping("/rs/event")
  public void addOneRsEvent(@RequestBody String rsEvent) {
    rsList.add(rsEvent);
  }

  
}
