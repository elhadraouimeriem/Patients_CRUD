package ma.meriem.patients_mvc;

import ma.meriem.patients_mvc.entities.Patient;
import ma.meriem.patients_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null,"hassan","el hadraoui",new Date(),false,12));
            patientRepository.save(
                    new Patient(null,"meriem","el hadraoui",new Date(),false,12));
            patientRepository.save(
                    new Patient(null,"fatima","el hadraoui",new Date(),false,12));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());

            });
        };


    }

}
