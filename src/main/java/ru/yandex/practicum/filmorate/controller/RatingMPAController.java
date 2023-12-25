package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.RatingMPA;
import ru.yandex.practicum.filmorate.service.RatingMPAService;

import java.util.List;

@RestController
@RequestMapping("/mpa")
public class RatingMPAController {
    private static final Logger log = LoggerFactory.getLogger(RatingMPAController.class);
    private final RatingMPAService ratingMPAService;

    @Autowired
    public RatingMPAController(RatingMPAService ratingMPAServiceArg) {
        ratingMPAService = ratingMPAServiceArg;
    }

    @GetMapping
    public List<RatingMPA> getRatingsMPA() {
        log.debug("Пришел GET /ratings запрос");
        List<RatingMPA> ratingsMPAList = ratingMPAService.getRatingsMPA();
        log.debug("На запрос GET /ratings отправлен ответ с размером тела: {}", ratingsMPAList.size());
        return ratingsMPAList;
    }

    @GetMapping("/{id}")
    public RatingMPA getRatingMPA(@PathVariable int id) {
        log.debug("Пришел GET /ratings/{} запрос", id);
        RatingMPA ratingMPA = ratingMPAService.getRatingMPA(id);
        log.debug("На запрос GET /ratings/{} отправлен ответ с размером тела: 1", id);
        return ratingMPA;
    }
}
