    package ma.meriem.patients_mvc;

    import ma.meriem.patients_mvc.entities.Patient;
    import ma.meriem.patients_mvc.repositories.PatientRepository;
    import ma.meriem.patients_mvc.security.service.AccountService;
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.context.annotation.Bean;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.provisioning.JdbcUserDetailsManager;

    import java.util.Date;

    @SpringBootApplication
    public class PatientsMvcApplication {

        public static void main(String[] args) {

            SpringApplication.run(PatientsMvcApplication.class, args);
        }
        //@Bean
        CommandLineRunner start (PatientRepository patientRepository){
            return args -> {
                patientRepository.save(
                        new Patient(null,"hassan","el hadraoui",new Date(),false,112));
                patientRepository.save(
                        new Patient(null,"meriem","el hadraoui",new Date(),false,112));
                patientRepository.save(
                        new Patient(null,"fatima","el hadraoui",new Date(),false,112));
            };


        }
        //@Bean
        CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager){
            PasswordEncoder passwordEncoder=passwordEncoder();
          return args -> {
              UserDetails u1=jdbcUserDetailsManager.loadUserByUsername("meriem2");
              if(u1==null)
            jdbcUserDetailsManager.createUser(
                    User.withUsername("meriem2").password(passwordEncoder().encode("1234")).roles("USER").build()
            );
              UserDetails u2=jdbcUserDetailsManager.loadUserByUsername("user22");
              if(u2==null)
              jdbcUserDetailsManager.createUser(
                      User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build()
              );
              UserDetails u3=jdbcUserDetailsManager.loadUserByUsername("admin2");
              if(u3==null)
              jdbcUserDetailsManager.createUser(
                      User.withUsername("admin2").password(passwordEncoder().encode("1234")).roles("ADMIN","USER").build()
              );
          };
        }
       //@Bean
       CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
            return args -> {
         accountService.addNewRole("USER");
         accountService.addNewRole("ADMIN");

         accountService.addNewUser("meriem","1234","meriem@gmail.com","1234");
         accountService.addNewUser("user2","1234","user2@gmail.com","1234");
         accountService.addNewUser("admin","1234","admin@gmail.com","1234");

         accountService.addRoleToUser("meriem","USER");
         accountService.addRoleToUser("user2","USER");
         accountService.addRoleToUser("admin","USER");
         accountService.addRoleToUser("admin","ADMIN");


            };
       }
        @Bean
    PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
    }
    }
