package app.circle.foodmood

import app.circle.foodmood.repository.RoleRepository
import app.circle.foodmood.security.Role
import app.circle.foodmood.security.RoleName
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableCaching
class FoodmoodApplication

fun main(args: Array<String>) {
    runApplication<FoodmoodApplication>(*args)
}


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
    }
}

@EnableJpaRepositories
class Config {

    constructor() {
        println("JPA Enabled")
    }

}


@Bean
fun restTemplate(): RestTemplate {
    return RestTemplate()
}

@Component
class CommandLineAppStartupRunner(val roleRepository: RoleRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {

        val roleExists = roleRepository.findAll()
        val roleList = ArrayList<String>()
        val roleListSave = ArrayList<Role>()

        roleExists.forEach {
            roleList.add(it.name.name)
        }

        RoleName.values().forEach {
            if (!roleList.contains(it.name)) {
                val role = Role(it)

                roleListSave.add(role)
            }
        }

        roleRepository.saveAll(roleListSave)
    }

}
