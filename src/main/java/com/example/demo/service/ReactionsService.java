package com.example.demo.service;

import com.example.demo.model.Reactions;

import java.util.List;

public interface ReactionsService {
    List<Reactions> findAll();
    Reactions saveReactions(Reactions reactions);
    String deleteReactions(Integer id);
    Reactions updateReactions(Reactions reactions);
    Reactions findById(Integer id);
}
