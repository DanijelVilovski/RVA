package rva.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rva.jpa.Igrac;
import rva.jpa.Tim;

// ime klase na koju se odnosi repozitorijum, tip vrednosti kljuca
public interface IgracRepository extends JpaRepository<Igrac, Integer> {
	
	Collection<Igrac> findByTim(Tim tim);
}
