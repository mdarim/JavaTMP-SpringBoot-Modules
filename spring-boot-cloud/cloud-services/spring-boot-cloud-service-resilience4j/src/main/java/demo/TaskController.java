package demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task/{id}")
    public String getTaskDetails(@PathVariable("id") Integer id) {
        return taskService.getTaskDetailsRetry(id);
    }

    @GetMapping("/taskFuture/{id}")
    public CompletableFuture<String> getFutureTaskDetails(@PathVariable("id") Integer id) {
        return taskService.getTaskDetailsFutureBulkhead(id);
    }

}
