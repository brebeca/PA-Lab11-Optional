package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/players")
public class Controller {
    private Player player;

    /**
     * initializeaza obiectul player care va face comunicarea cu baza de date
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Controller() throws SQLException, ClassNotFoundException {
        player=new Player(new DataBaseConn().getConnection());
    }

    /**
     *
     * @return lista de players
     */
    @GetMapping
    public List<String> getPlayers() throws SQLException {
         System.out.println("intra bine");
        return player.getList();
    }

    /**
     *
     * @return numarul de playeri din baza de date
     */
    @GetMapping("/count")
    public int countProducts() throws SQLException {
        return player.getCount();
    }

    /**
     *
     * @param name
     * @return un player cu numele {name}
     */
    @GetMapping("/{name}")
    public List<String> getProduct(@PathVariable("name") String name) throws SQLException {
         return player.findByName(name);
    }

    /**
     *  adauga un player nou cu numele "name" in baza de date
     * @param name
     * @return 1
     */
    @PostMapping
    public int createProduct(@RequestParam String name) throws SQLException, ClassNotFoundException {
        player.create(name);
        return 1;
    }

    /**
     * updateaza un pleyer existent : ii schimba numele
     * se intoarce un mesaj de success daca produsul exista si eroare daca nu exista
     * @param old_name numele din vechiul player
     * @param name numele cu care se face update-ul
     * @return
     */
    @PutMapping("/{old_name}")
    public ResponseEntity<String> updateProduct(
            @PathVariable String
                    old_name,  @RequestParam String name) throws SQLException {
        if(player.findByName(old_name).size()==0)
        {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.NOT_FOUND); //or GONE
        }
        player.setName(name,old_name);
        return new ResponseEntity<>(
                "Product updated successsfully", HttpStatus.OK);
    }



    /**
     * sterge playerul cu numele dat ca parametru
     * @param name numele dupa care se face cautarea
     * @return daca gaseste playerul intoarce mesaj de succes, daca nu de eroare
     */
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<String> deleteProduct(@PathVariable String name) throws SQLException {

        if(player.findByName(name).size()==0) {
            return new ResponseEntity<>(
                    "Product not found", HttpStatus.GONE);
        }
        player.delete(name);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }




}
