package com.rodrigo.agendalive.service;

import com.rodrigo.agendalive.document.LiveDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LiveService {

    Page<LiveDocument> findAll(Pageable pageable, String flag);
    Optional<LiveDocument> findById(String id);
    LiveDocument save(LiveDocument liveDocument);
    void delete(LiveDocument liveDocument);
}
