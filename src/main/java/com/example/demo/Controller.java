package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/players")
public class Controller {
    private final List<Player> players = new ArrayList<>();

    public Controller() {
        players.add(new Player("Ion"));
        players.add(new Player( "Bob"));
    }

    /**
     *
     * @return lista de players
     */
    @GetMapping
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @return numarul de playeri din lista
     */
    @GetMapping("/count")
    public int countProducts() {
        return players.size();
    }

    /**
     *
     * @param name
     * @return obiectul player cu numele {name}
     */
    @GetMapping("/{name}")
    public Player getProduct(@PathVariable("name") String name) {
        return players.stream()
                .filter(p -> p.getName().equals( name)).findFirst().orElse(null);
    }

    /**
     *  adauga un obiect nou cu numele "name" in lista
     * @param name
     * @return id-ul obiectului adaugat
     */
    @PostMapping
    public int createProduct(@RequestParam String name) {
        int id = 1 + players.size();
        players.add(new Player( name));
        return id;
    }

    /**
     *adauga obiectul primit ca parametru in lisat
     * @param player obiectul de adugat
     * @return
     */
    @PostMapping(value = "/obj", consumes="application/json")
    public ResponseEntity<String>
    createProduct(@RequestBody Player player) {
        players.add(player);
        return new ResponseEntity<>(
                "Product created successfully", HttpStatus.CREATED);
    }

    /**
     * updateaza un obiect existent : ii schimba numele
     * se intoarce un mesaj de success daca produsul exista si eroare daca nu exista
     * @param old_name numele din vechiul obiect
     * @param name numele cu care se face update-ul
     * @return
     */
    @PutMapping("/{old_name}")
    public ResponseEntity<String> updateProduct(
            @PathVariable String
                    old_name,  @RequestParam String name) {
        Player product = findByName(old_name);
        if (product == null) {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.NOT_FOUND); //or GONE
        }
        product.setName(name);
        return new ResponseEntity<>(
                "Product updated successsfully", HttpStatus.OK);
    }

    /**
     * cauta obiectul cu campul name = old_name
     * @param old_name numele dupa care se cauta obiectul
     * @return obiectul gasit sau null daca nu il gaseste
     */
    private Player findByName(String old_name) {
        for(Player player:players)
        {
            if(player.name.equals(old_name))
                return player;
        }
        return null;
    }

    /**
     * sterge playerul cu numele dat ca parametru
     * @param name numele dupa care se face cautarea
     * @return daca gaseste playerul intoarce mesaj de succes, daca nu de eroare
     */
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) {
        Player product = findByName(name);
        if (product == null) {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.GONE);
        }
        players.remove(product);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }

}
