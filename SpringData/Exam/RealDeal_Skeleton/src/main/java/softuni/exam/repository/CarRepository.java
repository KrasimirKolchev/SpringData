package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from softuni.exam.models.entities.Car as c " +
            "order by c.pictures.size desc, c.make")
    List<Car> getCarsOrderByPicturesCountDescThenByMake();
}
