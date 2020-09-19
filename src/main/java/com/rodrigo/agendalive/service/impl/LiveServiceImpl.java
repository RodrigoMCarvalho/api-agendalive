package com.rodrigo.agendalive.service.impl;

import com.rodrigo.agendalive.document.LiveDocument;
import com.rodrigo.agendalive.repository.LiveRepository;
import com.rodrigo.agendalive.service.LiveService;
import com.rodrigo.agendalive.validacao.LiveNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LiveServiceImpl implements LiveService {

    private LiveRepository repository;

    @Autowired
    public void setRepository(LiveRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<LiveDocument> findAll(Pageable pageable, String flag) {
        if(flag != null && flag.equals("next")) {
            return repository.findByLiveDateAfterOrderByLiveDateAsc(LocalDateTime.now(), pageable);
        } else if (flag != null && flag.equals("previous")) {
            return repository.findByLiveDateBeforeOrderByLiveDateDesc(LocalDateTime.now(), pageable);
        }
        return repository.findAll(pageable);
    }

    @Override
    public Optional<LiveDocument> findById(String id) {
        Optional<LiveDocument> live = repository.findById(id);
        if(!live.isPresent()) {
            throw new LiveNotFound("Live não encontrada.");
        }
        return live;
    }

    @Override
    public LiveDocument save(LiveDocument liveDocument) {
        return repository.save(liveDocument);
    }

    @Override
    public void delete(LiveDocument liveDocument) {
        findById(liveDocument.getId());
        repository.delete(liveDocument);
    }
}
