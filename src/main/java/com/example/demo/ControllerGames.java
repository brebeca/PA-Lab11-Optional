package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/games")
public class ControllerGames {
    private GameTable game;

    /**
     * initializeaza obiectul de tip GameTable care va face comunicarea cu baza de date
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ControllerGames() throws SQLException, ClassNotFoundException {
        game =new GameTable(new DataBaseConn().getConnection());
    }

    /**
     *
     * @return lista de games sub forma de string
     */
    @GetMapping
    public String getGame() throws SQLException {
        return game.getList().toString();
    }

    /**
     *
     * @return numarul de games din baza de date
     */
    @GetMapping("/count")
    public int countProducts() throws SQLException {
        return game.getCount();
    }

    /**
     *
     * @param name
     * @return un game cu numele winner-ului {name}
     */
    @GetMapping("/{name}")
    public String getProduct(@PathVariable("name") String name) throws SQLException {
        return game.findByWinner(name).toString();
    }

    /**
     *  adauga un game   nou cu winner cu numele "name" in baza de date
     * @param name
     * @return 1
     */
    @PostMapping
    public int createProduct(@RequestParam String name,@RequestParam int nrPlayers,@RequestParam int boardDim) throws SQLException, ClassNotFoundException {
        game.create(name,nrPlayers,boardDim);
        return 1;
    }

    /**
     * updateaza un game existent : ii schimba winner-ul
     * se intoarce un mesaj de success daca game-ul exista si eroare daca nu exista
     * @param old_name winner-ul din vechiul game
     * @param name winner-ul cu care se face update-ul
     * @return
     */
    @PutMapping("/{old_name}")
    public ResponseEntity<String> updateProduct(
            @PathVariable String
                    old_name,  @RequestParam String name) throws SQLException {
        if(game.findByWinner(old_name).size()==0)
        {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.NOT_FOUND); //or GONE
        }
        game.setName(name,old_name);
        return new ResponseEntity<>(
                "Product updated successsfully", HttpStatus.OK);
    }



    /**
     * sterge game-ul cu winner-ul dat ca parametru
     * @param name numele winner-ul dupa care se face cautarea
     * @return daca gaseste game-ul intoarce mesaj de succes, daca nu de eroare
     */
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) throws SQLException {

        if(game.findByWinner(name).size()==0) {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.GONE);
        }
        game.delete(name);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }



}
