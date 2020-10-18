package br.com.redis.service;

import br.com.redis.controller.exceptions.EntityNotFoundException;
import br.com.redis.model.Job;
import br.com.redis.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Cacheable(cacheNames = Job.CACHE_NAME, key="#root.method.name")
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Cacheable(cacheNames = Job.CACHE_NAME, key="#identifier")
    public Job findbyIdentifier(final String identifier) {
        return jobRepository.findById(identifier)
                .orElseThrow(() -> new EntityNotFoundException("Identifier not found: " + identifier));
    }

    @CacheEvict(cacheNames = Job.CACHE_NAME, allEntries = true)
    public Job create(final Job job) {
        return jobRepository.save(job);
    }

    @CachePut(cacheNames = Job.CACHE_NAME, key="#job.getIdentifier()")
    public Job update(final Job job) {
        if(job.getIdentifier() == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }
        return jobRepository.save(job);
    }

    @CacheEvict(cacheNames = Job.CACHE_NAME, key="#identifier")
    public void delete(final String identifier) {
        if(identifier == null) {
            throw new EntityNotFoundException("Identifier is empty");
        }
        jobRepository.deleteById(identifier);
    }
}
