package br.com.redis.controller;

import br.com.redis.model.Job;
import br.com.redis.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> findAll() {
        return jobService.findAll();
    }

    @GetMapping("/{identifier}")
    public Job findByIdentifier(@PathVariable("identifier") final String identifier) {
        return jobService.findbyIdentifier(identifier);
    }

    @PostMapping
    public Job create(@RequestBody final Job job) {
        return jobService.create(job);
    }

    @PutMapping
    public Job update(@RequestBody final Job job) {
        return jobService.update(job);
    }

    @DeleteMapping("/{identifier}")
    public void delete(@PathVariable("identifier") final String identifier) {
        jobService.delete(identifier);
    }

}
