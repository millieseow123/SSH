package vttp.edu.SSH.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp.edu.SSH.service.SearchService;

@Controller
@RequestMapping(path="/search")



public class PokeController {

    @Autowired
    public SearchService SearchSvc;
    
    @GetMapping
    public String search(@RequestParam String inputPhrase,
    @RequestParam Integer limit, @RequestParam String rating, Model model) throws IOException {
System.out.printf(">>> q = %s, limit = %d, rating = %s\n", inputPhrase, limit, rating);
        
        model.addAttribute("inputPhrase", inputPhrase);
List<String> results = SearchSvc.getGiphs(inputPhrase, limit, rating); //to link to service


    return "search";
    }
}
