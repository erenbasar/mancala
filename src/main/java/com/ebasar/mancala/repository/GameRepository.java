package com.ebasar.mancala.repository;

import org.springframework.data.repository.CrudRepository;
import com.ebasar.mancala.model.Game;

public interface GameRepository extends CrudRepository<Game, String> {
}
